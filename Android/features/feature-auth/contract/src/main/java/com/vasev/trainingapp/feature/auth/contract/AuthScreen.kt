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
     * Create / edit user screen ("about me"). One screen in two modes.
     * Экран создания / редактирования пользователя ("о себе"). Один экран в двух режимах.
     *
     * @param userId id of the user to edit; `null` for create mode.
     *   id пользователя для редактирования; `null` для режима создания.
     */
    data class Create(
        val userId: Long? = null,
    ) : AuthScreen
}
