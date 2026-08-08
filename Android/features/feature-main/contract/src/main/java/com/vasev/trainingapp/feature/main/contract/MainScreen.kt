package com.vasev.trainingapp.feature.main.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Public routes owned by the main application-shell feature.
 * Публичные маршруты фичи главной оболочки приложения.
 */
sealed interface MainScreen : Screen {

    /**
     * Persistent application shell displayed for an active profile.
     * Постоянная оболочка приложения, отображаемая для активного профиля.
     */
    data object Main : MainScreen
}
