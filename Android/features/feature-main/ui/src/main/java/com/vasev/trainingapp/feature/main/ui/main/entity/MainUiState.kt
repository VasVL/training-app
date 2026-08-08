package com.vasev.trainingapp.feature.main.ui.main.entity

/**
 * Main-shell state rendered by the UI.
 * Состояние главной оболочки, отображаемое UI.
 */
sealed interface MainUiState {

    /**
     * Active profile is being loaded.
     * Активный профиль загружается.
     */
    data object Loading : MainUiState

    /**
     * Active profile was loaded.
     * Активный профиль загружен.
     *
     * @param activeUser active profile data for the drawer and navigation.
     *   Данные активного профиля для шторки и навигации.
     */
    data class Ready(
        val activeUser: ActiveUser,
    ) : MainUiState {

        /**
         * UI data required for the active profile entry.
         * UI-данные, нужные для пункта активного профиля.
         *
         * @param id identifier used to open profile editing.
         *   Идентификатор для открытия редактирования профиля.
         * @param name name displayed in the drawer.
         *   Имя, отображаемое в шторке.
         */
        data class ActiveUser(
            val id: Long,
            val name: String,
        )
    }

    /**
     * Active profile could not be observed.
     * Не удалось получить активный профиль.
     *
     * @param reason concrete UI reason used to select a localized message.
     *   Конкретная UI-причина для выбора локализованного сообщения.
     */
    data class Error(
        val reason: ErrorReason,
    ) : MainUiState {

        /**
         * Reasons that can be shown for the main-shell loading failure.
         * Причины, которые можно показать при ошибке загрузки главной оболочки.
         */
        enum class ErrorReason {
            LOAD_ACTIVE_USER_FAILED,
        }
    }
}
