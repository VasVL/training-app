package com.vasev.trainingapp.feature.weight.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the weight tracking feature.
 * Маршруты экранов фичи weight (отслеживание веса).
 *
 * See APP_DESIGN.md, section "5. Отслеживание веса".
 * См. APP_DESIGN.md, раздел "5. Отслеживание веса".
 */
sealed interface WeightScreen : Screen {

    /**
     * Weight tracker screen: current weight + change graph.
     * Экран отслеживания веса: текущий вес + график изменений.
     */
    data object Tracker : WeightScreen

    /**
     * Weight log screen: list of date-weight measurements.
     * Экран измерений веса: список дата-вес.
     */
    data object Log : WeightScreen
}
