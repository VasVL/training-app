# Roadmap реализации TrainingApp

> Порядок реализации функционала. Обновлять по мере прогресса (отмечать выполненное ✅).

## Фаза 0. Фундамент
- [ ] **DI (Hilt)** — `Application` класс (`@HiltAndroidApp`), `MainActivity` (`@AndroidEntryPoint`), Hilt-модуль для `TrainingDatabase` + DAO. Без DI ничего не заработает.
- [ ] **core/common** — `Resource<T>` (sealed class Loading/Success/Error). Используется везде в Repository.
- [ ] **Реализация Navigator в `app`** — `NavigatorImpl` (маппинг `Screen` → `NavDirections`), nav_graph.xml. Без этого нет навигации.
- [ ] **Coil** — подключить в `app` (implementation(libs.coil)). Библиотека загрузки картинок.

## Фаза 1. Пользователи (feature-auth)
Точка входа: без пользователя ничего не работает — все данные привязаны к `userId`.
- [ ] `domain`: `UserRepository` (порт), `UserMaxRepository` (порт), модели (User, UserMax).
- [ ] `core/database`: адаптеры `UserRepositoryImpl`, `UserMaxRepositoryImpl` (реализация портов через DAO).
- [ ] `ui`: `UserSelectScreen` (выбор пользователя), `UserEditScreen` (создание/редактирование = "О себе").
- [ ] Hilt-модуль для Repository.
- [ ] Навигация: `AuthScreen` в nav_graph.
- [ ] **Результат:** можно зайти, выбрать/создать пользователя, заполнить "О себе" (имя, вес, рост, разовые максимумы).

## Фаза 2. Справочники (core/reference-data + feature-exercises + feature-anatomy)
Программы и тренировки ссылаются на упражнения и мышцы. Нужны данные.
- [ ] `core/database`: адаптеры `ExerciseRepositoryImpl`, `MuscleRepositoryImpl`, `MuscleGroupRepositoryImpl`.
- [ ] Заполнение БД вшитыми данными (упражнения, мышцы, группы) — JSON-ассеты, импорт при первом запуске.
- [ ] `feature-exercises/ui`: список упражнений, группы мышц, поиск.
- [ ] `feature-anatomy/ui`: атлас, детали мышцы.
- [ ] **Результат:** справочник упражнений и мышц доступен, данные в БД.

## Фаза 3. Программы (feature-programs) — просмотр
Основная фича, сначала просмотр (без создания).
- [ ] `domain`: `ProgramRepository` (порт), модели (Program, Microcycle, Day, ExerciseSet, SetTemplate).
- [ ] `core/database`: адаптер `ProgramRepositoryImpl`.
- [ ] Вшитые программы (JSON-ассеты, импорт при первом запуске).
- [ ] `ui`: `ProgramListScreen` (список + табы + пагинация), `ProgramDetailScreen` (описание + микроциклы ViewPager).
- [ ] Навигация: `ProgramScreen` в nav_graph.
- [ ] **Результат:** можно листать программы, открывать описание, смотреть микроциклы/дни/упражнения.

## Фаза 4. Текущая тренировка (feature-workout) — выполнение
Нужна программа, чтобы выполнять тренировку.
- [ ] `domain`: `WorkoutLogRepository` (порт), модели (WorkoutLog, WorkoutLogExercise, WorkoutLogSet).
- [ ] `core/database`: адаптер `WorkoutLogRepositoryImpl`.
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
- [ ] `domain`: `WeightRepository` (порт), адаптер.
- [ ] `ui`: график веса, измерения.
- [ ] **Результат:** можно вносить вес, смотреть график.

## Фаза 8. Настройки (feature-settings)
- [ ] `ui`: настройки (единицы измерения, пользователь по умолчанию, порядок табов).
- [ ] **Результат:** настраиваемое приложение.

## Фаза 9. Справка (feature-help)
- [ ] `ui`: СРЦ, о приложении. Простой статичный контент.
- [ ] **Результат:** справочные экраны.

## Вторая фаза (отложено)
- [ ] `core/network` + синхронизация с сетью
- [ ] `feature-nutrition` (дневник питания)
- [ ] Настраиваемая навигация (5 из N)
- [ ] Рейтинги/комментарии программ
- [ ] Диплинки
- [ ] Регистрация аккаунтов, удалённые данные

---

## Основной экран (MVP)
- Тулбар: гамбургер слева → шторка (Настройки, О себе).
- Bottom navigation: 5 кнопок (список программ, создание программы, текущая тренировка, календарь, отслеживание веса).
- В MVP набор кнопок зафиксирован. Настраиваемость (5 из N) — вторая фаза.
