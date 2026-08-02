package com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasev.trainingapp.feature.auth.domain.repository.UserMaxRepository
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserMaxesUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.mapper.UserMaxesUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

/**
 * ViewModel for the personal results tab.
 * ViewModel вкладки личных результатов.
 *
 * `@HiltViewModel` — Hilt creates this ViewModel and provides its dependencies.
 * `@HiltViewModel` — Hilt создаёт эту ViewModel и предоставляет её зависимости.
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
internal class UserMaxesViewModel @Inject constructor(
    private val userMaxRepository: UserMaxRepository,
    private val userMaxesUiMapper: UserMaxesUiMapper,
) : ViewModel() {

    private val userId = MutableStateFlow<Long?>(null)
    private val retryRequests = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    val uiState = userId
        .flatMapLatest { currentUserId ->
            retryRequests
                .onStart { emit(Unit) }
                .flatMapLatest { uiStateFor(currentUserId) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = UserMaxesUiState.Loading,
        )

    fun setUser(userId: Long?) {
        Timber.d("User maximums: set profile id=$userId")
        this.userId.value = userId
    }

    fun retry() {
        Timber.d("User maximums: retry loading results")
        retryRequests.tryEmit(Unit)
    }

    private fun uiStateFor(userId: Long?): Flow<UserMaxesUiState> {
        if (userId == null) {
            return flowOf(UserMaxesUiState.Ready.NewProfile)
        }

        return userMaxRepository.observeForUserProfile(userId)
            .map { maximums ->
                val uiMaximums = userMaxesUiMapper.map(maximums)
                Timber.d("User maximums: results updated, count=${uiMaximums.size}")
                val uiState: UserMaxesUiState = UserMaxesUiState.Ready.Content(
                    maximums = uiMaximums,
                )
                uiState
            }
            .catch { throwable ->
                Timber.e(throwable, "User maximums: failed to observe results")
                emit(
                    UserMaxesUiState.Error(
                        reason = UserMaxesUiState.Error.Reason.LOAD_MAXIMUMS_FAILED,
                    ),
                )
            }
    }
}
