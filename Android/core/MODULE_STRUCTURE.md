# Структура папки `core`

> **Важно:** Этот файл нужно обновлять после изменений в структуре папки (добавление/удаление модулей).

## Обзор
Папка `core/` — контейнер для общих модулей, переиспользуемых между feature-модулями. Модули не зависят от feature-модулей.

## Структура
```
core/
├── AGENTS.md              ← Контекст папки core (назначение, архитектурные замечания)
├── MODULE_STRUCTURE.md    ← Этот файл
├── common/                ← Контейнер небольших общих модулей
│   ├── AGENTS.md
│   ├── MODULE_STRUCTURE.md
│   ├── domain/            ← Kotlin/JVM: Resource<T>
│   ├── logging/           ← Android: расширения Timber
│   └── ui/                ← Android: общие UI-ресурсы и палитры тем
├── database/              ← Локальная БД (Room): все Entity, DAO, TrainingDatabase, Converters, DatabaseModule (Hilt)
│   ├── AGENTS.md
│   ├── build.gradle.kts
│   ├── MODULE_STRUCTURE.md
│   └── src/main/java/com/vasev/trainingapp/core/database/
│       ├── Converters.kt       ← TypeConverters для всех enum-ов (internal object)
│       ├── TrainingDatabase.kt ← Единая Room-база (@Database, @TypeConverters, internal)
│       ├── dao/                ← 22 @Dao-интерфейса
│       ├── di/
│       │   └── DatabaseModule.kt ← Hilt-модуль: @Provides TrainingDatabase + все DAO
│       └── entity/             ← @Entity (таблицы) + enum-ы (entity/types/)
└── navigation/            ← Порты навигации (Screen, Navigator)
    ├── build.gradle.kts
    └── src/main/java/com/vasev/trainingapp/core/navigation/
        ├── Navigator.kt   ← Интерфейс Navigator (navigate/back/popUpTo)
        └── Screen.kt      ← Маркерный интерфейс Screen
```

## Назначение модулей

### `core/common/`
Контейнер небольших общих модулей. `core:common:domain` — чистые Kotlin-типы (`Resource<T>`), `core:common:logging` — расширения Timber, `core:common:ui` — общие UI-ресурсы и палитры тем. Код и ресурсы не размещаются непосредственно в контейнере.
Подробнее: [`core/common/AGENTS.md`](common/AGENTS.md), [`core/common/MODULE_STRUCTURE.md`](common/MODULE_STRUCTURE.md)

### `core/database/`
Локальная база данных (Room): 22 Entity, 22 DAO, единая `TrainingDatabase`, `Converters`, `DatabaseModule` (Hilt `@Provides` для `TrainingDatabase` и всех DAO). Общая Room-инфраструктура — не зависит от feature-модулей. DAO доступны feature-модулям через DI (Hilt); реализации репозиториев (адаптеры для портов из `domain` фичей) живут в `data`-подмодулях фичей.
Подробнее: [`core/database/AGENTS.md`](database/AGENTS.md), [`core/database/MODULE_STRUCTURE.md`](database/MODULE_STRUCTURE.md)

### `core/navigation/`
Чистый Kotlin/JVM-модуль.
Порты навигации:
- `Screen` — маркерный интерфейс для маршрутов экранов. Каждая фича объявляет свои экраны в своём contract-модуле как `sealed interface XxxScreen : Screen`.
- `Navigator` — интерфейс с методами `navigate(screen)`, `back()`, `popUpTo(screen, inclusive)`. Реализация — в `app` (где доступен `NavController`), инжектируется через Hilt.

Feature-модули зависят только от `core/navigation` и не знают друг о друге.

## Правила
- При добавлении/удалении модуля — обновить этот файл и `settings.gradle.kts`.
- Модули из `core/` не зависят от feature-модулей.
- `core/database` предоставляет DAO через Hilt (`DatabaseModule`). Реализации репозиториев (адаптеры для портов из `domain` фичей) живут в `data`-подмодулях фичей (Ports & Adapters / Clean Architecture).
