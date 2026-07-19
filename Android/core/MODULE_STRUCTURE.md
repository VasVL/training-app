# Структура папки `core`

> **Важно:** Этот файл нужно обновлять после изменений в структуре папки (добавление/удаление модулей).

## Обзор
Папка `core/` — контейнер для общих модулей, переиспользуемых между feature-модулями. Модули не зависят от feature-модулей.

## Структура
```
core/
├── AGENTS.md              ← Контекст папки core (назначение, архитектурные замечания)
├── MODULE_STRUCTURE.md    ← Этот файл
├── common/                ← Утилиты, расширения, базовые классы (Resource<T>), константы (пока пусто)
│   ├── AGENTS.md
│   ├── build.gradle.kts
│   └── MODULE_STRUCTURE.md
├── database/              ← Локальная БД (Room): все Entity, DAO, TrainingDatabase, Converters
│   ├── AGENTS.md
│   ├── build.gradle.kts
│   └── MODULE_STRUCTURE.md
├── navigation/            ← Порты навигации (Screen, Navigator)
│   ├── build.gradle.kts
│   └── src/main/java/com/vasev/trainingapp/core/navigation/
│       ├── Navigator.kt   ← Интерфейс Navigator (navigate/back/popUpTo)
│       └── Screen.kt      ← Маркерный интерфейс Screen
└── reference-data/        ← Domain-модели и порты репозиториев справочных данных
    ├── AGENTS.md
    ├── build.gradle.kts
    └── MODULE_STRUCTURE.md
```

## Назначение модулей

### `core/common/`
Утилиты, расширения Kotlin, базовые классы (`Resource<T>`), константы. Не зависит от feature-модулей. Пока пустой.
Подробнее: [`core/common/AGENTS.md`](common/AGENTS.md), [`core/common/MODULE_STRUCTURE.md`](common/MODULE_STRUCTURE.md)

### `core/database/`
Локальная база данных (Room): 22 Entity, 22 DAO, единая `TrainingDatabase`, `Converters`. Адаптер для портов из `core/reference-data` (Ports & Adapters). Зависит от `core/common` и `core/reference-data`.
Подробнее: [`core/database/AGENTS.md`](database/AGENTS.md), [`core/database/MODULE_STRUCTURE.md`](database/MODULE_STRUCTURE.md)

### `core/navigation/`
Порты навигации:
- `Screen` — маркерный интерфейс для маршрутов экранов. Каждая фича объявляет свои экраны в своём contract-модуле как `sealed interface XxxScreen : Screen`.
- `Navigator` — интерфейс с методами `navigate(screen)`, `back()`, `popUpTo(screen, inclusive)`. Реализация — в `app` (где доступен `NavController`), инжектируется через Hilt.

Feature-модули зависят только от `core/navigation` и не знают друг о друге.

### `core/reference-data/`
Domain-модели и порты (интерфейсы) репозиториев для справочных данных (упражнения, мышцы, группы мышц). Чистый домен без Room-аннотаций. Реализации портов — в `core/database`. Зависит от `core/common`.
Подробнее: [`core/reference-data/AGENTS.md`](reference-data/AGENTS.md), [`core/reference-data/MODULE_STRUCTURE.md`](reference-data/MODULE_STRUCTURE.md)

## Правила
- При добавлении/удалении модуля — обновить этот файл и `settings.gradle.kts`.
- Модули из `core/` не зависят от feature-модулей.
- `core/*` модули могут предоставлять адаптеры для портов, объявленных в `domain` feature-модулей (Ports & Adapters).
