package com.vasev.trainingapp.feature.auth.ui.useredit.entity

/**
 * One-time actions emitted by the profile editing screen.
 * Одноразовые действия, отправляемые экраном редактирования профиля.
 */
internal sealed interface UserEditUiAction {

    /**
     * Closes the profile editing screen.
     * Закрывает экран редактирования профиля.
     */
    data object CloseScreen : UserEditUiAction

    /**
     * Shows an error that should be handled once by the container.
     * Показывает ошибку, которую контейнер должен обработать один раз.
     */
    data class ShowError(
        val reason: ErrorReason,
    ) : UserEditUiAction {

        /**
         * User-visible reasons for a failed profile operation.
         * Причины ошибки операции с профилем, понятные пользователю.
         */
        enum class ErrorReason {
            SAVE_PROFILE_FAILED,
        }
    }

    /**
     * Requests confirmation before leaving a profile with changes.
     * Запрашивает подтверждение перед выходом из профиля с изменениями.
     */
    data class ShowExitConfirmation(
        val isSaveAvailable: Boolean,
    ) : UserEditUiAction
}
