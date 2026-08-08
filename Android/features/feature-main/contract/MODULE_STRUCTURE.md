# Структура модуля `feature-main:contract`

```text
contract/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts ← Чистый Kotlin-модуль; зависит от core:navigation
└── src/main/java/com/vasev/trainingapp/feature/main/contract/
    └── MainScreen.kt ← публичный маршрут постоянной оболочки
```

## Назначение элементов

### `build.gradle.kts`

Конфигурация Kotlin/JVM-модуля и зависимость на `core:navigation`. `MainScreen` реализует общий маркерный интерфейс `Screen`, не раскрывая Android UI главной фичи.

### `MainScreen.kt`

Публичный маршрут `MainScreen.Main`. Его используют auth-фича после создания или выбора профиля и модуль `app` при сопоставлении маршрута корневому destination.

## Правила

- Маршруты размещать в пакете `com.vasev.trainingapp.feature.main.contract`.
- Сохранять модуль независимым от Android API и конкретной реализации UI.
- При добавлении файлов или папок обновлять эту структуру.
