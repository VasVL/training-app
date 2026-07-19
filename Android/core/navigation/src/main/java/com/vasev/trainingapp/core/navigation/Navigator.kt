package com.vasev.trainingapp.core.navigation

/**
 * Port for navigation between screens.
 * Порт навигации между экранами.
 *
 * Declared in `core/navigation` so feature modules depend only on this interface
 * and never know about each other or about the concrete navigation framework.
 * Объявлен в `core/navigation`, чтобы feature-модули зависели только от этого интерфейса
 * и никогда не знали друг о друге или о конкретном навигационном фреймворке.
 *
 * The implementation lives in the `app` module (where the NavController is available)
 * and is injected via Hilt. ViewModels can trigger navigation through this port,
 * keeping the UI layer free of "where to navigate" decisions.
 * Реализация живёт в модуле `app` (где доступен NavController) и инжектируется через Hilt.
 * ViewModel может инициировать навигацию через этот порт, оставляя UI-слой
 * свободным от решений "куда перейти".
 */
interface Navigator {

    /**
     * Navigate to the given [screen].
     * Перейти на указанный [screen].
     */
    fun navigate(screen: Screen)

    /**
     * Go back to the previous screen.
     * Вернуться на предыдущий экран.
     */
    fun back()

    /**
     * Pop the back stack up to the given [screen].
     * Очистить стек до экрана [screen].
     *
     * @param screen the destination screen that stays on top of the stack.
     *   Экран-назначение, который остаётся на вершине стека.
     * @param inclusive when `false` (default) [screen] stays on top of the stack;
     *   when `true` [screen] is also popped (useful to remove onboarding/login screens
     *   after a successful flow). Mirrors `NavController.popBackStack(route, inclusive)`.
     *   При `false` (по умолчанию) [screen] остаётся на вершине стека;
     *   при `true` [screen] также удаляется (полезно для выкидывания экранов
     *   онбординга/логина после успешного флоу). Аналог `NavController.popBackStack(route, inclusive)`.
     */
    fun popUpTo(
        screen: Screen,
        inclusive: Boolean = false,
    )
}
