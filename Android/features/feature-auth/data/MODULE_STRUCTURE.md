# Структура модуля `feature-auth:data`

```text
data/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts
└── src/main/
    ├── AndroidManifest.xml
    └── java/com/vasev/trainingapp/feature/auth/data/
        ├── di/
        │   └── AuthDataModule.kt             ← Hilt-биндинги auth
        ├── mapper/
        │   ├── ActiveUserContractMapper.kt  ← Room-проекция → contract-сводка
        │   ├── AuthMapper.kt                 ← User/UserMax ↔ Room-модели
        │   └── UserListItemMapper.kt         ← проекция списка → domain-модель
        ├── provider/
        │   └── ActiveUserProviderImpl.kt     ← Flow активного профиля для других фич
        └── repository/
            ├── UserMaxRepositoryImpl.kt     ← реализация UserMaxRepository
            └── UserRepositoryImpl.kt         ← реализация UserRepository
```

## Назначение элементов

### `provider/ActiveUserProviderImpl.kt`

Читает из `UserDao` только `ActiveUserProjection`, преобразует её в `ActiveUserSummary` и реализует публичный интерфейс `ActiveUserProvider`. Полная domain-модель пользователя для этого сценария не загружается.

### `di/AuthDataModule.kt`

Связывает Hilt-интерфейсы с реализациями: domain-репозитории и публичный провайдер активного профиля.

## Правила

- Репозитории реализуют порты domain-модуля.
- Провайдеры реализуют узкие публичные интерфейсы contract-модуля.
- Маппинг выполняется в data-слое рядом с источником и потребителем данных.
- При добавлении файлов или папок обновлять эту структуру.
