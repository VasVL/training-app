package com.vasev.trainingapp.core.database.entity.projection

import com.vasev.trainingapp.core.database.entity.types.UserRole

/**
 * Minimal user fields required to render and select an item in the user list.
 * Минимальные поля пользователя, нужные для отображения и выбора элемента списка пользователей.
 */
data class UserListItemProjection(
    val id: Long,
    val isDefault: Boolean,
    val name: String,
    val role: UserRole,
)
