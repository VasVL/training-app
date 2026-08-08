# Контекст папки `features`

## О папке
Папка `features/` — контейнер для feature-модулей. Каждая фича — отдельный Gradle-модуль.

Полное и актуальное описание внутренней структуры папки находится в файле **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md)**.

- **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md) нужно обновлять после любых изменений в структуре папки** — добавление/удаление feature-модулей.
- Общие архитектурные/стилистические замечания для всего Android-проекта — в [`Android/AGENTS.md`](../AGENTS.md).

## Назначение
Каждая фича — отдельный Gradle-модуль в `features/<name>/`. Feature-модуль может содержать подмодули:
- `contract/` — чистый Kotlin/JVM-модуль с контрактами/интерфейсами фичи (доступны другим модулям)
- `domain/` — чистый Kotlin/JVM-модуль с бизнес-логикой (domain-модели, enum-ы, интерфейсы репозиториев, use-case)
- `data/` — реализации репозиториев + мапперы (Room Entity ↔ domain-модель) + Hilt-биндинги. Зависит от `domain` фичи и `core/database`.
- `ui/` — UI-слой (Fragment, ViewModel, ViewBinding)
- `common/` — вспомогательный код фичи

Могут быть не все подмодули, но других быть не должно. Текущие фичи: `feature-anatomy`, `feature-auth`, `feature-calendar`, `feature-exercises`, `feature-help`, `feature-main`, `feature-nutrition`, `feature-programs`, `feature-settings`, `feature-weight`, `feature-workout`.

## Архитектурные замечания

### Изоляция модулей (Ports & Adapters)
- **`domain`-модуль одной фичи НЕ зависит от `domain`-модуля другой фичи.** Это исключает циклические зависимости.
- Если фиче нужны данные/действия из внешнего мира (другая фича, БД, сеть), её `domain` объявляет **порт** — интерфейс (например, `UserRepository`, `WeightRepository`).
- **Реализация порта (адаптер)** живёт в `data`-подмодуле фичи и подключается через DI (Hilt). `data` инжектирует DAO из `core/database`, маппит `Entity` ↔ domain model и реализует интерфейсы из `domain/repository`. Фича не знает, кто именно реализует порт.
- Домен работает со **своими моделями** (domain models), а не с внешними типами (Room Entity, DTO). Адаптер (`data`) маппит внешние типы → domain models.
- `contract`-модуль фичи — место для её **публичных портов** (интерфейсов, моделей, `Screen`-маршрутов), доступных другим модулям. Другие фичи зависят от `contract`, а не от `domain`.

### UI и бизнес-логика
- UI-слой не содержит бизнес-логику. Бизнес-логика — в `domain` (UseCase/Repository-интерфейсы), доступна через DI (Hilt). Реализации репозиториев — в `data`.
- Паттерн: MVVM + Clean Architecture. Единый `UiState` на экран через `StateFlow`.

### Зависимости
- `domain` фичи — чистый Kotlin/JVM, без Room и Android API. Зависит только от JVM-библиотек и, при необходимости, `core:common:domain`.
- `data` фичи зависит от `domain` фичи (интерфейсы репозиториев, domain-модели) и `core/database` (DAO). Содержит Hilt-биндинги (`@Binds` интерфейс→реализация).
- `ui` фичи зависит от `domain` фичи (интерфейсы репозиториев, модели) и `core/navigation` (через contract).
- `contract` фичи — чистый Kotlin/JVM и зависит от `core:navigation` (для `Screen`).
- Feature-модули не знают друг о друге напрямую. Зависимости между фичами — только через `contract`-модули (публичные порты).
- `app` зависит от `feature-*/data` (для Hilt-биндингов репозиториев) + `feature-*/ui` (экраны) + `core/database` (для Hilt-модуля БД) + `core/navigation` и нужных узких модулей `core:common:*`.
