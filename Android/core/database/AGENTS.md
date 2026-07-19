# Контекст модуля `core/database`

## О модуле
Модуль `core/database` — локальная база данных приложения (Room). Содержит все `@Entity` (таблицы), `@Dao` (доступ к данным), `TrainingDatabase` (единую Room-базу), `Converters` (TypeConverters для enum-ов) и `DatabaseModule` (Hilt-модуль с `@Provides` для `TrainingDatabase` и всех DAO). Это общая Room-инфраструктура: DAO доступны feature-модулям через DI (Hilt). Реализации репозиториев (адаптеры для портов, объявленных в `domain` feature-модулей) живут в `data`-подмодулях фичей (Ports & Adapters / Clean Architecture) — там же маппинг `Entity` ↔ domain model.

Полное и актуальное описание внутренней структуры модуля находится в файле **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md)**.

- **[`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md) нужно обновлять после любых изменений в структуре модуля** — добавление/удаление Entity/DAO, изменение схемы.
- Общие архитектурные/стилистические замечания для всего Android-проекта — в [`Android/AGENTS.md`](../../AGENTS.md).

## Назначение
- **Единая Room-база** `TrainingDatabase` для всего приложения (`version = 1`, `exportSchema = true`). Все 22 таблицы перечислены в одной `@Database`.
- **Entity** (`entity/`) — описание таблиц Room (`@Entity`, `@ForeignKey`, `@Index`, `@ColumnInfo`). 22 Entity покрывают домены: пользователи, справочники (упражнения/мышцы), программы, дневник тренировок, вес, питание.
- **DAO** (`dao/`) — доступ к данным (`@Dao`): `suspend`-функции для разовых операций, `Flow` для реактивных запросов. 22 DAO, по одному на Entity.
- **Converters** (`Converters.kt`) — `internal object` с `@TypeConverter` для всех enum-ов. Room хранит enum как `name` (String).
- **DatabaseModule** (`di/DatabaseModule.kt`) — Hilt-модуль (`@Module @InstallIn(SingletonComponent)`): `@Provides @Singleton` для `TrainingDatabase` (через `Room.databaseBuilder`) и `@Provides` для всех 22 DAO. Это убирает хардкод Room из `app` — `app` не зависит от Room напрямую.
- **schemas/** — экспортированные JSON-схемы Room (по одной на версию). Используются для миграций и проверок схемы в CI.

## Архитектурные замечания

### Ports & Adapters
- `core/database` — общая Room-инфраструктура: `TrainingDatabase`, DAO, `Converters`, `DatabaseModule` (Hilt `@Provides` для `TrainingDatabase` и всех DAO). Не содержит репозиториев и мапперов.
- Реализации репозиториев (адаптеры для портов, объявленных в `domain` feature-модулей) живут в `data`-подмодулях фичей (например, `feature-auth/data/repository/UserRepositoryImpl`). Они инжектируют DAO из `core/database` (через Hilt), маппят `Entity` ↔ domain model и реализуют интерфейсы из `domain/repository`.
- Домен (`domain` фич) **не зависит от `core/database`**. Зависимость направлена в сторону адаптера: `feature-*/data` → `core/database` + `feature-*/domain`.
- Реализации репозиториев инжектируются через Hilt по интерфейсу (биндинги в `feature-*/data/di`).

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
