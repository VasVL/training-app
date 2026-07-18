# Структура модуля `app`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление папок, ключевых файлов).

## Обзор
Модуль `app` — точка сборки приложения TrainingApp. Не содержит бизнес-логику. Хостит `Application`, `MainActivity`, граф навигации и реализацию `Navigator`.

## Структура
```
app/
├── build.gradle.kts          ← Конфигурация модуля (плагины, зависимости, SDK)
└── src/
    ├── main/
    │   ├── AndroidManifest.xml   ← Манифест приложения (объявление Application, Activity)
    │   ├── java/                 ← Kotlin-код (создаётся по мере разработки)
    │   │   └── <package>/        ← Пакет приложения
    │   │       ├── TrainingApp.kt       ← Application класс (@HiltAndroidApp, init Timber)
    │   │       ├── MainActivity.kt      ← Хост-Activity (@AndroidEntryPoint)
    │   │       └── navigation/          ← Реализация Navigator + граф навигации
    │   └── res/
    │       ├── values/
    │       │   ├── strings.xml   ← Строковые ресурсы
    │       │   ├── themes.xml    ← Темы (Material 2)
    │       │   └── colors.xml   ← Цвета
    │       ├── drawable/         ← Иконки, векторные изображения
    │       ├── layout/           ← XML-разметки (Activity, Fragment)
    │       └── navigation/
    │           └── nav_graph.xml ← Граф навигации (Jetpack Navigation)
    ├── test/                    ← Unit-тесты модуля
    └── androidTest/             ← Instrumented-тесты модуля
```

## Назначение элементов

### `build.gradle.kts`
Конфигурация Gradle-модуля: плагины (Android Application, Kotlin, Hilt), зависимости, SDK версии, `applicationId`, `proguard` правила.

### `src/main/AndroidManifest.xml`
Манифест приложения. Объявляет `Application` класс, `MainActivity`, разрешения, темы.

### `src/main/java/<package>/TrainingApp.kt`
Класс `Application` с аннотацией `@HiltAndroidApp`. В `onCreate()` инициализирует Timber (`Timber.plant(DebugTree())` только в debug-сборке).

### `src/main/java/<package>/MainActivity.kt`
Хост-Activity с `@AndroidEntryPoint`. Содержит `NavController` и хостит Fragment'ы.

### `src/main/java/<package>/navigation/`
Реализация `Navigator` (интерфейс из `core/navigation`). Маппинг `Screen` → `NavDirections`/вызовы `NavController`. Граф навигации — в `res/navigation/nav_graph.xml`.

### `src/main/res/`
Ресурсы приложения: строки, темы, цвета, иконки, разметки, граф навигации.

## Правила
- При добавлении/удалении папки или ключевого файла — обновить этот файл.
- Бизнес-логику в `app` не класть — только инфраструктура (Application, Activity, навигация, ресурсы).
- Зависимости: `app` → feature-модули + `core/navigation`. Feature-модули НЕ зависят от `app`.
