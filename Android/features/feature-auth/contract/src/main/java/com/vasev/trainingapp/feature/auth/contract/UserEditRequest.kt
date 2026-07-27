package com.vasev.trainingapp.feature.auth.contract

import java.io.Serializable

/**
 * Type-safe request for opening the user profile editing screen.
 * Типобезопасный запрос на открытие экрана редактирования профиля пользователя.
 */
sealed interface UserEditRequest : Serializable {

    /**
     * Creates the first owner profile.
     * Создаёт первый профиль владельца.
     */
    data object CreateFirstUser : UserEditRequest

    /**
     * Creates an additional trainee profile.
     * Создаёт дополнительный профиль подопечного.
     */
    data object CreateNewUser : UserEditRequest

    /**
     * Opens an existing profile for editing.
     * Открывает существующий профиль для редактирования.
     *
     * @param userId identifier of the profile to edit.
     *   идентификатор редактируемого профиля.
     */
    data class EditUser(
        val userId: Long,
    ) : UserEditRequest
}
