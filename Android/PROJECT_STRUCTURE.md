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
├── AGENTS.md              ← Контекст Android-приложения (архитектурные/стилистические замечания)
├── APP_DESIGN.md          ← Дизайн приложения (договорённости по экранам и данным)
├── ROADMAP.md             ← Порядок реализации функционала
├── VISUAL_STYLE.md        ← Общие визуальные правила приложения
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
│   ├── common/            ← Утилиты, Resource<T>, логирование
│   │   ├── AGENTS.md      ← Контекст модуля core/common
│   │   ├── build.gradle.kts
│   │   └── MODULE_STRUCTURE.md ← Структура модуля core/common
│   ├── database/          ← Локальная БД (Room): все Entity, DAO, TrainingDatabase, Converters, DatabaseModule (Hilt)
│   │   ├── AGENTS.md      ← Контекст модуля core/database
│   │   ├── build.gradle.kts
│   │   └── MODULE_STRUCTURE.md ← Структура модуля core/database
│   └── navigation/        ← Порты навигации: Screen.kt, Navigator.kt
│       └── build.gradle.kts
└── features/              ← Feature-модули (каждая фича — отдельный модуль)
    ├── AGENTS.md          ← Контекст папки features
    ├── MODULE_STRUCTURE.md ← Структура папки features
    ├── feature-anatomy/      ← Анатомический атлас (contract, domain, data, ui)
    ├── feature-auth/         ← Пользователи, "О себе" (contract, domain, data, ui)
    ├── feature-calendar/     ← Календарь (contract, domain, ui)
    ├── feature-exercises/    ← Упражнения (contract, domain, data, ui)
    ├── feature-help/         ← Справка: СРЦ, о приложении (contract, domain, ui)
    ├── feature-nutrition/    ← Дневник питания (contract, domain, data, ui)
    ├── feature-programs/     ← Программы (contract, domain, data, ui)
    ├── feature-settings/     ← Настройки (contract, domain, ui)
    ├── feature-weight/       ← Отслеживание веса (contract, domain, data, ui)
    └── feature-workout/      ← Текущая тренировка (contract, domain, data, ui)
```

## Назначение папок

### `app/`
Модуль приложения — точка сборки. Содержит:
- `Application` класс (инициализация Hilt, Timber)
- Граф навигации (Jetpack Navigation)
- Реализацию `Navigator` (маппинг `Screen` → `NavDirections`)
- `MainActivity` (хост для Fragment'ов)
- `NavigationModule` (Hilt-биндинг `Navigator`)
- НЕ содержит бизнес-логику
- НЕ зависит от Room напрямую (Hilt-модуль БД живёт в `core/database`)

Подробнее: [`app/AGENTS.md`](app/AGENTS.md), [`app/MODULE_STRUCTURE.md`](app/MODULE_STRUCTURE.md)

### `core/`
Общие модули, переиспользуемые между feature-модулями.

Подробнее: [`core/AGENTS.md`](core/AGENTS.md), [`core/MODULE_STRUCTURE.md`](core/MODULE_STRUCTURE.md)

#### `core/common/`
Утилиты, расширения Kotlin, базовые классы (например, `Resource<T>`), логирование (`LogExtensions`). Не зависит от feature-модулей.

Подробнее: [`core/common/AGENTS.md`](core/common/AGENTS.md), [`core/common/MODULE_STRUCTURE.md`](core/common/MODULE_STRUCTURE.md)

#### `core/database/`
Локальная БД (Room): все Entity, DAO, `TrainingDatabase`, `Converters`, `DatabaseModule` (Hilt `@Provides` для `TrainingDatabase` и всех DAO). Общая Room-инфраструктура — не зависит от feature-модулей. Зависит только от `core/common`.

Подробнее: [`core/database/AGENTS.md`](core/database/AGENTS.md), [`core/database/MODULE_STRUCTURE.md`](core/database/MODULE_STRUCTURE.md)

#### `core/navigation/`
Порты навигации: `Screen` (маркерный интерфейс для маршрутов экранов) и `Navigator` (интерфейс с методами `navigate(screen)`/`back()`/`popUpTo(screen, inclusive)`). Feature-модули зависят только от этого модуля и не знают друг о друге. Реализация `Navigator` — в `app` (где доступен `NavController`), инжектируется через Hilt.

### `features/`
Feature-модули. Каждая фича — отдельный Gradle-модуль с подмодулями `contract`/`domain`/`data`/`ui`:
- `contract/` — публичные порты фичи (интерфейсы, модели, `Screen`-маршруты), доступны другим модулям
- `domain/` — бизнес-логика (domain-модели, enum-ы, интерфейсы репозиториев, use-case)
- `data/` — реализации репозиториев + мапперы (Room Entity ↔ domain-модель) + Hilt-биндинги. Зависит от `domain` фичи и `core/database`
- `ui/` — UI-слой (Fragment, ViewModel, ViewBinding)
- `common/` — вспомогательный код фичи (необязательный)

Текущие фичи:
- `feature-anatomy` — анатомический атлас (contract, domain, data, ui)
- `feature-auth` — пользователи, "О себе" (contract, domain, data, ui); утверждённый интерфейс — `feature-auth/UI_DESIGN.md`, макеты — `feature-auth/UI_MOCKUPS.html`
- `feature-calendar` — календарь (contract, domain, ui)
- `feature-exercises` — список упражнений, поиск (contract, domain, data, ui)
- `feature-help` — справка (СРЦ, о приложении) (contract, domain, ui)
- `feature-nutrition` — дневник питания (contract, domain, data, ui)
- `feature-programs` — программы, микроциклы, дни (contract, domain, data, ui)
- `feature-settings` — настройки (contract, domain, ui)
- `feature-weight` — отслеживание веса (contract, domain, data, ui)
- `feature-workout` — текущая тренировка, выполнение (contract, domain, data, ui)

`domain`-модуль одной фичи НЕ зависит от `domain`-модуля другой фичи. Зависимости между фичами — только через `contract`-модули. Persistence изолирована за портами (Ports & Adapters / Clean Architecture): `domain` объявляет интерфейсы репозиториев, `data` реализует их через DAO из `core/database`.

Подробнее: [`features/AGENTS.md`](features/AGENTS.md), [`features/MODULE_STRUCTURE.md`](features/MODULE_STRUCTURE.md)

## Правила
- При добавлении/удалении модуля — обновить этот файл и `settings.gradle.kts`
- При добавлении новой папки — описать её назначение здесь
