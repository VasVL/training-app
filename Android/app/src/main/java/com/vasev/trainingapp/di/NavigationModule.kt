package com.vasev.trainingapp.di

import com.vasev.trainingapp.core.navigation.Navigator
import com.vasev.trainingapp.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that binds the [Navigator] port to its [NavigatorImpl] adapter.
 * Hilt-модуль, который связывает порт [Navigator] с его адаптером [NavigatorImpl].
 *
 * `@Module` — Hilt module — container for @Provides methods /
 * `@Module` — Модуль Hilt — контейнер для методов @Provides.
 *
 * `@InstallIn(SingletonComponent::class)` — Install in SingletonComponent — lives as long as
 * Application / Установить в SingletonComponent — живёт пока Application.
 *
 * Feature modules depend on the `Navigator` interface (from `core/navigation`); they never
 * see `NavigatorImpl`. This module is the only place that knows the concrete implementation,
 * keeping the Ports & Adapters boundary intact.
 * Feature-модули зависят от интерфейса `Navigator` (из `core/navigation`); они никогда не видят
 * `NavigatorImpl`. Этот модуль — единственное место, знающее конкретную реализацию,
 * что сохраняет границу Ports & Adapters нетронутой.
 */
@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    /**
     * Provides the [Navigator] interface backed by [NavigatorImpl].
     * Предоставляет интерфейс [Navigator] на основе [NavigatorImpl].
     *
     * `@Singleton` — Single instance for whole app /
     * `@Singleton` — Единственный экземпляр для всего приложения.
     *
     * Hilt injects `NavigatorImpl` (it has `@Inject constructor()`) and we return it as the
     * interface type. Singleton so all consumers share one navigator (and one NavController
     * once the real implementation is wired).
     * Hilt инжектит `NavigatorImpl` (у него `@Inject constructor()`) и мы возвращаем его как
     * тип интерфейса. Singleton, чтобы все потребители делили один навигатор (и один NavController,
     * когда реальная реализация будет подключена).
     */
    @Provides
    @Singleton
    internal fun provideNavigator(impl: NavigatorImpl): Navigator {
        return impl
    }
}
