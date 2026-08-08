# Структура папки `features`

> **Важно:** Этот файл нужно обновлять после изменений в структуре папки (добавление/удаление feature-модулей).

## Обзор
Папка `features/` — контейнер для feature-модулей. Каждая фича — отдельный Gradle-модуль.

## Структура
```
features/
├── feature-anatomy/          ← Анатомический атлас
│   ├── contract/             ← Kotlin/JVM: AnatomyScreen (маршруты), порты
│   ├── data/                  ← Реализации репозиториев + мапперы + Hilt-биндинги
│   ├── domain/               ← Kotlin/JVM: бизнес-логика (модели, enum-ы, интерфейсы репозиториев)
│   └── ui/                   ← UI-слой
├── feature-auth/             ← Пользователи, "О себе"
│   ├── contract/             ← AuthScreen (маршруты), порты
│   ├── data/
│   ├── domain/
│   └── ui/
├── feature-calendar/         ← Календарь
│   ├── contract/             ← CalendarScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-exercises/        ← Упражнения
│   ├── contract/             ← ExerciseScreen (маршруты), порты
│   ├── data/
│   ├── domain/
│   └── ui/
├── feature-help/             ← Справка (СРЦ, о приложении)
│   ├── contract/             ← HelpScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-main/             ← Постоянная главная оболочка
│   ├── UI_DESIGN.md          ← Утверждённый дизайн оболочки
│   ├── UI_MOCKUPS.html       ← Технический HTML-макет
│   ├── contract/             ← MainScreen (публичный маршрут)
│   ├── domain/
│   └── ui/                   ← Тулбар, шторка, нижняя навигация и заглушки
├── feature-nutrition/        ← Дневник питания
│   ├── contract/             ← NutritionScreen (маршруты), порты
│   ├── data/
│   ├── domain/
│   └── ui/
├── feature-programs/         ← Программы, микроциклы, дни
│   ├── contract/             ← ProgramScreen (маршруты), порты
│   ├── data/
│   ├── domain/
│   └── ui/
├── feature-settings/         ← Настройки
│   ├── contract/             ← SettingsScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-weight/           ← Отслеживание веса
│   ├── contract/             ← WeightScreen (маршруты), порты
│   ├── data/
│   ├── domain/
│   └── ui/
└── feature-workout/          ← Текущая тренировка, выполнение
    ├── contract/             ← WorkoutScreen (маршруты), порты
    ├── data/
    ├── domain/
    └── ui/
```

У каждой фичи есть подмодули `contract`, `domain`, `ui`. Подмодуль `data` есть у фичей с persistence (anatomy, auth, exercises, nutrition, programs, weight, workout). У `feature-calendar`, `feature-help`, `feature-main`, `feature-settings` `data`-модуля нет (нет собственного persistence). Подмодуль `common` — необязательный (пока ни у одной фичи его нет).

## Назначение подмодулей

### `contract/`
Чистый Kotlin/JVM-модуль с публичными портами фичи — интерфейсами, моделями и **Screen-маршрутами** (`sealed interface XxxScreen : Screen`), которые фича отдаёт наружу. Другие фичи зависят от `contract`, а не от `domain`. Contract-модуль зависит от `core:navigation` (для `Screen`). Это реализует инверсию зависимостей (Dependency Inversion) и распределённую навигацию.

### `domain/`
Чистый Kotlin/JVM-модуль с бизнес-логикой фичи: domain-моделями (`entity/`), enum-ами (`entity/type/`), интерфейсами репозиториев (`repository/`, портами для `data`), use-case (`usecase/`, по мере появления). Не зависит от `domain` других фич, Room и Android API. Внешние зависимости объявляет как интерфейсы (порты), реализации приходят через DI из `data`.

### `data/`
Реализации репозиториев (адаптеры для портов из `domain/repository`) + мапперы (Room Entity ↔ domain-модель) + Hilt-биндинги (`@Binds` интерфейс→реализация). Зависит от `domain` фичи и `core/database` (DAO). Инжектирует DAO из `core/database` через Hilt, маппит `Entity` ↔ domain model.

Структура `data`-подмодуля: `mapper/` (классы-мапперы), `repository/` (реализации репозиториев), `di/` (Hilt-модули).

### `ui/`
UI-слой: Fragment, ViewModel, ViewBinding. Не содержит бизнес-логику. ViewModel управляет единым `UiState` через `StateFlow`, отправляет намерения в UseCase/Repository (через интерфейсы из `domain`).

### `common/`
Вспомогательный код фичи (если нужен). Необязательный подмодуль.

## Правила
- При добавлении/удалении feature-модуля — обновить этот файл и `settings.gradle.kts`.
- `domain`-модуль одной фичи НЕ зависит от `domain`-модуля другой фичи.
- Зависимости между фичами — только через `contract`-модули.
- Persistence изолирована за портами: `domain` объявляет интерфейсы репозиториев, `data` реализует их через DAO из `core/database`.
- Могут быть не все подмодули, но других быть не должно.
