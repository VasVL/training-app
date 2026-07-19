# Контекст модуля `core/database`

## О модуле
Модуль `core/database` — локальная база данных приложения (Room). Содержит все `@Entity` (таблицы), `@Dao` (доступ к данным), `TrainingDatabase` (единую Room-базу) и `Converters` (TypeConverters для enum-ов). Это **адаптер** для портов, объявленных в [`core/reference-data`](../reference-data/AGENTS.md) (Ports & Adapters): домен работает со своими моделями и интерфейсами-портами, а `core/database` реализует эти порты и маппит `Entity` → domain model.

Полное и актуальное описание внутренней структуры модуля находится в файле **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md)**.

- **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md) нужно обновлять после любых изменений в структуре модуля** — добавление/удаление Entity/DAO, изменение схемы.
- Общие архитектурные/стилистические замечания для всего Android-проекта — в [`Android/AGENTS.md`](../../AGENTS.md).

## Назначение
- **Единая Room-база** `TrainingDatabase` для всего приложения (`version = 1`, `exportSchema = true`). Все 22 таблицы перечислены в одной `@Database`.
- **Entity** (`entity/`) — описание таблиц Room (`@Entity`, `@ForeignKey`, `@Index`, `@ColumnInfo`). 22 Entity покрывают домены: пользователи, справочники (упражнения/мышцы), программы, дневник тренировок, вес, питание.
- **DAO** (`dao/`) — доступ к данным (`@Dao`): `suspend`-функции для разовых операций, `Flow` для реактивных запросов. 22 DAO, по одному на Entity.
- **Converters** (`Converters.kt`) — `internal object` с `@TypeConverter` для всех enum-ов. Room хранит enum как `name` (String).
- **schemas/** — экспортированные JSON-схемы Room (по одной на версию). Используются для миграций и проверок схемы в CI.

## Архитектурные замечания

### Ports & Adapters
- `core/database` **реализует порты** из `core/reference-data` (например, `ExerciseRepository`, `MuscleGroupRepository`). Реализации маппят `Entity` → domain model и обратно.
- Домен (`core/reference-data`, `domain` фич) **не зависит от `core/database`**. Зависимость направлена в сторону адаптера: `core/database` → `core/reference-data`.
- Реализации портов инжектируются через Hilt по интерфейсу.

### Стратегия внешних ключей (FK)
- **CASCADE** — удаление родителя удаляет дочерние строки. Применяется для "владельческих" связей:
  - `users` → `user_maxes`, `workout_logs`, `weight_measurements`, `food_logs` (данные пользователя удаляются вместе с ним).
  - `muscle_groups` → `muscles`; `muscles` → `muscle_relations`.
  - `programs` → `microcycles`, `program_tags`, `program_categories`, `program_prerequisites`.
  - `microcycles` → `microcycle_days` → `workout_templates` → `exercise_sets` → `set_templates`.
  - `workout_logs` → `workout_log_exercises` → `workout_log_sets`.
- **SET_NULL** — удаление родителя оставляет дочернюю строку, обнуляя ссылку. Применяется, когда дочерняя запись должна пережить удаление родителя:
  - `users` → `exercises.createdByUserId`, `programs.createdByUserId` (вшитые/чужие программы и упражнения не удаляются при удалении пользователя).
  - `programs`/`microcycles`/`microcycle_days` → `workout_logs` (дневник сохраняется при удалении программы — запись просто отвязывается).
- **RESTRICT** — удаление родителя запрещено, пока на него есть ссылки. Применяется к справочникам, которые нельзя жёстко удалить:
  - `exercises` ← `user_maxes`, `exercise_muscles`, `exercise_sets`, `set_templates`, `workout_log_exercises`, `program_prerequisites` (упражнение, на которое есть ссылка, нельзя удалить; используется мягкое удаление).
  - `food_items` ← `food_logs`.

### Мягкое удаление упражнений
- `ExerciseEntity` имеет поле `isDeleted: Boolean`. Пользовательские упражнения помечаются `isDeleted=true` (скрыты из списка), но остаются в БД — программы и дневник не разрушаются.
- Вшитые упражнения (`isBuiltin=true`) удалить нельзя.
- Периодическая очистка: упражнение с `isDeleted=true` и без ссылок может быть удалено из БД (логика в адаптере).
- **Восстановление:** пользователь может восстановить мягко удалённое упражнение (`isDeleted=false`). UI должен предоставлять доступ к удалённым упражнениям (например, отдельный экран/фильтр "корзина") и кнопку восстановления.
- UI показывает удалённое упражнение с плашкой "удалено" при прохождении программы.

### Локальный + удалённый ID
- Все синхронизируемые таблицы имеют `id` (локальный, `autoGenerate`) + `remoteId: String?` (nullable, unique index). Сетевая синхронизация — вторая фаза.

### Конвенции
- `TrainingDatabase` и `Converters` — `internal` (не видны вне модуля). Доступ только через DAO и порты.
- Имена таблиц — во множественном числе (`users`, `exercises`, ...). Имена колонок — в camelCase через `@ColumnInfo(name = ...)`.
- Составные первичные ключи — для link-таблиц (`exercise_muscles`, `muscle_relations`, `program_tags`, `program_categories`).
- Все FK-колонки имеют `@Index` для производительности.
