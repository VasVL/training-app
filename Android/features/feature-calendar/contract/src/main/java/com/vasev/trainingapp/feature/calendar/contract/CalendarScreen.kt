package com.vasev.trainingapp.feature.calendar.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the calendar feature.
 * Маршруты экранов фичи calendar (календарь).
 *
 * See APP_DESIGN.md, section "4. Календарь".
 * См. APP_DESIGN.md, раздел "4. Календарь".
 */
sealed interface CalendarScreen : Screen {

    /**
     * Calendar screen with completed and planned workouts.
     * Экран календаря с пройденными и запланированными тренировками.
     */
    data object Calendar : CalendarScreen
}
