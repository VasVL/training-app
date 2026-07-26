package com.vasev.trainingapp.feature.auth.data.mapper

import com.vasev.trainingapp.core.database.entity.projection.UserListItemProjection
import com.vasev.trainingapp.core.database.entity.types.UserRole as EntityUserRole
import com.vasev.trainingapp.feature.auth.domain.entity.UserListItem
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import javax.inject.Inject

/**
 * Maps the user-list database projection to the domain data required by user selection.
 * Преобразует database-проекцию списка пользователей в domain-данные для выбора пользователя.
 *
 * `@Inject` — Hilt creates this stateless mapper for the repository.
 * `@Inject` — Hilt создаёт этот маппер без состояния для репозитория.
 */
internal class UserListItemMapper @Inject constructor() {

    fun map(projection: UserListItemProjection): UserListItem {
        return UserListItem(
            id = projection.id,
            isDefault = projection.isDefault,
            name = projection.name,
            role = mapRole(projection.role),
        )
    }

    private fun mapRole(role: EntityUserRole): UserRole {
        return when (role) {
            EntityUserRole.OWNER -> UserRole.OWNER
            EntityUserRole.TRAINEE -> UserRole.TRAINEE
        }
    }
}
