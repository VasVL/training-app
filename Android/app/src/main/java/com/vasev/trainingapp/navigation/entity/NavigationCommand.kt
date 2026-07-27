package com.vasev.trainingapp.navigation.entity

import com.vasev.trainingapp.core.navigation.Screen

/**
 * One-time commands handled by MainActivity through NavController.
 * Одноразовые команды, обрабатываемые MainActivity через NavController.
 */
internal sealed interface NavigationCommand {

    data object Back : NavigationCommand

    data class Navigate(
        val screen: Screen,
    ) : NavigationCommand

    data class PopUpTo(
        val inclusive: Boolean,
        val screen: Screen,
    ) : NavigationCommand
}
