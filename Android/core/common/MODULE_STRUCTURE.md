# Структура модуля `core/common`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление папок, ключевых файлов).

## Обзор
Модуль `core/common` — общий переиспользуемый код: утилиты, расширения Kotlin, базовые классы (`Resource<T>`), логирование, константы. Не зависит от feature-модулей.

## Структура
```
core/common/
├── .gitkeep                   ← Файл-заглушка (папка сохранена в git)
├── AGENTS.md                  ← Контекст модуля (назначение, архитектурные замечания)
├── build.gradle.kts          ← Конфигурация модуля (Android Library, Kotlin, зависимости)
├── MODULE_STRUCTURE.md       ← Этот файл
└── src/main/
    ├── AndroidManifest.xml   ← Манифест библиотеки (минимальный, package declaration)
    └── java/com/vasev/trainingapp/core/common/
        ├── Resource.kt            ← sealed class Resource<T> (Loading/Success/Error)
        └── logs/
            └── LogExtensions.kt   ← Расширения для логирования (поверх Timber)
```

## Назначение элементов

### `Resource.kt`
`sealed class Resource<out T>` — обёртка для состояния данных: `Loading` / `Success(data)` / `Error(message, cause)`. Используется в Repository для предсказуемой обработки ошибок: Repository возвращает `Flow<Resource<T>>`, ViewModel мапит в `UiState`.

### `logs/LogExtensions.kt`
Расширения для логирования поверх Timber — переиспользуемые хелперы для логирования в feature-модулях и `core/*`.

## Правила
- При добавлении/удалении папки или ключевого файла — обновить этот файл.
- Не зависеть от feature-модулей. Зависимости только от внешних библиотек и (при необходимости) других `core/*` модулей.
- Код должен быть максимально общим, без привязки к конкретным фичам.
