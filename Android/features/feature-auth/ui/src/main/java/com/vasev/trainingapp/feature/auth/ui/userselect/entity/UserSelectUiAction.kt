package com.vasev.trainingapp.feature.auth.ui.userselect.entity

/**
 * One-time actions emitted by the user selection screen.
 * Одноразовые действия, отправляемые экраном выбора пользователя.
 */
internal sealed interface UserSelectUiAction {

    /**
     * Requests confirmation before deleting a user profile.
     * Запрашивает подтверждение перед удалением профиля пользователя.
     */
    data class ShowDeletionConfirmation(
        val id: Long,
        val name: String,
    ) : UserSelectUiAction

    /**
     * Shows a reversible deletion message for a user profile.
     * Показывает сообщение об обратимом удалении профиля пользователя.
     */
    data class ShowDeletionSnackbar(
        val id: Long,
        val name: String,
    ) : UserSelectUiAction

    /**
     * Shows a message for an operation that could not be completed.
     * Показывает сообщение об операции, которую не удалось выполнить.
     */
    data class ShowError(
        val reason: ErrorReason,
    ) : UserSelectUiAction {

        /**
         * User-visible reasons for an action error.
         * Причины ошибки действия, понятные пользователю.
         */
        enum class ErrorReason {
            ACTIVE_USER_NOT_FOUND,
            ACTIVATE_USER_WRITE_FAILED,
            CANCEL_DELETION_WRITE_FAILED,
            DELETE_USER_NOT_AVAILABLE,
            REQUEST_DELETION_WRITE_FAILED,
        }
    }
}
