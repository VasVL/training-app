package com.vasev.trainingapp.feature.auth.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the auth feature (user selection / "about me").
 * Маршруты экранов фичи auth (выбор пользователя / "о себе").
 *
 * See APP_DESIGN.md, section "Пользователи и вход".
 * См. APP_DESIGN.md, раздел "Пользователи и вход".
 */
sealed interface AuthScreen : Screen {

    /**
     * User selection screen: pick an existing user or create a new one.
     * Экран выбора пользователя: выбрать существующего или создать нового.
     */
    data object Select : AuthScreen

    /**
     * Create the first user profile on the first app start.
     * Создать первый профиль пользователя при первом запуске приложения.
     */
    data object CreateFirstUser : AuthScreen

    /**
     * Create an additional user profile from the user list.
     * Создать дополнительный профиль пользователя из списка пользователей.
     */
    data object CreateNewUser : AuthScreen

    /**
     * Edit an existing user profile.
     * Редактировать существующий профиль пользователя.
     *
     * @param userId id of the user to edit.
     *   id пользователя для редактирования.
     */
    data class EditUser(
        val userId: Long,
    ) : AuthScreen
}
