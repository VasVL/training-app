# Структура модуля `feature-main:contract`

```text
contract/
├── AGENTS.md
├── MODULE_STRUCTURE.md
└── build.gradle.kts ← Чистый Kotlin-модуль; зависит от core:navigation
```

## Назначение элементов

### `build.gradle.kts`

Конфигурация Kotlin/JVM-модуля и зависимость на `core:navigation`. Будущие публичные маршруты главной оболочки будут реализовывать общий интерфейс `Screen`.

## Правила

- Будущие маршруты размещать в пакете `com.vasev.trainingapp.feature.main.contract`.
- Сохранять модуль независимым от Android API и конкретной реализации UI.
- При добавлении файлов или папок обновлять эту структуру.
