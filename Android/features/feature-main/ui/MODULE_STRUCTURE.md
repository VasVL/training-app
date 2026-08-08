# Структура модуля `feature-main:ui`

```text
ui/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts ← Android Library, ViewBinding, Hilt, Navigation UI и Safe Args
└── src/main/
    ├── java/com/vasev/trainingapp/feature/main/ui/
    │   ├── main/
    │   │   ├── MainFragment.kt         ← тулбар, шторка и нижняя навигация
    │   │   ├── entity/
    │   │   │   └── MainUiState.kt      ← Loading, Error и Ready оболочки
    │   │   ├── mapper/
    │   │   │   └── MainActiveUserMapper.kt
    │   │   │                              ← auth contract → UI-сущность
    │   │   └── viewmodel/
    │   │       └── MainViewModel.kt    ← состояние, retry и внешняя навигация
    │   └── placeholder/
    │       └── MainPlaceholderFragment.kt
    │                                      ← временные заглушки разделов
    └── res/
        ├── color/
        │   └── main_bottom_navigation_item.xml
        ├── drawable/
        │   ├── background_main_drawer_avatar.xml
        │   └── ic_main_*.xml            ← иконки оболочки
        ├── layout/
        │   ├── fragment_main.xml
        │   ├── fragment_main_placeholder.xml
        │   └── header_main_drawer.xml
        ├── menu/
        │   ├── menu_main_bottom_navigation.xml
        │   └── menu_main_drawer.xml
        ├── navigation/
        │   └── main_nav_graph.xml       ← дочерний граф пяти вкладок и заглушек
        └── values/
            ├── dimens.xml
            └── strings.xml
```

## Назначение элементов

### `build.gradle.kts`

Конфигурация Android UI-модуля. Подключает XML Views, ViewBinding, Hilt, Navigation Component, Safe Args, публичный контракт auth и общие core-модули. Прямых зависимостей на data- или domain-модули auth нет.

Утверждённые визуальные решения описаны в [`../UI_DESIGN.md`](../UI_DESIGN.md), технический макет хранится в [`../UI_MOCKUPS.html`](../UI_MOCKUPS.html).

### Пакет `main`

`MainFragment` отображает состояние и связывает View-компоненты. `MainViewModel` наблюдает активного пользователя через `feature-auth:contract`, обрабатывает повторную загрузку и инициирует переходы в auth. `MainActiveUserMapper` не пропускает contract-модель непосредственно в `MainUiState`.

### Пакет `placeholder`

Временный экран для ещё не реализованных разделов. Заголовок передаётся через типобезопасный `MainPlaceholderFragmentArgs`, сгенерированный Safe Args из дочернего графа.

### Ресурсы

Модуль владеет всей разметкой оболочки, её меню, иконками, строками, размерами и дочерним графом. `app` содержит только корневой NavHost и подключает `MainFragment` как destination.

## Правила

- Создавать пакеты и каталоги ресурсов только с первым файлом соответствующего назначения.
- Поддерживать эту структуру актуальной при добавлении или удалении ключевых файлов.
