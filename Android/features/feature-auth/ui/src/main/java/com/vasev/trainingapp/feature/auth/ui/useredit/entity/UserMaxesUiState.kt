package com.vasev.trainingapp.feature.auth.ui.useredit.entity

/**
 * State of the personal maximums tab.
 * Состояние вкладки личных максимумов.
 */
internal sealed interface UserMaxesUiState {

    /**
     * Personal maximums are loading.
     * Личные максимумы загружаются.
     */
    data object Loading : UserMaxesUiState

    /**
     * Personal maximums could not be loaded.
     * Личные максимумы не удалось загрузить.
     */
    data class Error(
        val reason: Reason,
    ) : UserMaxesUiState {

        /**
         * Reasons why the maximums tab cannot be displayed.
         * Причины, по которым вкладку максимумов нельзя отобразить.
         */
        enum class Reason {
            LOAD_MAXIMUMS_FAILED,
        }
    }

    /**
     * Personal maximums are ready for display.
     * Личные максимумы готовы к отображению.
     */
    sealed interface Ready : UserMaxesUiState {

        /**
         * The profile has not been saved yet, so results cannot be added.
         * Профиль ещё не сохранён, поэтому результаты пока нельзя добавить.
         */
        data object NewProfile : Ready

        /**
         * Personal results are ready for display.
         * Личные результаты готовы к отображению.
         */
        data class Content(
            val maximums: List<Maximum>,
        ) : Ready {

            /**
             * UI data required by one personal maximum row.
             * UI-данные, нужные одной строке личного максимума.
             */
            data class Maximum(
                val dateText: String,
                val exerciseName: String,
                val id: Long,
                val valueText: String,
            )
        }

    }
}
