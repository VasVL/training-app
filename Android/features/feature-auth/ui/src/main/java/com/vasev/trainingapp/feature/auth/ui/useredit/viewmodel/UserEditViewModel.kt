package com.vasev.trainingapp.feature.auth.ui.useredit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiAction
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.mapper.UserEditDomainMapper
import com.vasev.trainingapp.feature.auth.ui.useredit.mapper.UserEditUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    private val userEditDomainMapper: UserEditDomainMapper,
    private val userEditUiMapper: UserEditUiMapper,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _uiAction = MutableSharedFlow<UserEditUiAction>()
    private val _uiState = MutableStateFlow<UserEditUiState>(UserEditUiState.Loading)
    private var userIdToLoad: Long? = null

    val uiAction = _uiAction.asSharedFlow()
    val uiState = _uiState.asStateFlow()

    fun createFirstUser() {
        Timber.d("createFirstUser")
        val state = UserEditUiState.Ready(
            mode = UserEditUiState.Ready.Mode.CreateFirstUser,
            role = UserEditUiState.Ready.Role.OWNER,
        )
        _uiState.value = state
    }

    fun createNewUser() {
        Timber.d("createNewUser")
        val state = UserEditUiState.Ready(
            mode = UserEditUiState.Ready.Mode.CreateNewUser,
            role = UserEditUiState.Ready.Role.TRAINEE,
        )
        _uiState.value = state
    }

    fun discardChanges() {
        Timber.d("discardChanges")
        viewModelScope.launch {
            emitAction(action = UserEditUiAction.CloseScreen)
        }
    }

    fun loadUser(userId: Long) {
        Timber.d("loadUser: userId=$userId")
        userIdToLoad = userId
        viewModelScope.launch {
            loadUserById(userId = userId)
        }
    }

    fun reloadUser() {
        val userId = userIdToLoad
        if (userId == null) {
            Timber.d("reloadUser: result=IGNORED_NO_USER_ID")
            return
        }
        Timber.d("reloadUser: userId=$userId")
        viewModelScope.launch {
            loadUserById(userId = userId)
        }
    }

    fun requestExit() {
        Timber.d("requestExit")
        val state = uiState.value
        if (state !is UserEditUiState.Ready || !state.hasChanges) {
            viewModelScope.launch {
                emitAction(action = UserEditUiAction.CloseScreen)
            }
            return
        }

        val validationErrors = validateFields(state)
        _uiState.update { currentState ->
            val currentReadyState = currentState as? UserEditUiState.Ready
            if (currentReadyState == state) {
                currentReadyState.copy(validationErrors = validationErrors)
            } else {
                currentState
            }
        }
        viewModelScope.launch {
            emitAction(
                action = UserEditUiAction.ShowExitConfirmation(
                    isSaveAvailable = validationErrors.isEmpty(),
                ),
            )
        }
    }

    fun saveProfile(closeScreenAfterSave: Boolean = false) {
        Timber.d("saveProfile: closeScreenAfterSave=$closeScreenAfterSave")
        val state = uiState.value
        if (state !is UserEditUiState.Ready) {
            Timber.d("saveProfile: state=${state::class.simpleName}, result=IGNORED")
            return
        }
        if (!state.hasChanges) {
            Timber.d("saveProfile: result=NO_CHANGES")
            return
        }

        val validationErrors = validateFields(state)
        if (validationErrors.isNotEmpty()) {
            _uiState.update { currentState ->
                val currentReadyState = currentState as? UserEditUiState.Ready
                if (currentReadyState == state) {
                    currentReadyState.copy(validationErrors = validationErrors)
                } else {
                    currentState
                }
            }
            Timber.d("saveProfile: result=VALIDATION_FAILED")
            return
        }

        viewModelScope.launch {
            saveReadyProfile(
                closeScreenAfterSave = closeScreenAfterSave,
                state = state,
            )
        }
    }

    fun setBirthDate(birthDate: LocalDate?) {
        Timber.d("setBirthDate: birthDate=$birthDate")
        updateReady(
            transform = { currentState -> currentState.copy(birthDate = birthDate) },
            validationError = UserEditUiState.Ready.ValidationError.BIRTH_DATE_FUTURE,
            validationErrorPresent = birthDate?.isAfter(LocalDate.now()) == true,
        )
    }

    fun setGender(gender: UserEditUiState.Ready.Gender) {
        Timber.d("setGender: gender=$gender")
        updateReady(
            transform = { currentState -> currentState.copy(gender = gender) },
        )
    }

    fun setHeight(heightInput: String) {
        Timber.d("setHeight: heightInput=$heightInput")
        updateReady(
            transform = { currentState -> currentState.copy(heightInput = heightInput) },
            validationError = UserEditUiState.Ready.ValidationError.HEIGHT_INVALID,
            validationErrorPresent = heightInput.isNotBlank() && !isPositiveDecimal(heightInput),
        )
    }

    fun setName(nameInput: String) {
        Timber.d("setName: nameInput=$nameInput")
        updateReady(
            transform = { currentState -> currentState.copy(nameInput = nameInput) },
            validationError = UserEditUiState.Ready.ValidationError.NAME_REQUIRED,
            validationErrorPresent = nameInput.isBlank(),
        )
    }

    fun setWeight(weightInput: String) {
        Timber.d("setWeight: weightInput=$weightInput")
        updateReady(
            transform = { currentState -> currentState.copy(weightInput = weightInput) },
            validationError = UserEditUiState.Ready.ValidationError.WEIGHT_INVALID,
            validationErrorPresent = weightInput.isNotBlank() && !isPositiveDecimal(weightInput),
        )
    }

    private fun validateFields(
        state: UserEditUiState.Ready,
    ): Set<UserEditUiState.Ready.ValidationError> {
        return buildSet {
            if (state.birthDate?.isAfter(LocalDate.now()) == true) {
                add(UserEditUiState.Ready.ValidationError.BIRTH_DATE_FUTURE)
            }
            if (state.heightInput.isNotBlank() && !isPositiveDecimal(state.heightInput)) {
                add(UserEditUiState.Ready.ValidationError.HEIGHT_INVALID)
            }
            if (state.nameInput.isBlank()) {
                add(UserEditUiState.Ready.ValidationError.NAME_REQUIRED)
            }
            if (state.weightInput.isNotBlank() && !isPositiveDecimal(state.weightInput)) {
                add(UserEditUiState.Ready.ValidationError.WEIGHT_INVALID)
            }
        }
    }

    private fun isPositiveDecimal(input: String): Boolean {
        return input.replace(',', '.')
            .toDoubleOrNull()
            ?.takeIf(Double::isFinite)
            ?.let { value -> value > 0.0 }
            ?: false
    }

    private suspend fun loadUserById(userId: Long) {
        _uiState.value = UserEditUiState.Loading
        try {
            val user = userRepository.getById(userId)
            if (user == null) {
                Timber.w("loadUserById: userId=$userId, result=USER_NOT_FOUND")
                _uiState.value = UserEditUiState.Error(
                    reason = UserEditUiState.Error.Reason.USER_NOT_FOUND,
                )
                return
            }

            val state = userEditUiMapper.map(
                mode = UserEditUiState.Ready.Mode.EditUser(userId = userId),
                user = user,
            ).copy(isSaved = true)
            _uiState.value = state
            Timber.d("loadUserById: userId=$userId, result=LOADED")
        } catch (throwable: Throwable) {
            Timber.e(throwable, "loadUserById: userId=$userId, result=LOAD_FAILED")
            _uiState.value = UserEditUiState.Error(
                reason = UserEditUiState.Error.Reason.LOAD_USER_FAILED,
            )
        }
    }

    private suspend fun saveReadyProfile(
        closeScreenAfterSave: Boolean,
        state: UserEditUiState.Ready,
    ) {
        try {
            when (val mode = state.mode) {
                is UserEditUiState.Ready.Mode.CreateFirstUser,
                is UserEditUiState.Ready.Mode.CreateNewUser,
                -> {
                    userRepository.insert(
                        user = userEditDomainMapper.mapNew(
                            createdAt = System.currentTimeMillis(),
                            isDefault = mode is UserEditUiState.Ready.Mode.CreateFirstUser,
                            state = state,
                        ),
                    )
                }

                is UserEditUiState.Ready.Mode.EditUser -> {
                    val user = userRepository.getById(id = mode.userId)
                    if (user == null) {
                        Timber.w("saveReadyProfile: userId=${mode.userId}, result=USER_NOT_FOUND")
                        _uiState.update { currentState ->
                            if (currentState == state) {
                                UserEditUiState.Error(
                                    reason = UserEditUiState.Error.Reason.USER_NOT_FOUND,
                                )
                            } else {
                                currentState
                            }
                        }
                        return
                    }

                    userRepository.update(
                        user = userEditDomainMapper.mapUpdated(
                            state = state,
                            user = user,
                        ),
                    )
                }
            }
            _uiState.update { currentState ->
                val currentReadyState = currentState as? UserEditUiState.Ready
                if (currentReadyState == state) {
                    currentReadyState.copy(
                        hasChanges = false,
                        isSaved = true,
                        validationErrors = emptySet(),
                    )
                } else {
                    currentState
                }
            }
            Timber.d("saveReadyProfile: result=SAVED")
            if (closeScreenAfterSave) {
                emitAction(action = UserEditUiAction.CloseScreen)
            }
        } catch (throwable: Throwable) {
            Timber.e(throwable, "saveReadyProfile: result=SAVE_FAILED")
            emitAction(
                action = UserEditUiAction.ShowError(
                    reason = UserEditUiAction.ShowError.ErrorReason.SAVE_PROFILE_FAILED,
                ),
            )
        }
    }

    private suspend fun emitAction(action: UserEditUiAction) {
        _uiAction.emit(value = action)
    }

    private fun updateReady(
        transform: (UserEditUiState.Ready) -> UserEditUiState.Ready,
        validationError: UserEditUiState.Ready.ValidationError? = null,
        validationErrorPresent: Boolean = false,
    ) {
        _uiState.update { state ->
            if (state !is UserEditUiState.Ready) {
                Timber.d("updateReady: state=${state::class.simpleName}, result=IGNORED")
                return@update state
            }

            val updatedState = transform(state)
            updatedState.copy(
                hasChanges = true,
                isSaved = false,
                validationErrors = state.validationErrors.updateValidationErrors(
                    validationError = validationError,
                    validationErrorPresent = validationErrorPresent,
                ),
            )
        }
    }

    private fun Set<UserEditUiState.Ready.ValidationError>.updateValidationErrors(
        validationError: UserEditUiState.Ready.ValidationError?,
        validationErrorPresent: Boolean,
    ): Set<UserEditUiState.Ready.ValidationError> {
        if (validationError == null) {
            return this
        }

        return if (validationErrorPresent) {
            this + validationError
        } else {
            this - validationError
        }
    }

}
