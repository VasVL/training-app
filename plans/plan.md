# Plan: Isolate persistence behind repository interfaces (Clean Architecture)

> Цель: `app` не зависит от Room напрямую. Каждая фича имеет `domain` (модели + интерфейсы репозиториев, без Room) и `data` (реализации репозиториев + мапперы, зависит от `core/database` за DAO). `core/database` — общая Room-инфраструктура.

## Архитектура

```
:core:database              TrainingDatabase, все Entity, все DAO, Converters. Hilt-модуль: @Provides TrainingDatabase + DAO.
:core:common                Resource, LogExtensions (без enum-ов).

:features:feature-auth
├── contract/              AuthScreen (как сейчас)
├── domain/                User, UserMax (модели) + UserRepository, UserMaxRepository (интерфейсы). Без Room.
├── data/                  UserRepositoryImpl, UserMaxRepositoryImpl + мапперы. Зависит от core/database, feature-auth/domain. Hilt @Binds.
└── ui/                    зависит от feature-auth/domain

:features:feature-programs
├── contract/              ProgramScreen
├── domain/                Program, Microcycle, MicrocycleDay, ExerciseSet, SetTemplate, ProgramCategory, ProgramTag, ProgramPrerequisite + интерфейсы репозиториев
├── data/                  реализации + мапперы. Зависит от core/database, feature-programs/domain.
└── ui/

:features:feature-workout
├── contract/, domain/ (WorkoutLog, WorkoutLogExercise, WorkoutLogSet + интерфейсы), data/, ui/

:features:feature-weight
├── contract/, domain/ (WeightMeasurement + интерфейс), data/, ui/

:features:feature-nutrition   НОВЫЙ
├── contract/, domain/ (FoodItem, FoodLog + интерфейсы), data/, ui/

:features:feature-exercises
├── contract/, domain/ (Exercise + интерфейс ExerciseRepository), data/, ui/

:features:feature-anatomy
├── contract/, domain/ (Muscle, MuscleGroup + интерфейсы MuscleRepository, MuscleGroupRepository), data/, ui/

:app                       Зависит от core/database, feature-*/data, feature-*/ui. БЕЗ Room напрямую.
```

### Enum-ы (дублирование — вариант C)

- `core/database/entity/types/` — enum-ы для Entity (оставить как есть, не трогать).
- `feature-*/domain/` — enum-ы для domain-моделей (свои в каждой фиче, рядом с моделями).
- Маппинг enum↔enum в `feature-*/data/` (классы с методом map).

### Зависимости (ациклический граф)

```
core/database              → Room, KSP (без фичей)
core/common                → (ничего)
feature-*/domain           → (чистый, без Room)
feature-*/data             → feature-*/domain, core/database
feature-*/ui               → feature-*/domain
app                        → core/database, feature-*/data, feature-*/ui
```

Циклов нет. `app` не зависит от Room напрямую.

### Удаления

- `core/reference-data` → удалить. Модели справочников → в `feature-exercises/domain` (Exercise) и `feature-anatomy/domain` (Muscle, MuscleGroup). Интерфейсы репозиториев → туда же.
- `app/di/DatabaseModule.kt` → удалить. Hilt-модуль `@Provides TrainingDatabase + DAO` → в `core/database`.
- Room-зависимость из `app/build.gradle.kts` → убрать.

## Шаги

### Шаг 1. Hilt-модуль в core/database
- [ ] Добавить Hilt plugin + ksp(hilt-compiler) в `core/database/build.gradle.kts`.
- [ ] Создать `core/database/src/main/java/com/vasev/trainingapp/core/database/di/DatabaseModule.kt`: @Module @InstallIn(SingletonComponent), @Provides @Singleton TrainingDatabase (Room.databaseBuilder), @Provides для всех 22 DAO.
- [ ] Удалить `app/src/main/java/com/vasev/trainingapp/di/DatabaseModule.kt`.
- [ ] Убрать `implementation(libs.room.runtime)` из `app/build.gradle.kts`.
- [ ] Проверить сборку: `cd Android && ./gradlew assembleDebug` (проверка, что Hilt-модуль переехал корректно).

### Шаг 2. feature-auth: domain + data
- [ ] Создать `features/feature-auth/domain/` (модуль уже есть, добавить зависимости: Hilt не нужен, только coroutines для Flow).
- [ ] В `feature-auth/domain/`: enum-ы (UserRole, Gender, WeightUnit, HeightUnit, MeasurementUnit) + модели (User, UserMax) + интерфейсы (UserRepository, UserMaxRepository). Пакет `com.vasev.trainingapp.feature.auth.domain`.
- [ ] Создать модуль `features/feature-auth/data/` (build.gradle.kts, AndroidManifest.xml). Зависимости: `feature-auth/domain`, `core/database`, Hilt, KSP.
- [ ] В `feature-auth/data/`: UserRepositoryImpl, UserMaxRepositoryImpl + мапперы (UserEntity↔User, UserMaxEntity↔UserMax, enum↔enum). Пакет `com.vasev.trainingapp.feature.auth.data`.
- [ ] Hilt-модуль в `feature-auth/data/`: @Module @InstallIn(SingletonComponent), @Binds UserRepository→UserRepositoryImpl, UserMaxRepository→UserMaxRepositoryImpl.
- [ ] Зарегистрировать `:features:feature-auth:data` в `settings.gradle.kts`.

### Шаг 3. feature-programs: domain + data
- [ ] В `feature-programs/domain/`: enum-ы (MicrocycleDayType, SetType, WeightType, RepType, PrerequisiteType, ProgramCategory) + модели (Program, Microcycle, MicrocycleDay, ExerciseSet, SetTemplate, ProgramCategory, ProgramTag, ProgramPrerequisite) + интерфейсы (ProgramRepository, MicrocycleRepository, MicrocycleDayRepository, ExerciseSetRepository, SetTemplateRepository, ProgramCategoryRepository, ProgramTagRepository, ProgramPrerequisiteRepository).
- [ ] Создать `features/feature-programs/data/`: реализации + мапперы + Hilt @Binds.
- [ ] Зарегистрировать `:features:feature-programs:data` в `settings.gradle.kts`.

### Шаг 4. feature-workout: domain + data
- [ ] В `feature-workout/domain/`: enum-ы (WorkoutLogStatus, WorkoutLogSetStatus, SetType) + модели (WorkoutLog, WorkoutLogExercise, WorkoutLogSet) + интерфейсы (WorkoutLogRepository, WorkoutLogExerciseRepository, WorkoutLogSetRepository).
- [ ] Создать `features/feature-workout/data/`: реализации + мапперы + Hilt @Binds.
- [ ] Зарегистрировать `:features:feature-workout:data`.

### Шаг 5. feature-weight: domain + data
- [ ] В `feature-weight/domain/`: модель (WeightMeasurement) + интерфейс (WeightRepository).
- [ ] Создать `features/feature-weight/data/`: реализация + маппер + Hilt @Binds.
- [ ] Зарегистрировать `:features:feature-weight:data`.

### Шаг 6. feature-nutrition: domain + data (НОВЫЙ)
- [ ] Создать `features/feature-nutrition/` с подмодулями contract, domain, data, ui.
- [ ] В `feature-nutrition/domain/`: модели (FoodItem, FoodLog) + интерфейсы (FoodItemRepository, FoodLogRepository).
- [ ] В `feature-nutrition/data/`: реализации + мапперы + Hilt @Binds.
- [ ] Зарегистрировать все 4 подмодуля в `settings.gradle.kts`.

### Шаг 7. Справочники: feature-exercises + feature-anatomy (перенос из core/reference-data)
- [ ] В `feature-exercises/domain/`: enum (ExerciseType) + модель (Exercise) + интерфейс (ExerciseRepository). Перенести из `core/reference-data/model/Exercise.kt` и `core/reference-data/repository/ExerciseRepository.kt`.
- [ ] Создать `features/feature-exercises/data/`: ExerciseRepositoryImpl + маппер + Hilt @Binds.
- [ ] В `feature-anatomy/domain/`: enum (MuscleInvolvement, MuscleRelation) + модели (Muscle, MuscleGroup, ExerciseMuscle, MuscleRelationEntry) + интерфейсы (MuscleRepository, MuscleGroupRepository, ExerciseMuscleRepository, MuscleRelationRepository).
- [ ] Создать `features/feature-anatomy/data/`: реализации + мапперы + Hilt @Binds.
- [ ] Зарегистрировать `:features:feature-exercises:data`, `:features:feature-anatomy:data`.
- [ ] Удалить `core/reference-data` (модуль, build.gradle.kts, запись в settings.gradle.kts, все файлы).

### Шаг 8. app — обновить зависимости
- [ ] В `app/build.gradle.kts`: `implementation(project(":core:database"))` (для Hilt-модуля), `implementation(project(":features:feature-auth:data"))`, ..., для всех фичей. Убрать Room.
- [ ] Оставить `app/di/NavigationModule.kt`.

### Шаг 9. Проверка сборки
- [ ] `cd Android && ./gradlew assembleDebug`. Ожидаемый результат: BUILD SUCCESSFUL.
- [ ] Если ошибки — исправить импорты, зависимости, маппинг.

### Шаг 10. Коммит
- [ ] `git add -A && git commit -m "Isolate persistence behind repository interfaces (Clean Architecture)"`.

## Замечания

- Комментарии в коде на двух языках (английский + русский).
- Переменные/параметры конструктора в алфавитном порядке.
- Hilt-аннотации объяснять в комментарии.
- Entity (Room) ≠ domain-модель — маппинг обязателен.
- Enum-ы дублируются: в core/database (для Entity) и в feature-*/domain (для domain-моделей). Маппинг enum↔enum в feature-*/data.
- Реализации репозиториев в feature-*/data (по модели пользователя).
- DAO и Entity остаются в core/database (общая Room-инфраструктура).
- Справочники (Exercise, Muscle) — в feature-exercises/domain и feature-anatomy/domain (без общего feature/common).
- Разные фичи могут определять свои модели для одной сущности (разные поля).
