package com.vasev.trainingapp.core.navigation

/**
 * Base marker interface for all screen routes in the app.
 * Базовый маркерный интерфейс для всех маршрутов экранов в приложении.
 *
 * Each feature declares its own screens in its contract-module as
 * `sealed interface XxxScreen : Screen` with objects/data classes for concrete routes.
 * Каждая фича объявляет свои экраны в своём contract-модуле как
 * `sealed interface XxxScreen : Screen` с объектами/data class для конкретных маршрутов.
 *
 * The app module depends on all contract-modules and implements [Navigator],
 * mapping each [Screen] to a concrete navigation action (NavDirections).
 * Модуль app зависит от всех contract-модулей и реализует [Navigator],
 * маппя каждый [Screen] в конкретное действие навигации (NavDirections).
 */
interface Screen
