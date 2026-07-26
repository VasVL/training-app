package com.vasev.trainingapp.feature.auth.ui.userselect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasev.trainingapp.core.navigation.MainScreen
import com.vasev.trainingapp.core.navigation.Navigator
import com.vasev.trainingapp.feature.auth.contract.AuthScreen
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiState
import com.vasev.trainingapp.feature.auth.ui.userselect.mapper.UserSelectUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel for selecting the active user.
 * ViewModel для выбора активного пользователя.
 *
 * `@HiltViewModel` — Hilt creates this ViewModel and provides dependencies from its constructor.
 * `@HiltViewModel` — Hilt создаёт эту ViewModel и предоставляет зависимости из её конструктора.
 */
@HiltViewModel
internal class UserSelectViewModel @Inject constructor(
    private val navigator: Navigator,
    private val userRepository: UserRepository,
    private val userSelectUiMapper: UserSelectUiMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserSelectUiState>(UserSelectUiState.Loading)

    val uiState = _uiState.asStateFlow()

    init {
        Timber.d("User selection: start observing users")
        observeUsers()
    }

    fun onAddUserClicked() {
        Timber.d("User selection: add user requested")
        navigator.navigate(AuthScreen.Create())
    }

    fun onUserClicked(id: Long) {
        Timber.d("User selection: activate user requested, id=$id")
        viewModelScope.launch {
            try {
                userRepository.setActive(id)
                Timber.d("User selection: active user updated, navigate to main")
                navigator.navigate(MainScreen.Main)
            } catch (throwable: Throwable) {
                Timber.e(throwable, "User selection: failed to activate user")
                updateError(throwable)
            }
        }
    }

    private fun currentUsers(): List<UserSelectUiState.Ready.UserItem> {
        return when (val state = _uiState.value) {
            is UserSelectUiState.Error -> state.users
            is UserSelectUiState.Loading -> emptyList()
            is UserSelectUiState.Ready -> state.users
        }
    }

    private fun observeUsers() {
        viewModelScope.launch {
            userRepository.observeForSelection()
                .catch { throwable ->
                    Timber.e(throwable, "User selection: failed to load users")
                    updateError(throwable)
                }
                .collect { users ->
                    val userItems = userSelectUiMapper.map(users)
                    Timber.d("User selection: users loaded, count=${userItems.size}")
                    _uiState.value = UserSelectUiState.Ready(
                        users = userItems,
                    )
                }
        }
    }

    private fun updateError(throwable: Throwable) {
        _uiState.value = UserSelectUiState.Error(
            message = throwable.message.orEmpty(),
            users = currentUsers(),
        )
    }
}
