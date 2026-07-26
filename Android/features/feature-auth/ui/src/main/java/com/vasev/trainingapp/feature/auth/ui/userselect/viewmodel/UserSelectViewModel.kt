package com.vasev.trainingapp.feature.auth.ui.userselect.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasev.trainingapp.core.navigation.MainScreen
import com.vasev.trainingapp.core.navigation.Navigator
import com.vasev.trainingapp.feature.auth.contract.AuthScreen
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiAction
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiState
import com.vasev.trainingapp.feature.auth.ui.userselect.mapper.UserSelectUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
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

    private val _uiAction = MutableSharedFlow<UserSelectUiAction>()

    val uiAction = _uiAction.asSharedFlow()
    val uiState = _uiState.asStateFlow()

    init {
        Timber.d("User selection: start observing users")
        observeUsers()
    }

    fun createNewUser() {
        Timber.d("User selection: add user requested")
        navigator.navigate(AuthScreen.CreateNewUser)
    }

    fun activateUser(id: Long) {
        Timber.d("User selection: activate user requested, id=$id")
        viewModelScope.launch {
            try {
                val isActiveUserSet = userRepository.setActive(id)
                if (!isActiveUserSet) {
                    Timber.w("User selection: selected user was not found")
                    emitError(
                        reason = UserSelectUiAction.ShowError.ErrorReason.ACTIVE_USER_NOT_FOUND,
                    )
                    return@launch
                }

                Timber.d("User selection: active user updated, navigate to main")
                navigator.navigate(MainScreen.Main)
            } catch (throwable: Throwable) {
                Timber.e(throwable, "User selection: failed to activate user")
                emitError(
                    reason = UserSelectUiAction.ShowError.ErrorReason.ACTIVATE_USER_WRITE_FAILED,
                )
            }
        }
    }

    fun requestUserDeletion(id: Long) {
        Timber.d("User selection: delete user requested, id=$id")
        val user = currentUsers().firstOrNull { currentUser ->
            !currentUser.isActive &&
                currentUser.id == id &&
                currentUser.role == UserSelectUiState.Ready.UserItem.Role.TRAINEE
        }
        if (user == null) {
            Timber.w("User selection: user is not available for deletion, id=$id")
            viewModelScope.launch {
                emitError(
                    reason = UserSelectUiAction.ShowError.ErrorReason.DELETE_USER_NOT_AVAILABLE,
                )
            }
            return
        }

        viewModelScope.launch {
            emitAction(
                action = UserSelectUiAction.ShowDeletionConfirmation(
                    id = user.id,
                    name = user.name,
                ),
            )
        }
    }

    fun cancelDeletion(id: Long) {
        Timber.d("User selection: cancel deletion requested, id=$id")
        viewModelScope.launch {
            try {
                val isDeletionCancelled = userRepository.cancelDeletion(id)
                if (!isDeletionCancelled) {
                    Timber.w("User selection: deletion could not be cancelled, id=$id")
                    emitError(
                        reason = UserSelectUiAction.ShowError.ErrorReason.CANCEL_DELETION_WRITE_FAILED,
                    )
                    return@launch
                }

                Timber.d("User selection: deletion cancelled, id=$id")
            } catch (throwable: Throwable) {
                Timber.e(throwable, "User selection: failed to cancel deletion, id=$id")
                emitError(
                    reason = UserSelectUiAction.ShowError.ErrorReason.CANCEL_DELETION_WRITE_FAILED,
                )
            }
        }
    }

    fun deleteUser(
        id: Long,
        name: String,
    ) {
        Timber.d("User selection: deletion confirmed, id=$id")
        viewModelScope.launch {
            try {
                val isDeletionRequested = userRepository.requestDeletion(id)
                if (!isDeletionRequested) {
                    Timber.w("User selection: user is no longer available for deletion, id=$id")
                    emitError(
                        reason = UserSelectUiAction.ShowError.ErrorReason.DELETE_USER_NOT_AVAILABLE,
                    )
                    return@launch
                }

                Timber.d("User selection: deletion requested, id=$id")
                emitAction(
                    action = UserSelectUiAction.ShowDeletionSnackbar(
                        id = id,
                        name = name,
                    ),
                )
            } catch (throwable: Throwable) {
                Timber.e(throwable, "User selection: failed to request deletion, id=$id")
                emitError(
                    reason = UserSelectUiAction.ShowError.ErrorReason.REQUEST_DELETION_WRITE_FAILED,
                )
            }
        }
    }

    fun finalizeDeletion(id: Long) {
        Timber.d("User selection: deletion snackbar dismissed, id=$id")
        viewModelScope.launch {
            try {
                val isDeletionFinalized = userRepository.finalizeDeletion(id)
                if (!isDeletionFinalized) {
                    Timber.w("User selection: deletion finalization deferred, id=$id")
                    return@launch
                }

                Timber.d("User selection: deletion finalized, id=$id")
            } catch (throwable: Throwable) {
                Timber.e(throwable, "User selection: failed to finalize deletion, id=$id")
            }
        }
    }

    fun editUser(id: Long) {
        Timber.d("User selection: edit user requested, id=$id")
        navigator.navigate(
            screen = AuthScreen.EditUser(
                userId = id,
            ),
        )
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
                    updateLoadError()
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

    private suspend fun emitAction(action: UserSelectUiAction) {
        _uiAction.emit(value = action)
    }

    private suspend fun emitError(reason: UserSelectUiAction.ShowError.ErrorReason) {
        emitAction(
            action = UserSelectUiAction.ShowError(
                reason = reason,
            ),
        )
    }

    private fun updateLoadError() {
        _uiState.value = UserSelectUiState.Error(
            reason = UserSelectUiState.Error.Reason.LOAD_USERS_FAILED,
            users = currentUsers(),
        )
    }
}
