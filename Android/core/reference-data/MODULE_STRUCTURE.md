# Структура модуля `core/reference-data`

> **Важно:** Этот файл нужно обновлять после изменений в структуре модуля (добавление/удаление моделей, портов).

## Обзор
Модуль `core/reference-data` — доменные модели и порты (интерфейсы) репозиториев для справочных данных (упражнения, мышцы, группы мышц, связи мышц). Чистый домен без Room-аннотаций. Реализации портов — в [`core/database`](../database/MODULE_STRUCTURE.md).

## Структура
```
core/reference-data/
├── build.gradle.kts          ← Конфигурация модуля (Android Library, Kotlin, Coroutines)
└── src/
    └── main/
        ├── AndroidManifest.xml   ← Манифест библиотеки (минимальный)
        └── java/com/vasev/trainingapp/core/referencedata/
            ├── model/             ← Domain-модели (data class, без Room-аннотаций)
            │   ├── types/          ← enum-ы (типы полей моделей), пакет ...model.types
            │   │   ├── ExerciseType.kt         ← enum: DYNAMIC, STATIC
            │   │   ├── MuscleInvolvement.kt     ← enum: PRIMARY, SECONDARY
            │   │   └── MuscleRelation.kt        ← enum: ANTAGONIST, SYNERGIST
            │   ├── Exercise.kt            ← Упражнение (id, name, type, isBuiltin, isDeleted, ...)
            │   ├── ExerciseMuscle.kt      ← Связь упражнения и мышцы (exerciseId, muscleId, involvement)
            │   ├── Muscle.kt              ← Мышца (id, groupId, name, description, imageUrl)
            │   ├── MuscleGroup.kt         ← Группа мышц (id, name, imageUrl)
            │   └── MuscleRelationEntry.kt ← Связь мышц (muscleId, relatedMuscleId, relation)
            └── repository/        ← Порты (интерфейсы репозиториев)
                ├── ExerciseRepository.kt      ← Порт доступа к упражнениям
                ├── MuscleGroupRepository.kt   ← Порт доступа к группам мышц
                └── MuscleRepository.kt        ← Порт доступа к мышцам (пока пустой)
```

## Назначение элементов

### `build.gradle.kts`
Конфигурация Gradle-модуля: плагины (Android Library, Kotlin), зависимости (Coroutines для `Flow` в портах), внутренний модуль `core/common`.

### `model/types/` — enum-ы
3 enum-а, используемых как типы полей в domain-моделях. Вынесены в отдельный подпакет `...model.types` для консистентности с `core/database/entity/types/`.
- `ExerciseType` — enum: DYNAMIC, STATIC.
- `MuscleInvolvement` — enum: PRIMARY, SECONDARY.
- `MuscleRelation` — enum: ANTAGONIST, SYNERGIST.

### `model/` — domain-модели
Чистые Kotlin `data class` без аннотаций внешних фреймворков. Используются feature-модулями и доменом.
- `Exercise` — упражнение: id, name, type, isBuiltin, isDeleted, createdByUserId, description, imageUrl.
- `ExerciseMuscle` — связь упражнения и мышцы: exerciseId, muscleId, involvement.
- `Muscle` — мышца: id, groupId, name, description, imageUrl.
- `MuscleGroup` — группа мышц: id, name, imageUrl.
- `MuscleRelationEntry` — связь мышц: muscleId, relatedMuscleId, relation.

### `repository/` — порты репозиториев
Интерфейсы доступа к справочным данным. Методы: `suspend` для разовых операций, `Flow` для реактивных запросов. Реализации — в `core/database` (Room-адаптер).
- `ExerciseRepository` — `getById`, `getByRemoteId`, `getMusclesForExercise`, `softDelete`, `observeAll`, `observeBuiltin`, `observeCreatedByUser`.
- `MuscleGroupRepository` — `getById`, `observeAll`.
- `MuscleRepository` — пока пустой (будет заполнен по мере разработки).

## Правила
- При добавлении/удалении модели, порта или enum-а — обновить этот файл.
- Модели — без аннотаций Room/Retrofit/сериализации. Чистые `data class`.
- Enum-ы хранить в `model/types/`, `data class` — в `model/`.
- Порты — `interface` с `suspend`/`Flow`-методами. Имена — `<Entity>Repository`.
- Реализации портов — в `core/database`, инжектируются через Hilt по интерфейсу.
- Feature-модули зависят от `core/reference-data`, но не от `core/database`.
