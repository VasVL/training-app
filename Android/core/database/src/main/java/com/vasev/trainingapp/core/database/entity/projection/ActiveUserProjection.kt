package com.vasev.trainingapp.core.database.entity.projection

/**
 * Minimal user fields required by consumers of the active profile.
 * Минимальные поля пользователя, нужные потребителям активного профиля.
 */
data class ActiveUserProjection(
    val id: Long,
    val name: String,
)
