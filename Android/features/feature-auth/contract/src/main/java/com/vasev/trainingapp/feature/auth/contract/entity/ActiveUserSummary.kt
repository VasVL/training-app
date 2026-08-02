package com.vasev.trainingapp.feature.auth.contract.entity

/**
 * Public non-sensitive data about the currently active user profile.
 * Публичные нечувствительные данные о текущем активном профиле пользователя.
 *
 * @param id identifier used to navigate to the profile.
 *   Идентификатор для перехода к профилю.
 * @param name name displayed in the application shell.
 *   Имя, отображаемое в оболочке приложения.
 */
data class ActiveUserSummary(
    val id: Long,
    val name: String,
)
