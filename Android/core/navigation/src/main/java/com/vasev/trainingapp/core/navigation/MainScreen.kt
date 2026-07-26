package com.vasev.trainingapp.core.navigation

/**
 * Application-level screen routes that do not belong to a feature.
 * Маршруты экранов уровня приложения, которые не принадлежат конкретной фиче.
 */
sealed interface MainScreen : Screen {

    /**
     * Temporary empty main screen shown after an active user has been selected.
     * Временный пустой главный экран, показываемый после выбора активного пользователя.
     */
    data object Main : MainScreen
}
