# Структура модуля `core/database`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление Entity/DAO, изменение схемы).

## Обзор
Модуль `core/database` — локальная база данных приложения (Room). Единая `TrainingDatabase` содержит 22 таблицы (Entity) и 22 DAO. Модуль — общая Room-инфраструктура: `DatabaseModule` (Hilt) предоставляет `TrainingDatabase` и все DAO через DI. Реализации репозиториев (адаптеры для портов из `domain` фичей) живут в `data`-подмодулях фичей.

## Структура
```
core/database/
├── build.gradle.kts          ← Конфигурация модуля (Android Library, Kotlin, KSP, Room)
├── schemas/                  ← Экспортированные JSON-схемы Room
│   └── com.vasev.trainingapp.core.database.TrainingDatabase/
│       └── 1.json            ← Схема версии 1
└── src/
    └── main/
        ├── AndroidManifest.xml   ← Манифест библиотеки (минимальный)
        └── java/com/vasev/trainingapp/core/database/
            ├── Converters.kt       ← TypeConverters для всех enum-ов (internal object)
            ├── TrainingDatabase.kt ← Единая Room-база (@Database, @TypeConverters, internal)
            ├── dao/                 ← 22 @Dao-интерфейса
            ├── di/
            │   └── DatabaseModule.kt ← Hilt-модуль: @Provides TrainingDatabase + все 22 DAO
            │   ├── ExerciseDao.kt
            │   ├── ExerciseMuscleDao.kt
            │   ├── ExerciseSetDao.kt
            │   ├── FoodItemDao.kt
            │   ├── FoodLogDao.kt
            │   ├── MicrocycleDao.kt
            │   ├── MicrocycleDayDao.kt
            │   ├── MuscleDao.kt
            │   ├── MuscleGroupDao.kt
            │   ├── MuscleRelationDao.kt
            │   ├── ProgramCategoryDao.kt
            │   ├── ProgramDao.kt
            │   ├── ProgramPrerequisiteDao.kt
            │   ├── ProgramTagDao.kt
            │   ├── SetTemplateDao.kt
            │   ├── UserDao.kt
            │   ├── UserMaxDao.kt
            │   ├── WeightMeasurementDao.kt
            │   ├── WorkoutLogDao.kt
            │   ├── WorkoutLogExerciseDao.kt
            │   ├── WorkoutLogSetDao.kt
            │   └── WorkoutTemplateDao.kt
            └── entity/              ← @Entity (таблицы) + enum-ы
                ├── types/           ← enum-ы (типы полей Entity), пакет ...entity.types
                │   ├── ExerciseType.kt          ← enum: DYNAMIC, STATIC
                │   ├── Gender.kt                ← enum: FEMALE, MALE
                │   ├── HeightUnit.kt            ← enum: CM, INCHES
                │   ├── MeasurementUnit.kt       ← enum: CM, KG, LBS, METERS, REPS, SECONDS
                │   ├── MicrocycleDayType.kt     ← enum: REST, WORKOUT
                │   ├── MuscleInvolvement.kt     ← enum: PRIMARY, SECONDARY
                │   ├── MuscleRelation.kt        ← enum: ANTAGONIST, SYNERGIST
                │   ├── PrerequisiteType.kt     ← enum: BODYWEIGHT, ONE_REP_MAX, REPS
                │   ├── ProgramCategory.kt      ← enum (вшитые категории программ)
                │   ├── RepType.kt              ← enum: DURATION, FIXED, PERCENT_OF_MAX, RPE
                │   ├── SetType.kt              ← enum: DROPSET, SINGLE, SUPERSET
                │   ├── UserRole.kt             ← enum: OWNER, TRAINEE
                │   ├── WeightType.kt           ← enum: ABSOLUTE, BODYWEIGHT, PERCENT, PERCENT_PLUS_BODYWEIGHT
                │   ├── WeightUnit.kt          ← enum: KG, LBS
                │   ├── WorkoutLogSetStatus.kt  ← enum: COMPLETED, FAILED, NOT_STARTED, PARTIAL
                │   └── WorkoutLogStatus.kt    ← enum: COMPLETED, IN_PROGRESS, PLANNED, SKIPPED
                ├── ExerciseEntity.kt
                ├── ExerciseMuscleEntity.kt
                ├── ExerciseSetEntity.kt
                ├── FoodItemEntity.kt
                ├── FoodLogEntity.kt
                ├── MicrocycleDayEntity.kt
                ├── MicrocycleEntity.kt
                ├── MuscleEntity.kt
                ├── MuscleGroupEntity.kt
                ├── MuscleRelationEntity.kt
                ├── ProgramCategoryEntity.kt
                ├── ProgramEntity.kt
                ├── ProgramPrerequisiteEntity.kt
                ├── ProgramTagEntity.kt
                ├── SetTemplateEntity.kt
                ├── UserEntity.kt
                ├── UserMaxEntity.kt
                ├── WeightMeasurementEntity.kt
                ├── WorkoutLogEntity.kt
                ├── WorkoutLogExerciseEntity.kt
                ├── WorkoutLogSetEntity.kt
                └── WorkoutTemplateEntity.kt
```

## Назначение элементов

### `build.gradle.kts`
Конфигурация Gradle-модуля: плагины (Android Library, Kotlin, KSP, Hilt), зависимости (Room runtime/ktx/compiler через KSP, Coroutines, Hilt). KSP-аргументы `room.schemaLocation` и `room.incremental` задают экспорт схем и инкрементальную обработку.

### `TrainingDatabase.kt`
Единая Room-база (`@Database(version = 1, exportSchema = true)`). Перечисляет все 22 Entity и 22 DAO. `@TypeConverters(Converters::class)` регистрирует конвертеры enum-ов. Класс `internal abstract` — доступ только внутри модуля.

### `di/DatabaseModule.kt`
Hilt-модуль (`@Module @InstallIn(SingletonComponent)`): `@Provides @Singleton` для `TrainingDatabase` (через `Room.databaseBuilder`) и `@Provides` для всех 22 DAO. Это убирает хардкод Room из `app` — `app` не зависит от Room напрямую, а получает DAO (и, через `data`-модули фичей, репозитории) через Hilt.

### `Converters.kt`
`internal object` с `@TypeConverter`-функциями для всех enum-ов из `entity/types/`. Room хранит enum как `name` (String).

### `schemas/`
Экспортированные JSON-схемы Room (по одной на версию БД). Файл `1.json` — схема версии 1. Используются для миграций и проверок схемы.

### `entity/types/` — enum-ы
16 enum-ов, используемых как типы полей в Entity. Вынесены в отдельный подпакет `...entity.types`, чтобы не перемешиваться с `@Entity`-классами. Импортируются в Entity, DAO и `Converters`.

### `entity/` — таблицы по доменам (22 Entity)

**Пользователи:**
- `UserEntity` (`users`) — пользователь: role, name, weight, height, gender, birthDateEpochDay, weightUnit, heightUnit, isDefault, createdAt, remoteId.
- `UserMaxEntity` (`user_maxes`) — разовый максимум: userId (CASCADE), exerciseId (RESTRICT), maxValue, unit, measuredAt.

**Справочники:**
- `MuscleGroupEntity` (`muscle_groups`) — группа мышц: name, imageUrl, remoteId.
- `MuscleEntity` (`muscles`) — мышца: groupId (CASCADE), name, description, imageUrl, remoteId.
- `MuscleRelationEntity` (`muscle_relations`) — связь мышц: muscleId (CASCADE), relatedMuscleId (CASCADE), relation. Составной PK.
- `ExerciseEntity` (`exercises`) — упражнение: type, isBuiltin, isDeleted, createdByUserId (SET_NULL), name, description, imageUrl, remoteId.
- `ExerciseMuscleEntity` (`exercise_muscles`) — связь упражнения и мышцы: exerciseId (RESTRICT), muscleId (CASCADE), involvement. Составной PK.

**Программы:**
- `ProgramEntity` (`programs`) — программа: title, description, isBuiltin, isFavorite, canSkipWorkouts, recommendedAdjustmentPercent, createdByUserId (SET_NULL), createdAt, remoteId.
- `ProgramCategoryEntity` (`program_categories`) — вшитая категория-тег: programId (CASCADE), category. Составной PK.
- `ProgramTagEntity` (`program_tags`) — пользовательский тег: programId (CASCADE), tag. Составной PK.
- `ProgramPrerequisiteEntity` (`program_prerequisites`) — условие начала: programId (CASCADE), exerciseId (RESTRICT, nullable), type, requiredValue.
- `MicrocycleEntity` (`microcycles`) — микроцикл: programId (CASCADE, nullable для standalone), title, description, order, remoteId.
- `MicrocycleDayEntity` (`microcycle_days`) — день микроцикла: microcycleId (CASCADE), title, type, order.
- `WorkoutTemplateEntity` (`workout_templates`) — шаблон тренировки в дне: dayId (CASCADE), order.
- `ExerciseSetEntity` (`exercise_sets`) — упражнение в шаблоне: workoutTemplateId (CASCADE), exerciseId (RESTRICT), weightRefExerciseId (RESTRICT), setType, supersetGroupId, dropsetReductions, weightType, weightValue, repType, repValue, rpeValue, durationValue, restTimeSeconds, order.
- `SetTemplateEntity` (`set_templates`) — шаблон подхода: exerciseSetId (CASCADE), weightRefExerciseId (RESTRICT), weightType, weightValue, repType, repValue, rpeValue, durationValue, order.

**Дневник тренировок:**
- `WorkoutLogEntity` (`workout_logs`) — запись тренировки: userId (CASCADE), programId (SET_NULL), microcycleId (SET_NULL), dayId (SET_NULL), title, status, scheduledDate, startedAt, completedAt, adjustmentPercent, comment.
- `WorkoutLogExerciseEntity` (`workout_log_exercises`) — упражнение в записи: workoutLogId (CASCADE), exerciseId (RESTRICT), setType, supersetGroupId, isSkipped, order, durationSeconds.
- `WorkoutLogSetEntity` (`workout_log_sets`) — подход в записи: workoutLogExerciseId (CASCADE), status, plannedWeight, plannedReps, actualWeight, actualReps, restTimeSeconds, comment, order.

**Вес:**
- `WeightMeasurementEntity` (`weight_measurements`) — измерение веса: userId (CASCADE), weight, measuredAt.

**Питание (вторая фаза):**
- `FoodItemEntity` (`food_items`) — продукт: name, category, calories, protein, fat, carbs, imageUrl, remoteId.
- `FoodLogEntity` (`food_logs`) — запись дневника питания: userId (CASCADE), foodId (RESTRICT), amount, loggedAt.

### Планируемое расширение метрик

Текущая схема ещё содержит `UserMaxEntity` и фиксированные поля подходов. Для универсальных схем упражнений запланирована замена на набор связанных метрик:

- `ExerciseMetricDefinitionEntity` — схема результата упражнения: metricType, fixedValue (nullable), unit, isPrimary, comparisonDirection. Для пользовательского упражнения схема неизменяема; при другой схеме создаётся копия упражнения без истории.
- `UserRecordEntity` и `UserRecordMetricEntity` — личный результат и его метрики. Расчётные результаты дополнительно хранят исходные значения и формулу.
- `SetTemplateMetricEntity` — целевая метрика планового подхода.
- `WorkoutLogSetMetricEntity` — плановая и фактическая метрика выполненного подхода, включая снимок веса тела.
- Метрики включают дополнительный вес, собственный вес, собственный вес вместе с дополнительным весом, повторы, длительность и дистанцию. Процентная нагрузка хранит ссылку на метрику-источник результата.
- Дропсеты поддерживают формульное снижение от начального веса, ручные значения каждого подхода и режим «по ощущениям».

### `dao/`
22 `@Dao`-интерфейса, по одному на Entity. Методы: `suspend` для разовых операций (insert/update/delete), `Flow<...>` для реактивных запросов. Имена соответствуют Entity (например, `ExerciseDao` для `ExerciseEntity`).

## Правила
- При добавлении/удалении Entity или DAO — обновить этот файл, `TrainingDatabase.kt` (списки `entities` и `abstract fun`-DAO) и `Converters.kt` (если добавлен новый enum).
- При добавлении нового enum — создать файл в `entity/types/`, добавить `@TypeConverter`-пары в `Converters.kt`.
- При изменении схемы — увеличить `version` в `TrainingDatabase` и добавить миграцию; новая JSON-схема экспортируется автоматически.
- `TrainingDatabase` и `Converters` — `internal`, доступ только через DAO и порты.
- Все FK-колонки должны иметь `@Index`.
- Стратегия FK: CASCADE для владельческих связей, SET_NULL для сохранения дочерних записей, RESTRICT для справочников (использовать мягкое удаление).
- Enum-ы хранить в `entity/types/`, `@Entity`-классы — в `entity/`.
