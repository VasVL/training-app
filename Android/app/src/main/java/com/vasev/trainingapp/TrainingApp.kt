package com.vasev.trainingapp

import android.app.Application
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import com.vasev.trainingapp.logs.ReleaseErrorTree
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Application class for TrainingApp — the root of the Hilt dependency graph.
 * Класс Application для TrainingApp — корень графа зависимостей Hilt.
 *
 * `@HiltAndroidApp` — Marks Application as Hilt container, generates Hilt component /
 * `@HiltAndroidApp` — Помечает Application как Hilt-контейнер, генерирует Hilt-компонент.
 *
 * This annotation triggers Hilt code generation: it creates the application-level
 * component (SingletonComponent) that holds all singletons provided by `@Module`s
 * annotated with `@InstallIn(SingletonComponent::class)`. Without this annotation
 * Hilt will not work anywhere in the app.
 * Эта аннотация запускает кодогенерацию Hilt: создаётся компонент уровня приложения
 * (SingletonComponent), который хранит все синглтоны из `@Module`-модулей с
 * `@InstallIn(SingletonComponent::class)`. Без этой аннотации Hilt нигде не заработает.
 */
@HiltAndroidApp
class TrainingApp : Application() {

    /**
     * `@Inject` — Hilt provides the user repository implementation for app-start cleanup.
     * `@Inject` — Hilt предоставляет реализацию репозитория пользователей для очистки при старте.
     */
    @Inject
    lateinit var userRepository: UserRepository

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * Called when the application is starting, before any activity/service.
     * Вызывается при старте приложения, до любой activity/service.
     */
    override fun onCreate() {
        super.onCreate()

        // Plant Timber trees for logging / Посадить деревья Timber для логирования.
        //
        // Debug build: `Timber.plant(DebugTree())` — registers a tree that prints ALL
        // log levels (verbose/info/warn/error) to Logcat with the calling class name as tag.
        // Debug-сборка: `Timber.plant(DebugTree())` — регистрирует дерево, печатающее ВСЕ
        // уровни логов (verbose/info/warn/error) в Logcat с именем вызывающего класса как тег.
        //
        // Release build: plant a tree that only forwards ERROR-level logs (crashes, fatal
        // issues) to Logcat. This avoids leaking verbose debug info in production while
        // still capturing critical errors. `Timber.Tree` is the abstract base; we override
        // `log` to filter by priority.
        // Release-сборка: сажаем дерево, которое пропускает только логи уровня ERROR (краши,
        // критические проблемы) в Logcat. Это избегает утечки отладочной информации в проде,
        // но всё ещё ловит критические ошибки. `Timber.Tree` — абстрактный базовый класс;
        // переопределяем `log` для фильтрации по приоритету.
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseErrorTree())
        }

        applicationScope.launch(Dispatchers.IO) {
            try {
                val deletedProfilesCount = userRepository.finalizePendingDeletions()
                Timber.i(
                    "Application startup: finalized pending user deletions, count=$deletedProfilesCount",
                )
            } catch (throwable: Throwable) {
                Timber.e(throwable, "Application startup: failed to finalize pending user deletions")
            }
        }
    }
}
