package com.vasev.trainingapp.feature.anatomy.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the anatomy atlas feature.
 * Маршруты экранов фичи anatomy (анатомический атлас).
 *
 * See APP_DESIGN.md, "Дополнительные экраны" (Анатомический атлас).
 * См. APP_DESIGN.md, "Дополнительные экраны" (Анатомический атлас).
 */
sealed interface AnatomyScreen : Screen {

    /**
     * Atlas screen: list of muscles / human model.
     * Экран атласа: список мышц / модель человека.
     */
    data object Atlas : AnatomyScreen

    /**
     * Muscle detail screen: muscle info + suitable exercises.
     * Экран детали мышцы: информация о мышце + подходящие упражнения.
     *
     * @param muscleId id of the muscle to show.
     *   id мышцы для отображения.
     */
    data class MuscleDetail(
        val muscleId: Long,
    ) : AnatomyScreen
}
