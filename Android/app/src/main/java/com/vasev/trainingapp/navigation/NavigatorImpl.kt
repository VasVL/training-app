package com.vasev.trainingapp.navigation

import com.vasev.trainingapp.core.common.logging.d
import com.vasev.trainingapp.core.navigation.Navigator
import com.vasev.trainingapp.core.navigation.Screen
import timber.log.Timber
import javax.inject.Inject

/**
 * Temporary implementation — logs navigation. Real mapping (Screen → NavDirections)
 * will be added when Fragments and nav_graph are ready /
 * Временная реализация — логирует навигацию. Реальный маппинг (Screen → NavDirections)
 * будет добавлен, когда будут готовы Fragment'ы и nav_graph.
 *
 * `@Inject constructor()` — tells Hilt to create instances of this class when needed
 * (no parameters to inject here, but Hilt still needs this annotation to know it
 * can build this type). The class is provided to consumers via [NavigationModule].
 * `@Inject constructor()` — говорит Hilt создавать экземпляры этого класса по необходимости
 * (здесь нет параметров для инъекции, но Hilt всё равно нужна эта аннотация, чтобы знать,
 * что он может собрать этот тип). Класс предоставляется потребителям через [NavigationModule].
 */
class NavigatorImpl @Inject constructor() : Navigator {

    /**
     * Logs the target screen. Will be replaced with NavController.navigate(...) later.
     * Логирует целевой экран. Будет заменён на NavController.navigate(...) позже.
     *
     * `Timber.d { ... }` — lazy extension from core:common:logging: the lambda is only invoked when
     * a tree is planted, so the string is not built in builds without logging.
     * `Timber.d { ... }` — ленивый extension из core:common:logging: лямбда вызывается только
     * когда посажено дерево, поэтому строка не строится в сборках без логов.
     */
    override fun navigate(screen: Screen) {
        Timber.d { "navigate: $screen" }
    }

    /**
     * Logs a back request. Will be replaced with NavController.popBackStack() later.
     * Логирует запрос назад. Будет заменён на NavController.popBackStack() позже.
     */
    override fun back() {
        Timber.d { "back" }
    }

    /**
     * Logs a popUpTo request. Will be replaced with NavController.popBackStack(route, inclusive) later.
     * Логирует запрос popUpTo. Будет заменён на NavController.popBackStack(route, inclusive) позже.
     */
    override fun popUpTo(screen: Screen, inclusive: Boolean) {
        Timber.d { "popUpTo: $screen, inclusive=$inclusive" }
    }
}
