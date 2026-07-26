package com.vasev.trainingapp.feature.auth.ui.userselect.entity

/**
 * States rendered by the user-selection screen.
 * Состояния, отображаемые экраном выбора пользователя.
 */
internal sealed interface UserSelectUiState {

    /**
     * State while the user list is loading.
     * Состояние во время загрузки списка пользователей.
     */
    data object Loading : UserSelectUiState

    /**
     * State when loading or selecting a user has failed.
     * Состояние, когда загрузка или выбор пользователя завершились ошибкой.
     */
    data class Error(
        val message: String,
        val users: List<Ready.UserItem>,
    ) : UserSelectUiState

    /**
     * State with data ready for rendering the user list.
     * Состояние с данными, готовыми для отображения списка пользователей.
     */
    data class Ready(
        val users: List<UserItem>,
    ) : UserSelectUiState {

        /**
         * UI data required by one user-list row.
         * UI-данные, нужные одной строке списка пользователей.
         */
        data class UserItem(
            val id: Long,
            val isActive: Boolean,
            val name: String,
            val role: Role,
        ) {

            /**
             * Role labels displayed by the user-selection screen.
             * Подписи роли, отображаемые экраном выбора пользователя.
             */
            enum class Role {
                OWNER,
                TRAINEE,
            }
        }
    }
}
