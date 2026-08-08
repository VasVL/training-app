# Структура модуля `app`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление папок, ключевых файлов).

## Обзор
Модуль `app` — точка сборки приложения TrainingApp. Не содержит бизнес-логику или Fragment-разметки фич. Хостит `Application`, `MainActivity`, корневой граф навигации, реализацию `Navigator` и `NavigationModule` (Hilt). Не зависит от Room API напрямую — Hilt-модуль БД живёт в `core/database`.

## Структура
```
app/
├── AGENTS.md                  ← Контекст модуля app
├── MODULE_STRUCTURE.md        ← Этот файл
├── build.gradle.kts          ← Конфигурация модуля (плагины, зависимости, SDK)
└── src/
    └── main/
        ├── AndroidManifest.xml   ← Манифест приложения (объявление Application, Activity)
        ├── java/com/vasev/trainingapp/
        │   ├── TrainingApp.kt         ← Application класс (@HiltAndroidApp, init Timber)
        │   ├── MainActivity.kt      ← Хост-Activity (@AndroidEntryPoint)
        │   ├── di/
        │   │   └── NavigationModule.kt ← Hilt-модуль: @Binds Navigator → NavigatorImpl
        │   ├── logs/
        │   │   └── ReleaseErrorTree.kt ← Дерево Timber для production-логирования
        │   └── navigation/
        │       ├── NavigatorImpl.kt  ← Реализация Navigator (Screen → NavDirections)
        │       └── entity/
        │           └── NavigationCommand.kt ← одноразовая команда навигации
        └── res/
            ├── layout/
            │   └── activity_main.xml       ← корневой NavHost приложения
            ├── mipmap-nodpi/
            │   └── ic_training_app.png     ← иконка приложения и splash screen
            ├── navigation/
            │   └── nav_graph.xml           ← корневой граф Navigation Component
            └── values/
                ├── colors.xml              ← цвета тем приложения
                ├── strings.xml             ← название приложения
                └── themes.xml              ← тема и системный splash screen
```

## Назначение элементов

### `build.gradle.kts`
Конфигурация Gradle-модуля: плагины (Android Application, Kotlin, Hilt, KSP, Navigation Safe Args), зависимости, SDK версии, `applicationId`, `proguard` правила.

Зависимости (без Room напрямую):
- `core:common:logging` — общие расширения Timber.
- `core:database` — Hilt-модуль БД (`DatabaseModule` предоставляет `TrainingDatabase` и DAO).
- `core:navigation` — порты навигации (`Screen`, `Navigator`).
- `feature-*/data` — Hilt-биндинги репозиториев (`@Binds` интерфейс→реализация).
- `feature-*/ui` — экраны (Fragment, ViewModel).
- `feature-main:contract` — публичный маршрут `MainScreen.Main`, который `NavigatorImpl` сопоставляет корневому destination.
- `feature-main:ui` — постоянная оболочка с тулбаром, шторкой и нижней навигацией.
- Внешние: AppCompat, Coil, ConstraintLayout, core-ktx, Hilt, Material, Navigation, Timber.

Утверждённые визуальные и навигационные решения оболочки находятся в [`feature-main/UI_DESIGN.md`](../features/feature-main/UI_DESIGN.md), а технический макет — в [`feature-main/UI_MOCKUPS.html`](../features/feature-main/UI_MOCKUPS.html).

### `src/main/AndroidManifest.xml`
Манифест приложения. Объявляет `Application` класс, `MainActivity`, разрешения, темы.

### `TrainingApp.kt`
Класс `Application` с аннотацией `@HiltAndroidApp`. В `onCreate()` инициализирует Timber (`Timber.plant(DebugTree())` только в debug-сборке; в release — `ReleaseErrorTree`).

### `MainActivity.kt`
Хост-Activity с `@AndroidEntryPoint`. Содержит `NavController` и хостит Fragment'ы.

### `di/NavigationModule.kt`
Hilt-модуль (`@Module @InstallIn(...)`) с `@Binds` для `Navigator` → `NavigatorImpl`. Это единственный Hilt-модуль в `app` — `DatabaseModule` живёт в `core/database`, а репозитории биндятся в `data`-подмодулях фичей.

### `logs/ReleaseErrorTree.kt`
Дерево Timber для production-логирования — отправка ошибок в краш-репорт (в будущем).

### `navigation/NavigatorImpl.kt`
Реализация `Navigator` (интерфейс из `core/navigation`). Маппинг `Screen` → `NavDirections`/вызовы `NavController`. Граф навигации — в `res/navigation/nav_graph.xml`.

### `src/main/res/`
Только ресурсы уровня приложения: тема, цвета темы, название и иконка приложения, системный splash screen, разметка Activity и корневой граф навигации. Ресурсы конкретных экранов находятся в соответствующих feature-модулях.

## Правила
- При добавлении/удалении папки или ключевого файла — обновить этот файл.
- Бизнес-логику в `app` не класть — только инфраструктура (Application, Activity, навигация, ресурсы, Hilt-модуль Navigator).
- Не добавлять Room-зависимость в `app` — Hilt-модуль БД живёт в `core/database`.
- Зависимости: `app` → `feature-*/data` + `feature-*/ui` + `core/database` + `core:navigation` + нужные узкие модули `core:common:*`. Feature-модули НЕ зависят от `app`.
