# Структура модуля `feature-auth:contract`

```text
contract/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts
└── src/main/java/com/vasev/trainingapp/feature/auth/contract/
    ├── ActiveUserProvider.kt       ← Поток сводки активного профиля
    ├── AuthScreen.kt               ← Маршруты экранов пользователей
    ├── UserEditRequest.kt          ← Аргументы редактирования профиля
    └── entity/
        └── ActiveUserSummary.kt    ← Публичные id и имя активного профиля
```

## Назначение элементов

### `ActiveUserProvider.kt`

Узкий публичный интерфейс для других фичей. Возвращает `Flow<ActiveUserSummary?>`, чтобы оболочка приложения могла реактивно показывать имя активного профиля без доступа к БД или полному `UserRepository`.

### `AuthScreen.kt`

Типизированные маршруты к экранам пользователей: выбор, создание первого и нового профиля, редактирование существующего.

### `UserEditRequest.kt`

Данные, необходимые `app` для передачи в экран редактирования через Navigation Component.

### `entity/ActiveUserSummary.kt`

Минимальная публичная модель активного профиля: идентификатор для перехода и имя для отображения в оболочке.

## Правила

- Не раскрывать через контракт полную domain-модель `User` или DAO.
- Новые публичные модели добавлять в `entity/`.
- При добавлении файлов или папок обновлять эту структуру.
