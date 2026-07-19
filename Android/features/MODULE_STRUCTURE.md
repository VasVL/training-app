# Структура папки `features`

> **Важно:** Этот файл нужно обновлять после изменений в структуре папки (добавление/удаление feature-модулей).

## Обзор
Папка `features/` — контейнер для feature-модулей. Каждая фича — отдельный Gradle-модуль.

## Структура
```
features/
├── feature-anatomy/          ← Анатомический атлас
│   ├── contract/             ← AnatomyScreen (маршруты), порты
│   ├── domain/               ← Бизнес-логика
│   └── ui/                   ← UI-слой
├── feature-auth/             ← Пользователи, "О себе"
│   ├── contract/             ← AuthScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-calendar/         ← Календарь
│   ├── contract/             ← CalendarScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-exercises/        ← Упражнения
│   ├── contract/             ← ExerciseScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-help/             ← Справка (СРЦ, о приложении)
│   ├── contract/             ← HelpScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-programs/         ← Программы, микроциклы, дни
│   ├── contract/             ← ProgramScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-settings/         ← Настройки
│   ├── contract/             ← SettingsScreen (маршруты), порты
│   ├── domain/
│   └── ui/
├── feature-weight/           ← Отслеживание веса
│   ├── contract/             ← WeightScreen (маршруты), порты
│   ├── domain/
│   └── ui/
└── feature-workout/          ← Текущая тренировка, выполнение
    ├── contract/             ← WorkoutScreen (маршруты), порты
    ├── domain/
    └── ui/
```

У каждой фичи есть подмодули `contract`, `domain`, `ui`. Подмодуль `common` — необязательный (пока ни у одной фичи его нет).

## Назначение подмодулей

### `contract/`
Публичные порты фичи — интерфейсы, модели и **Screen-маршруты** (`sealed interface XxxScreen : Screen`), которые фича отдаёт наружу. Другие фичи зависят от `contract`, а не от `domain`. Contract-модуль зависит от `core/navigation` (для `Screen`). Это реализует инверсию зависимостей (Dependency Inversion) и распределённую навигацию.

### `domain/`
Бизнес-логика фичи: UseCase, интерфейсы Repository (порты для внешних зависимостей), domain models. Не зависит от `domain` других фич. Внешние зависимости объявляет как интерфейсы (порты), реализации приходят через DI.

### `ui/`
UI-слой: Fragment, ViewModel, ViewBinding. Не содержит бизнес-логику. ViewModel управляет единым `UiState` через `StateFlow`, отправляет намерения в UseCase/Repository.

### `common/`
Вспомогательный код фичи (если нужен). Необязательный подмодуль.

## Правила
- При добавлении/удалении feature-модуля — обновить этот файл и `settings.gradle.kts`.
- `domain`-модуль одной фичи НЕ зависит от `domain`-модуля другой фичи.
- Зависимости между фичами — только через `contract`-модули.
- Могут быть не все подмодули, но других быть не должно.
