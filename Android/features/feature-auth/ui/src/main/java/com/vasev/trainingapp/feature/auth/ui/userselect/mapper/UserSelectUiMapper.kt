package com.vasev.trainingapp.feature.auth.ui.userselect.mapper

import com.vasev.trainingapp.feature.auth.domain.entity.UserListItem
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import com.vasev.trainingapp.feature.auth.ui.userselect.entity.UserSelectUiState
import javax.inject.Inject

/**
 * Maps user-selection domain data to screen-specific UI data.
 * Преобразует domain-данные выбора пользователя в UI-данные конкретного экрана.
 *
 * `@Inject` — Hilt creates this stateless mapper for the ViewModel.
 * `@Inject` — Hilt создаёт этот маппер без состояния для ViewModel.
 */
internal class UserSelectUiMapper @Inject constructor() {

    fun map(users: List<UserListItem>): List<UserSelectUiState.Ready.UserItem> {
        return users.map { user ->
            UserSelectUiState.Ready.UserItem(
                id = user.id,
                isActive = user.isDefault,
                name = user.name,
                role = mapRole(user.role),
            )
        }
    }

    private fun mapRole(role: UserRole): UserSelectUiState.Ready.UserItem.Role {
        return when (role) {
            UserRole.OWNER -> UserSelectUiState.Ready.UserItem.Role.OWNER
            UserRole.TRAINEE -> UserSelectUiState.Ready.UserItem.Role.TRAINEE
        }
    }
}
