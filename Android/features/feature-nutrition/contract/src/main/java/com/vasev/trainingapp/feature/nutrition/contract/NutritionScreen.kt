package com.vasev.trainingapp.feature.nutrition.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Nutrition feature screen routes / Маршруты экранов фичи питания
 *
 * Each object/data class is a navigation destination implementing the [Screen] marker interface /
 * Каждый object/data class — пункт навигации, реализующий маркерный интерфейс [Screen]
 */
sealed interface NutritionScreen : Screen {

    data object List : NutritionScreen

    data class Detail(val foodItemId: Long) : NutritionScreen

    data object Log : NutritionScreen
}
