# Структура модуля `feature-main:domain`

```text
domain/
├── AGENTS.md
├── MODULE_STRUCTURE.md
└── build.gradle.kts ← Чистый Kotlin-модуль; зависит от feature-auth:contract
```

## Назначение элементов

### `build.gradle.kts`

Конфигурация Kotlin/JVM-модуля. Зависимость на `feature-auth:contract` даёт domain-сценариям доступ только к публичной сводке активного профиля; зависимость на Coroutines нужна для `Flow`.

## Правила

- Добавлять `entity/` только вместе с первой domain-моделью.
- Добавлять `usecase/` только вместе с первым domain-сценарием.
- Не дублировать здесь модели, которыми владеет `feature-auth`.
- При добавлении файлов или папок обновлять эту структуру.
