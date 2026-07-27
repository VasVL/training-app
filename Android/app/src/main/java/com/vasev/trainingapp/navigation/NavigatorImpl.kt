package com.vasev.trainingapp.navigation

import com.vasev.trainingapp.core.common.logging.d
import com.vasev.trainingapp.core.navigation.Navigator
import com.vasev.trainingapp.core.navigation.Screen
import com.vasev.trainingapp.navigation.entity.NavigationCommand
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber

/**
 * Emits one-time navigation commands for the activity that owns NavController.
 * Отправляет одноразовые команды навигации для Activity-владельца NavController.
 *
 * `@Inject constructor()` — Hilt can create this class and provide it through [NavigationModule].
 * `@Inject constructor()` — Hilt может создать этот класс и предоставить его через [NavigationModule].
 *
 * `@Singleton` — Hilt creates one navigator for the whole application.
 * `@Singleton` — Hilt создаёт один навигатор для всего приложения.
 */
@Singleton
internal class NavigatorImpl @Inject constructor() : Navigator {

    private val navigationCommands = MutableSharedFlow<NavigationCommand>(
        extraBufferCapacity = NAVIGATION_COMMAND_BUFFER_CAPACITY,
    )

    val commands = navigationCommands.asSharedFlow()

    /**
     * Enqueues navigation to the target screen.
     * Добавляет переход к целевому экрану в очередь.
     */
    override fun navigate(screen: Screen) {
        Timber.d { "navigate: $screen" }
        sendCommand(NavigationCommand.Navigate(screen))
    }

    /**
     * Enqueues navigation to the previous screen.
     * Добавляет переход к предыдущему экрану в очередь.
     */
    override fun back() {
        Timber.d { "back" }
        sendCommand(NavigationCommand.Back)
    }

    /**
     * Enqueues a back-stack cleanup request.
     * Добавляет запрос очистки стека навигации в очередь.
     */
    override fun popUpTo(screen: Screen, inclusive: Boolean) {
        Timber.d { "popUpTo: $screen, inclusive=$inclusive" }
        sendCommand(
            NavigationCommand.PopUpTo(
                inclusive = inclusive,
                screen = screen,
            ),
        )
    }

    private fun sendCommand(command: NavigationCommand) {
        if (!navigationCommands.tryEmit(command)) {
            Timber.e("Navigation command could not be emitted: $command")
        }
    }

    private companion object {

        private const val NAVIGATION_COMMAND_BUFFER_CAPACITY = 1
    }
}
