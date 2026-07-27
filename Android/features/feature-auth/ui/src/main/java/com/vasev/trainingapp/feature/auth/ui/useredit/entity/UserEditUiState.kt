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
        val heightInput: String = "",
        val heightUnit: HeightUnit = HeightUnit.UNKNOWN,
        val nameInput: String = "",
        val role: Role = Role.UNKNOWN,
        val weightInput: String = "",
        val weightUnit: WeightUnit = WeightUnit.UNKNOWN,
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
            UNKNOWN,
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
         * Weight units available in the profile form.
         * Единицы веса, доступные в форме профиля.
         */
        enum class WeightUnit {
            KILOGRAMS,
            POUNDS,
            UNKNOWN,
        }
    }
}
