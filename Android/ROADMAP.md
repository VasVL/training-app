# Roadmap реализации TrainingApp

> Порядок реализации функционала. Обновлять по мере прогресса (отмечать выполненное ✅).

## Фаза 0. Фундамент
- [x] **DI (Hilt)** — `Application` класс (`@HiltAndroidApp`), `MainActivity` (`@AndroidEntryPoint`), `DatabaseModule` в `core/database` (Hilt `@Provides` для `TrainingDatabase` + DAO). Без DI ничего не заработает.
- [x] **core/common** — `Resource<T>` (sealed class Loading/Success/Error), `LogExtensions`. Используется везде в Repository.
- [x] **Реализация Navigator в `app`** — `NavigatorImpl` (маппинг `Screen` → `NavDirections`), `NavigationModule` (Hilt `@Binds`), nav_graph.xml. Без этого нет навигации.
- [x] **Coil** — подключить в `app` (implementation(libs.coil)). Библиотека загрузки картинок.
- [x] **Изоляция persistence** — `domain` фичей объявляет порты (интерфейсы репозиториев), `data`-подмодули реализуют их через DAO из `core/database`. `app` не зависит от Room напрямую.

## Фаза 1. Пользователи (feature-auth)
Точка входа: без пользователя ничего не работает — все данные привязаны к `userId`.
- [x] `domain`: `UserRepository` (порт), `UserMaxRepository` (порт), модели (User, UserMax), enum-ы (UserRole, Gender, WeightUnit, HeightUnit, MeasurementUnit).
- [x] `data`: адаптеры `UserRepositoryImpl`, `UserMaxRepositoryImpl` (реализация портов через DAO из `core/database`) + мапперы + Hilt `@Binds`.
- [ ] `ui`: `UserSelectScreen` (выбор пользователя), `UserEditScreen` (создание/редактирование = "О себе").
- [ ] Навигация: `AuthScreen` в nav_graph.
- [ ] **Результат:** можно зайти, выбрать/создать пользователя, заполнить "О себе" (имя, вес, рост, разовые максимумы).

## Фаза 2. Справочники (feature-exercises + feature-anatomy)
Программы и тренировки ссылаются на упражнения и мышцы. Нужны данные. Модели справочников живут в `feature-exercises/domain` (Exercise) и `feature-anatomy/domain` (Muscle, MuscleGroup).
- [x] `feature-exercises/domain`: модель (Exercise) + enum (ExerciseType) + интерфейс (ExerciseRepository).
- [x] `feature-exercises/data`: `ExerciseRepositoryImpl` + маппер + Hilt `@Binds`.
- [x] `feature-anatomy/domain`: модели (Muscle, MuscleGroup, ExerciseMuscle, MuscleRelationEntry) + enum-ы (MuscleInvolvement, MuscleRelation) + интерфейсы (MuscleRepository, MuscleGroupRepository, ExerciseMuscleRepository, MuscleRelationRepository).
- [x] `feature-anatomy/data`: реализации + мапперы + Hilt `@Binds`.
- [ ] Заполнение БД вшитыми данными (упражнения, мышцы, группы) — JSON-ассеты, импорт при первом запуске.
- [ ] `feature-exercises/ui`: список упражнений, группы мышц, поиск.
- [ ] `feature-anatomy/ui`: атлас, детали мышцы.
- [ ] **Результат:** справочник упражнений и мышц доступен, данные в БД.

## Фаза 3. Программы (feature-programs) — просмотр
Основная фича, сначала просмотр (без создания).
- [x] `domain`: интерфейсы репозиториев (ProgramRepository, MicrocycleRepository, MicrocycleDayRepository, ExerciseSetRepository, SetTemplateRepository, ProgramCategoryRepository, ProgramTagRepository, ProgramPrerequisiteRepository), модели (Program, Microcycle, MicrocycleDay, ExerciseSet, SetTemplate, ProgramCategoryEntry, ProgramTag, ProgramPrerequisite), enum-ы (MicrocycleDayType, SetType, WeightType, RepType, PrerequisiteType, ProgramCategory).
- [x] `data`: реализации репозиториев + мапперы + Hilt `@Binds`.
- [ ] Вшитые программы (JSON-ассеты, импорт при первом запуске).
- [ ] `ui`: `ProgramListScreen` (список + табы + пагинация), `ProgramDetailScreen` (описание + микроциклы ViewPager).
- [ ] Навигация: `ProgramScreen` в nav_graph.
- [ ] **Результат:** можно листать программы, открывать описание, смотреть микроциклы/дни/упражнения.

## Фаза 4. Текущая тренировка (feature-workout) — выполнение
Нужна программа, чтобы выполнять тренировку.
- [x] `domain`: интерфейсы (WorkoutLogRepository, WorkoutLogExerciseRepository, WorkoutLogSetRepository), модели (WorkoutLog, WorkoutLogExercise, WorkoutLogSet), enum-ы (WorkoutLogStatus, WorkoutLogSetStatus, SetType).
- [x] `data`: реализации репозиториев + мапперы + Hilt `@Binds`.
- [ ] Логика: "Начать программу" → создаёт `workout_logs` (PLANNED) для всех тренировок программы в календаре.
- [ ] `ui`: `WorkoutSelectionScreen` (список запланированных), `WorkoutSessionScreen` (выполнение), `ExerciseSessionScreen` (подходы, статусы).
- [ ] Расчёт рабочего веса (adjustmentPercent, проценты от максимума).
- [ ] **Результат:** можно начать программу, выполнять тренировки, отмечать подходы.

## Фаза 5. Календарь (feature-calendar)
- [ ] `domain`: запросы `workout_logs` по датам.
- [ ] `ui`: календарь с отмеченными тренировками, тап на день → тренировка.
- [ ] **Результат:** видно расписание тренировок.

## Фаза 6. Создание программ (feature-programs — редактирование)
Просмотр важнее, создание сложнее (4 уровня вложенности).
- [ ] `ui`: `ProgramEditScreen`, `MicrocycleEditScreen`, `DayEditScreen`, `ExerciseEditScreen`.
- [ ] Nested nav graph для редактирования.
- [ ] **Результат:** можно создавать свои программы.

## Фаза 7. Отслеживание веса (feature-weight)
- [x] `domain`: модель (WeightMeasurement) + интерфейс (WeightRepository).
- [x] `data`: `WeightRepositoryImpl` + маппер + Hilt `@Binds`.
- [ ] `ui`: график веса, измерения.
- [ ] **Результат:** можно вносить вес, смотреть график.

## Фаза 8. Настройки (feature-settings)
- [ ] `ui`: настройки (единицы измерения, пользователь по умолчанию, порядок табов).
- [ ] **Результат:** настраиваемое приложение.

## Фаза 9. Справка (feature-help)
- [ ] `ui`: СРЦ, о приложении. Простой статичный контент.
- [ ] **Результат:** справочные экраны.

## Метрики и отчёты об ошибках (отложено, не в MVP)
- [ ] **Обёртка над метриками** — единый интерфейс `AnalyticsTracker` / `CrashReporter` в core/metrics.
- [ ] **Метрика (одна для всех):** Firebase Crashlytics + Analytics — краши, события, воронки, Remote Config, A/B тесты.
- [ ] **Реклама (по стране, runtime-переключение):**
  - Россия — Yandex Mobile Ads (Яндекс).
  - Остальной мир — Google AdMob.
- [ ] **Runtime-переключение рекламы** по стране (Locale/SIM) — один APK, один listing в Google Play.
- [ ] **Кнопка "Сообщить о баге"** — в шторке (Настройки). Экран с описанием + приложить лог (Timber → файл в release).
- [ ] **Breadcrumbs** — последние действия пользователя перед крашем.
- [ ] **Push-уведомления** — напоминания о тренировке (Firebase Cloud Messaging).

## Фаза 10. Питание (feature-nutrition)
- [x] `domain`: модели (FoodItem, FoodLog) + интерфейсы (FoodItemRepository, FoodLogRepository).
- [x] `data`: реализации + мапперы + Hilt `@Binds`.
- [ ] `ui`: дневник питания, выбор еды.
- [ ] **Результат:** можно вести дневник питания.

## Вторая фаза (отложено)
- [ ] `core/network` + синхронизация с сетью
- [ ] Настраиваемая навигация (5 из N)
- [ ] Рейтинги/комментарии программ
- [ ] Диплинки
- [ ] Регистрация аккаунтов, удалённые данные

---

## Основной экран (MVP)
- Тулбар: гамбургер слева → шторка (Настройки, О себе).
- Bottom navigation: 5 кнопок (список программ, создание программы, текущая тренировка, календарь, отслеживание веса).
- В MVP набор кнопок зафиксирован. Настраиваемость (5 из N) — вторая фаза.
