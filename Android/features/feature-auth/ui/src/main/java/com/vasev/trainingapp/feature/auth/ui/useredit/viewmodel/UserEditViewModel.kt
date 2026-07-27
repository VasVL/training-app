package com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.mapper.UserEditUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel for creating and editing a user profile.
 * ViewModel для создания и редактирования профиля пользователя.
 *
 * `@HiltViewModel` — Hilt creates this ViewModel and provides its dependencies.
 * `@HiltViewModel` — Hilt создаёт эту ViewModel и предоставляет её зависимости.
 */
@HiltViewModel
internal class UserEditViewModel @Inject constructor(
    private val userEditUiMapper: UserEditUiMapper,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserEditUiState>(UserEditUiState.Loading)

    val uiState = _uiState.asStateFlow()

    fun createFirstUser() {
        Timber.d("User edit: initialize first user profile")
        _uiState.value = UserEditUiState.Ready(
            role = UserEditUiState.Ready.Role.OWNER,
        )
    }

    fun createNewUser() {
        Timber.d("User edit: initialize additional user profile")
        _uiState.value = UserEditUiState.Ready(
            role = UserEditUiState.Ready.Role.TRAINEE,
        )
    }

    fun loadUser(userId: Long) {
        Timber.d("User edit: load existing user profile")
        viewModelScope.launch {
            try {
                val user = userRepository.getById(userId)
                if (user == null) {
                    Timber.w("User edit: requested user profile was not found")
                    _uiState.value = UserEditUiState.Error(
                        reason = UserEditUiState.Error.Reason.USER_NOT_FOUND,
                    )
                    return@launch
                }

                _uiState.value = userEditUiMapper.map(user)
                Timber.d("User edit: existing user profile loaded")
            } catch (throwable: Throwable) {
                Timber.e(throwable, "User edit: failed to load existing user profile")
                _uiState.value = UserEditUiState.Error(
                    reason = UserEditUiState.Error.Reason.LOAD_USER_FAILED,
                )
            }
        }
    }
}
