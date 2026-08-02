# Структура модуля `feature-main:ui`

```text
ui/
├── AGENTS.md
├── MODULE_STRUCTURE.md
└── build.gradle.kts ← Android Library, ViewBinding, Hilt и Navigation UI
```

## Назначение элементов

### `build.gradle.kts`

Конфигурация Android UI-модуля. Подключает XML Views, ViewBinding, Hilt, Navigation Component и зависимости на публичные контракты и domain-слой главной фичи.

## Планируемая структура при реализации оболочки

```text
src/main/
├── AndroidManifest.xml
├── java/com/vasev/trainingapp/feature/main/ui/
│   ├── MainFragment.kt
│   ├── entity/
│   ├── mapper/
│   └── viewmodel/
└── res/
    ├── layout/
    ├── menu/
    └── navigation/
```

## Правила

- Создавать пакеты и каталоги ресурсов только с первым файлом соответствующего назначения.
- Поддерживать эту структуру актуальной при добавлении или удалении ключевых файлов.
