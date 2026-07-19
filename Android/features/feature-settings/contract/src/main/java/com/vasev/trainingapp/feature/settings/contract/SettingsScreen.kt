package com.vasev.trainingapp.feature.settings.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the settings feature, including the launcher hub of all
 * app screens.
 * Маршруты экранов фичи settings, включая хаб-лаунчер всех экранов приложения.
 *
 * See APP_DESIGN.md, "Шторка (Navigation Drawer): Настройки" and
 * "Bottom navigation" (customizable set of 5 screens — second phase).
 * См. APP_DESIGN.md, "Шторка (Navigation Drawer): Настройки" и
 * "Bottom navigation" (настраиваемый набор из 5 экранов — вторая фаза).
 */
sealed interface SettingsScreen : Screen {

    /**
     * Settings screen.
     * Экран настроек.
     */
    data object Settings : SettingsScreen

    /**
     * Launcher hub: list of all app screens. Lets the user navigate to any
     * screen and (in the future) pick the 5 shown on the main screen.
     * Хаб-лаунчер: список всех экранов приложения. Позволяет перейти в любой
     * экран и (в будущем) выбрать 5 для главного экрана.
     *
     * For MVP it is a fallback/dev entry point to every screen while the
     * customizable bottom navigation is not yet implemented.
     * Для MVP это запасная/dev-точка входа на каждый экран, пока настраиваемая
     * нижняя навигация ещё не реализована.
     */
    data object AllScreens : SettingsScreen
}
