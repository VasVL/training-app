package com.vasev.trainingapp.feature.main.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasev.trainingapp.core.navigation.Navigator
import com.vasev.trainingapp.feature.auth.contract.ActiveUserProvider
import com.vasev.trainingapp.feature.auth.contract.AuthScreen
import com.vasev.trainingapp.feature.auth.contract.entity.ActiveUserSummary
import com.vasev.trainingapp.feature.main.ui.main.entity.MainUiState
import com.vasev.trainingapp.feature.main.ui.main.mapper.MainActiveUserMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

/**
 * Owns the state and navigation decisions of the main application shell.
 * Владеет состоянием и навигационными решениями главной оболочки приложения.
 *
 * `@HiltViewModel` — Hilt creates this ViewModel and retains it with the screen lifecycle.
 * `@HiltViewModel` — Hilt создаёт эту ViewModel и сохраняет её в жизненном цикле экрана.
 *
 * `@Inject` — Hilt supplies all constructor dependencies.
 * `@Inject` — Hilt передаёт все зависимости конструктора.
 */
@HiltViewModel
internal class MainViewModel @Inject constructor(
    private val activeUserMapper: MainActiveUserMapper,
    private val activeUserProvider: ActiveUserProvider,
    private val navigator: Navigator,
) : ViewModel() {

    private val reloadActiveUserRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState: StateFlow<MainUiState> = observeActiveUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MainUiState.Loading,
        )

    fun openActiveProfile() {
        Timber.d("openActiveProfile")
        when (val state = uiState.value) {
            is MainUiState.Error -> {
                Timber.w("openActiveProfile: result=IGNORED_ERROR_STATE")
            }

            is MainUiState.Loading -> {
                Timber.w("openActiveProfile: result=IGNORED_LOADING_STATE")
            }

            is MainUiState.Ready -> {
                navigator.navigate(AuthScreen.EditUser(userId = state.activeUser.id))
            }
        }
    }

    fun openUserSelection() {
        Timber.d("openUserSelection")
        navigator.navigate(AuthScreen.Select)
    }

    fun reloadActiveUser() {
        val accepted = reloadActiveUserRequests.tryEmit(Unit)
        Timber.d("reloadActiveUser: accepted=$accepted")
    }

    private fun observeActiveUser(): Flow<MainUiState> {
        Timber.d("observeActiveUser")
        return activeUserProvider.observeActiveUser()
            .distinctUntilChanged()
            .onEach { activeUser ->
                if (activeUser == null) {
                    Timber.w("observeActiveUser: result=ACTIVE_USER_NOT_FOUND")
                    openUserSelection()
                }
            }
            .filterNotNull()
            .map(::mapActiveUser)
            .retryWhen { throwable, attempt ->
                Timber.e(
                    throwable,
                    "observeActiveUser: attempt=$attempt, result=LOAD_ACTIVE_USER_FAILED",
                )
                emit(
                    MainUiState.Error(
                        reason = MainUiState.Error.ErrorReason.LOAD_ACTIVE_USER_FAILED,
                    ),
                )
                reloadActiveUserRequests.first()
                true
            }
    }

    private fun mapActiveUser(activeUser: ActiveUserSummary): MainUiState {
        return MainUiState.Ready(
            activeUser = activeUserMapper.map(activeUser),
        )
    }
}
