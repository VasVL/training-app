# Структура контейнера `core/common`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление папок, ключевых файлов).

## Обзор
`core/common` — контейнер общих модулей, каждый из которых отвечает за один слой или тип переиспользуемого кода.

## Структура
```
core/common/
├── AGENTS.md                  ← Контекст контейнера
├── MODULE_STRUCTURE.md        ← Этот файл
├── domain/                    ← Чистый Kotlin/JVM-модуль с общими доменными типами
│   ├── AGENTS.md
│   ├── MODULE_STRUCTURE.md
│   ├── build.gradle.kts
│   └── src/main/java/com/vasev/trainingapp/core/common/domain/
│       ├── MeasurementConversion.kt
│       └── Resource.kt
├── logging/                   ← Android-модуль расширений Timber
│   ├── AGENTS.md
│   ├── MODULE_STRUCTURE.md
│   ├── build.gradle.kts
│   └── src/main/java/com/vasev/trainingapp/core/common/logging/
│       └── LogExtensions.kt
└── ui/                        ← Android-модуль общих UI-ресурсов и палитр тем
    ├── AGENTS.md
    ├── MODULE_STRUCTURE.md
    ├── build.gradle.kts
    └── src/main/
        ├── java/com/vasev/trainingapp/core/common/ui/view/
        │   └── PendingView.kt
        └── res/               ← палитры тем, общие размеры, иконки и pending-ресурсы
```

## Назначение элементов

### `domain/`
Чистый Kotlin/JVM-модуль. Содержит `Resource<T>` — обёртку для состояния данных: `Loading` / `Success(data)` / `Error(message, cause)`, а также `MeasurementConversion` с коэффициентами единиц измерения.

### `logging/`
Android-библиотека с расширениями для ленивого логирования поверх Timber.

### `ui/`
Android-библиотека с общими XML-ресурсами и View, не принадлежащими отдельной фиче. В том числе предоставляет цветовые палитры, необходимые feature-модулям для независимой сборки.

## Правила
- При добавлении/удалении подмодуля — обновить этот файл, `core/MODULE_STRUCTURE.md`, `PROJECT_STRUCTURE.md` и `settings.gradle.kts`.
- Не размещать исходный код или ресурсы непосредственно в `core/common`.
- Создавать новый подмодуль, когда код не относится к назначению существующих.
