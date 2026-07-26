package com.vasev.trainingapp.feature.auth.domain.entity

import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole

/**
 * User fields required by the user-selection scenario.
 * Поля пользователя, нужные сценарию выбора пользователя.
 */
data class UserListItem(
    val id: Long,
    val isDefault: Boolean,
    val name: String,
    val role: UserRole,
)
