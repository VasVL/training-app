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
│   ├── common/            ← Утилиты, расширения, общий код (пока пусто)
│   │   ├── AGENTS.md      ← Контекст модуля core/common
│   │   └── MODULE_STRUCTURE.md ← Структура модуля core/common
│   ├── database/          ← Локальная БД (Room): все Entity, DAO, TrainingDatabase, Converters
│   │   ├── AGENTS.md      ← Контекст модуля core/database
│   │   └── MODULE_STRUCTURE.md ← Структура модуля core/database
│   ├── navigation/        ← Порты навигации: Screen.kt, Navigator.kt
│   │   └── build.gradle.kts
│   └── reference-data/    ← Domain-модели и порты репозиториев справочных данных
│       ├── AGENTS.md      ← Контекст модуля core/reference-data
│       └── MODULE_STRUCTURE.md ← Структура модуля core/reference-data
└── features/              ← Feature-модули (каждая фича — отдельный модуль)
    ├── AGENTS.md          ← Контекст папки features
    ├── MODULE_STRUCTURE.md ← Структура папки features
    ├── feature-anatomy/      ← Анатомический атлас (contract, domain, ui)
    ├── feature-auth/         ← Пользователи, "О себе" (contract, domain, ui)
    ├── feature-calendar/     ← Календарь (contract, domain, ui)
    ├── feature-exercises/    ← Упражнения (contract, domain, ui)
    ├── feature-help/         ← Справка: СРЦ, о приложении (contract, domain, ui)
    ├── feature-programs/     ← Программы (contract, domain, ui)
    ├── feature-settings/     ← Настройки (contract, domain, ui)
    ├── feature-weight/       ← Отслеживание веса (contract, domain, ui)
    └── feature-workout/      ← Текущая тренировка (contract, domain, ui)
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

#### `core/database/`
Локальная БД (Room): все Entity, DAO, `TrainingDatabase`, `Converters`. Адаптер для портов из `core/reference-data` (Ports & Adapters). Зависит от `core/common` и `core/reference-data`.

Подробнее: [`core/database/AGENTS.md`](core/database/AGENTS.md), [`core/database/MODULE_STRUCTURE.md`](core/database/MODULE_STRUCTURE.md)

#### `core/navigation/`
Порты навигации: `Screen` (маркерный интерфейс для маршрутов экранов) и `Navigator` (интерфейс с методами `navigate(screen)`/`back()`/`popUpTo(screen, inclusive)`). Feature-модули зависят только от этого модуля и не знают друг о друге. Реализация `Navigator` — в `app` (где доступен `NavController`), инжектируется через Hilt.

#### `core/reference-data/`
Domain-модели и порты (интерфейсы) репозиториев справочных данных (упражнения, мышцы, группы мышц). Чистый домен без Room-аннотаций. Реализации портов — в `core/database`. Зависит от `core/common`.

Подробнее: [`core/reference-data/AGENTS.md`](core/reference-data/AGENTS.md), [`core/reference-data/MODULE_STRUCTURE.md`](core/reference-data/MODULE_STRUCTURE.md)

### `features/`
Feature-модули. Каждая фича — отдельный Gradle-модуль с подмодулями `contract`/`domain`/`ui`:
- `contract/` — публичные порты фичи (интерфейсы, модели, `Screen`-маршруты), доступны другим модулям
- `domain/` — бизнес-логика (UseCase, Repository-интерфейсы, модели)
- `ui/` — UI-слой (Fragment, ViewModel, ViewBinding)
- `common/` — вспомогательный код фичи (необязательный)

Текущие фичи (у каждой есть `contract`, `domain`, `ui`):
- `feature-anatomy` — анатомический атлас
- `feature-auth` — пользователи, "О себе"
- `feature-calendar` — календарь
- `feature-exercises` — список упражнений, поиск
- `feature-help` — справка (СРЦ, о приложении)
- `feature-programs` — программы, микроциклы, дни
- `feature-settings` — настройки
- `feature-weight` — отслеживание веса
- `feature-workout` — текущая тренировка, выполнение

`domain`-модуль одной фичи НЕ зависит от `domain`-модуля другой фичи. Зависимости между фичами — только через `contract`-модули.

Подробнее: [`features/AGENTS.md`](features/AGENTS.md), [`features/MODULE_STRUCTURE.md`](features/MODULE_STRUCTURE.md)

## Правила
- При добавлении/удалении модуля — обновить этот файл и `settings.gradle.kts`
- При добавлении новой папки — описать её назначение здесь
