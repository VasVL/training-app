package com.vasev.trainingapp.feature.auth.ui.useredit.entity

import java.time.LocalDate

/**
 * State of the user profile editing screen.
 * Состояние экрана редактирования профиля пользователя.
 */
internal sealed interface UserEditUiState {

    /**
     * Profile data is loading.
     * Данные профиля загружаются.
     */
    data object Loading : UserEditUiState

    /**
     * Profile data could not be loaded or saved.
     * Не удалось загрузить или сохранить данные профиля.
     */
    data class Error(
        val reason: Reason,
    ) : UserEditUiState {

        /**
         * Reasons why the profile screen cannot be displayed.
         * Причины, по которым экран профиля нельзя отобразить.
         */
        enum class Reason {
            LOAD_USER_FAILED,
            USER_NOT_FOUND,
        }
    }

    /**
     * Profile data is ready for viewing and editing.
     * Данные профиля готовы для просмотра и редактирования.
     */
    data class Ready(
        val birthDate: LocalDate? = null,
        val gender: Gender = Gender.UNKNOWN,
        val hasChanges: Boolean = false,
        val heightInput: String = "",
        val heightUnit: HeightUnit = HeightUnit.CENTIMETERS,
        val isSaved: Boolean = false,
        val mode: Mode,
        val nameInput: String = "",
        val role: Role = Role.UNKNOWN,
        val validationErrors: Set<ValidationError> = emptySet(),
        val weightInput: String = "",
        val weightUnit: WeightUnit = WeightUnit.KILOGRAMS,
    ) : UserEditUiState {

        /**
         * Gender values available in the profile form.
         * Значения пола, доступные в форме профиля.
         */
        enum class Gender {
            FEMALE,
            MALE,
            UNKNOWN,
        }

        /**
         * Height units available in the profile form.
         * Единицы роста, доступные в форме профиля.
         */
        enum class HeightUnit {
            CENTIMETERS,
            INCHES,
        }

        /**
         * Scenarios supported by the profile screen.
         * Сценарии, поддерживаемые экраном профиля.
         */
        sealed interface Mode {

            /**
             * The first user and the profile owner are being created.
             * Создаётся первый пользователь и владелец профиля.
             */
            data object CreateFirstUser : Mode

            /**
             * An additional trainee profile is being created.
             * Создаётся дополнительный профиль занимающегося.
             */
            data object CreateNewUser : Mode

            /**
             * An existing profile is being edited.
             * Редактируется существующий профиль.
             */
            data class EditUser(
                val userId: Long,
            ) : Mode
        }

        /**
         * Roles available in the profile form.
         * Роли, доступные в форме профиля.
         */
        enum class Role {
            OWNER,
            TRAINEE,
            UNKNOWN,
        }

        /**
         * Validation errors currently present in the profile form.
         * Ошибки валидации, которые сейчас есть в форме профиля.
         */
        enum class ValidationError {
            BIRTH_DATE_FUTURE,
            HEIGHT_INVALID,
            NAME_REQUIRED,
            WEIGHT_INVALID,
        }

        /**
         * Weight units available in the profile form.
         * Единицы веса, доступные в форме профиля.
         */
        enum class WeightUnit {
            KILOGRAMS,
            POUNDS,
        }
    }
}
