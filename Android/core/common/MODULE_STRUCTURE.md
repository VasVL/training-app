# Структура модуля `core/common`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление папок, ключевых файлов).

## Обзор
Модуль `core/common` — общий переиспользуемый код: утилиты, расширения Kotlin, базовые классы (`Resource<T>`), константы. Не зависит от feature-модулей.

## Структура
```
core/common/
├── build.gradle.kts          ← Конфигурация модуля (Android Library, Kotlin, зависимости)
└── src/
    ├── main/
    │   ├── AndroidManifest.xml   ← Манифест библиотеки (минимальный, package declaration)
    │   └── java/
    │       └── <package>/common/
    │           ├── resource/         ← Resource<T> sealed class (Loading/Success/Error)
    │           ├── extensions/       ← Kotlin extension functions
    │           ├── constants/        ← Общие константы приложения
    │           └── ...               ← Прочие утилиты/базовые классы
    └── test/
        └── java/                 ← Unit-тесты модуля
```

## Назначение элементов

### `build.gradle.kts`
Конфигурация Gradle-модуля: плагины (Android Library, Kotlin), зависимости, SDK версии. Модуль `com.android.library`.

### `src/main/java/<package>/common/resource/`
`Resource<T>` — sealed class-обёртка для состояния данных. Варианты: `Loading`, `Success(data: T)`, `Error(message: String, cause: Throwable?)`. Используется в Repository для предсказуемой обработки ошибок и передачи состояния в ViewModel.

## Правила
- При добавлении/удалении папки или ключевого файла — обновить этот файл.
- Не зависеть от feature-модулей. Зависимости только от внешних библиотек и (при необходимости) других `core/*` модулей.
- Код должен быть максимально общим, без привязки к конкретным фичам.
