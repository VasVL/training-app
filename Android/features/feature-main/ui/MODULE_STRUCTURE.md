# Структура модуля `feature-main:ui`

```text
ui/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts ← Android Library, ViewBinding, Hilt и Navigation UI
└── src/main/java/com/vasev/trainingapp/feature/main/ui/main/
    ├── entity/
    │   └── MainUiState.kt          ← Loading, Error и Ready главной оболочки
    ├── mapper/
    │   └── MainActiveUserMapper.kt ← auth contract → UI-сущность
    └── viewmodel/
        └── MainViewModel.kt        ← состояние, повторная загрузка и навигация профиля
```

## Назначение элементов

### `build.gradle.kts`

Конфигурация Android UI-модуля. Подключает XML Views, ViewBinding, Hilt, Navigation Component, публичный контракт auth и общие core-модули. Прямых зависимостей на data- или domain-модули auth нет.

## Планируемое расширение при переносе оболочки

```text
src/main/
├── AndroidManifest.xml
├── java/com/vasev/trainingapp/feature/main/ui/main/
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
