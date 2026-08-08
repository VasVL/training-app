# Контекст модуля `app`

> **Важно:** Замечания, даваемые пользователем в процессе разработки, имеют высший приоритет. Этот файл хранит накопленный контекст; при конфликте с ним — уточнять у пользователя как поступить.

## О модуле
Модуль `app` — точка сборки приложения TrainingApp. Содержит `Application` класс, `MainActivity`, граф навигации, реализацию `Navigator` и `NavigationModule` (Hilt-биндинг `Navigator`). **Не содержит бизнес-логику.** **Не зависит от Room напрямую** — Hilt-модуль БД (`DatabaseModule`) живёт в `core/database`, а репозитории биндятся в `data`-подмодулях фичей.

Полное и актуальное описание внутренней структуры модуля находится в файле **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md)**.
Утверждённый визуальный дизайн главной оболочки находится внутри фичи-владельца: **[`feature-main/UI_DESIGN.md`](../features/feature-main/UI_DESIGN.md)**, технический макет — **[`feature-main/UI_MOCKUPS.html`](../features/feature-main/UI_MOCKUPS.html)**.

- **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md) нужно обновлять после любых изменений в структуре модуля** — добавление/удаление папок, ключевых файлов.
- Общие архитектурные/стилистические замечания для всего Android-проекта — в [`Android/AGENTS.md`](../AGENTS.md).

## Назначение
- `Application` класс — инициализация Hilt (`@HiltAndroidApp`), Timber (`Timber.plant(DebugTree())` в debug-сборке).
- `MainActivity` — хост для Fragment'ов, `@AndroidEntryPoint`.
- Граф навигации (Jetpack Navigation) — XML/DSL, описывает переходы между экранами.
- Реализация `Navigator` (`NavigatorImpl`) — маппинг `Screen` → `NavDirections`/`NavController` вызовы. Инжектируется через Hilt по интерфейсу из `core/navigation`.
- `NavigationModule` (Hilt) — `@Binds` `Navigator` → `NavigatorImpl`.
- `ReleaseErrorTree` — дерево Timber для production-логирования (отправка ошибок в краш-репорт).
- Ресурсы приложения (строки, темы, иконки) — в `src/main/res/`.

## Архитектурные замечания
- **Бизнес-логику сюда не класть.** Вся бизнес-логика — в feature-модулях (`features/<name>/domain/`) и `core/`.
- `app` НЕ зависит от Room напрямую. Hilt-модуль `DatabaseModule` (`@Provides` для `TrainingDatabase` и DAO) живёт в `core/database`. Репозитории биндятся в `data`-подмодулях фичей (`@Binds` интерфейс→реализация).
- `app` зависит от: `feature-*/data` (Hilt-биндинги репозиториев) + `feature-*/ui` (экраны) + `core/database` (Hilt-модуль БД) + `core:navigation` + нужные узкие модули `core:common:*`. Feature-модули НЕ зависят от `app`.
- `Navigator` — единственное место, где `app` знает о конкретных экранах feature-модулей (через граф навигации).
- DI: `@HiltAndroidApp` на `Application`, `@AndroidEntryPoint` на `Activity`/`Fragment`. Подробнее про Hilt-аннотации — в [`Android/AGENTS.md`](../AGENTS.md).
