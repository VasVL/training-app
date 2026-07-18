# Контекст модуля `app`

> **Важно:** Замечания, даваемые пользователем в процессе разработки, имеют высший приоритет. Этот файл хранит накопленный контекст; при конфликте с ним — уточнять у пользователя как поступить.

## О модуле
Модуль `app` — точка сборки приложения TrainingApp. Содержит `Application` класс, `MainActivity`, граф навигации и реализацию `Navigator`. **Не содержит бизнес-логику.**

Полное и актуальное описание внутренней структуры модуля находится в файле **[`PROJECT_STRUCTURE.md`](PROJECT_STRUCTURE.md)**.

- **[`PROJECT_STRUCTURE.md`](PROJECT_STRUCTURE.md) нужно обновлять после любых изменений в структуре модуля** — добавление/удаление папок, ключевых файлов.
- Общие архитектурные/стилистические замечания для всего Android-проекта — в [`Android/AGENTS.md`](../AGENTS.md).

## Назначение
- `Application` класс — инициализация Hilt (`@HiltAndroidApp`), Timber (`Timber.plant(DebugTree())` в debug-сборке).
- `MainActivity` — хост для Fragment'ов, `@AndroidEntryPoint`.
- Граф навигации (Jetpack Navigation) — XML/DSL, описывает переходы между экранами.
- Реализация `Navigator` — маппинг `Screen` → `NavDirections`/`NavController` вызовы. Инжектируется через Hilt по интерфейсу из `core/navigation`.
- Ресурсы приложения (строки, темы, иконки) — в `src/main/res/`.

## Архитектурные замечания
- **Бизнес-логику сюда не класть.** Вся бизнес-логика — в feature-модулях (`features/<name>/domain/`) и `core/`.
- `app` зависит от feature-модулей и `core/navigation`, но feature-модули НЕ зависят от `app`.
- `Navigator` — единственное место, где `app` знает о конкретных экранах feature-модулей (через граф навигации).
- DI: `@HiltAndroidApp` на `Application`, `@AndroidEntryPoint` на `Activity`/`Fragment`. Подробнее про Hilt-аннотации — в [`Android/AGENTS.md`](../AGENTS.md).

## История замечаний
(Здесь будут записываться новые архитектурные/стилистические замечания по мере разработки)
