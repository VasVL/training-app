# Структура проекта TrainingApp

> **Важно:** Этот файл нужно обновлять после изменений в структуре проекта (добавление/удаление модулей, папок, ключевых файлов).

## Обзор
Android-приложение для занятий силовыми видами спорта. Многомодульный проект на Kotlin + Jetpack.

## Корневая структура
```
TrainingApp/
├── AGENTS.md              ← Контекст проекта (архитектурные/стилистические замечания). Автозагружается агентами.
├── .gitignore             ← Игнорируемые файлы (macOS, IDE, Android build-артефакты)
└── Android/               ← Весь Android-проект
```

## Структура Android/
```
Android/
├── PROJECT_STRUCTURE.md   ← Этот файл (описание структуры проекта)
├── .gitignore             ← Android-специфичный .gitignore
├── settings.gradle.kts    ← Конфигурация Gradle: include модулей
├── build.gradle.kts       ← Корневой build-файл (плагины apply false)
├── gradle.properties      ← Свойства Gradle (AndroidX, JVM args)
├── gradlew                ← Gradle Wrapper (Unix)
├── gradlew.bat            ← Gradle Wrapper (Windows)
├── gradle/
│   ├── libs.versions.toml ← Version catalog (версии всех зависимостей)
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── app/                   ← Модуль приложения (точка сборки, без бизнес-логики)
│   ├── AGENTS.md          ← Контекст модуля app
│   ├── MODULE_STRUCTURE.md ← Структура модуля app
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       └── res/values/strings.xml
├── core/                  ← Общие модули (переиспользуемый код)
│   ├── AGENTS.md          ← Контекст папки core
│   ├── MODULE_STRUCTURE.md ← Структура папки core
│   └── common/            ← Утилиты, расширения, общий код (пока пусто)
│       ├── AGENTS.md      ← Контекст модуля core/common
│       └── MODULE_STRUCTURE.md ← Структура модуля core/common
└── features/              ← Feature-модули (каждая фича — отдельный модуль, пока пусто)
    ├── AGENTS.md          ← Контекст папки features
    └── MODULE_STRUCTURE.md ← Структура папки features
```

## Назначение папок

### `app/`
Модуль приложения — точка сборки. Содержит:
- `Application` класс (инициализация Hilt, Timber)
- Граф навигации (Jetpack Navigation)
- Реализацию `Navigator` (маппинг `Screen` → `NavDirections`)
- `MainActivity` (хост для Fragment'ов)
- НЕ содержит бизнес-логику

Подробнее: [`app/AGENTS.md`](app/AGENTS.md), [`app/MODULE_STRUCTURE.md`](app/MODULE_STRUCTURE.md)

### `core/`
Общие модули, переиспользуемые между feature-модулями.

Подробнее: [`core/AGENTS.md`](core/AGENTS.md), [`core/MODULE_STRUCTURE.md`](core/MODULE_STRUCTURE.md)

#### `core/common/`
Утилиты, расширения Kotlin, базовые классы (например, `Resource<T>`), константы. Не зависит от feature-модулей.

Подробнее: [`core/common/AGENTS.md`](core/common/AGENTS.md), [`core/common/MODULE_STRUCTURE.md`](core/common/MODULE_STRUCTURE.md)

### `features/`
Feature-модули. Каждая фича — отдельный Gradle-модуль. Может содержать подмодули:
- `contract/` — контракты/интерфейсы фичи (доступны другим модулям)
- `domain/` — бизнес-логика (UseCase, Repository, модели)
- `ui/` — UI-слой (Fragment, ViewModel, ViewBinding)
- `common/` — вспомогательный код фичи

`domain`-модуль одной фичи НЕ зависит от `domain`-модуля другой фичи. Зависимости между фичами — только через `contract`-модули.

Подробнее: [`features/AGENTS.md`](features/AGENTS.md), [`features/MODULE_STRUCTURE.md`](features/MODULE_STRUCTURE.md)

## Правила
- При добавлении/удалении модуля — обновить этот файл и `settings.gradle.kts`
- При добавлении новой папки — описать её назначение здесь
