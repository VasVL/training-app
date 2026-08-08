# Структура модуля `core:common:ui`

```text
ui/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts
└── src/main/
    ├── AndroidManifest.xml
    ├── java/com/vasev/trainingapp/core/common/ui/view/
    │   └── PendingView.kt                ← общая мерцающая заглушка
    └── res/
        ├── color/
        │   └── common_pending_background.xml
        │                                      ← единый theme-aware pending-цвет
        ├── drawable/
        │   ├── bg_pending.xml             ← стандартная скруглённая форма
        │   ├── bg_pending_oval.xml        ← овальная форма
        │   ├── ic_add.xml                 ← общая иконка добавления
        │   └── ic_check.xml               ← общая иконка подтверждения
        └── values/
            └── dimens.xml                 ← общие радиусы UI-элементов
```

## Назначение элементов

### `PendingView`

Медленно меняет прозрачность заглушки между двумя значениями и останавливает анимацию после отсоединения от окна. Без дополнительной настройки использует `bg_pending`. Экран может передать другую геометрию через `android:background`, но `PendingView` всё равно применит общий цвет `common_pending_background` и единую анимацию.

### Pending-ресурсы

`common_pending_background` строится на основе текущего `colorOnSurface`, поэтому заглушки сохраняют одинаковый контраст в разных темах и на разных поверхностях. `bg_pending_oval` подходит для круглого или вытянутого овального placeholder: итоговые пропорции определяются размерами самой View.
