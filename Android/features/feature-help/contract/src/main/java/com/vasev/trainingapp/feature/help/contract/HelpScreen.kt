package com.vasev.trainingapp.feature.help.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the help feature (SRC info, about app).
 * Маршруты экранов фичи help (информация о СРЦ, о приложении).
 *
 * See APP_DESIGN.md, "Дополнительные экраны" (СРЦ, о приложении).
 * См. APP_DESIGN.md, "Дополнительные экраны" (СРЦ, о приложении).
 */
sealed interface HelpScreen : Screen {

    /**
     * SRC (self-calculating cycles) info screen.
     * Экран информации о СРЦ (саморасчитывающиеся циклы).
     */
    data object Src : HelpScreen

    /**
     * About app screen.
     * Экран "о приложении".
     */
    data object About : HelpScreen
}
