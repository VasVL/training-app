# Структура модуля `core:common:ui`

```text
ui/
├── AGENTS.md
├── MODULE_STRUCTURE.md
├── build.gradle.kts                    ← Android Library, core-ktx и Material-атрибуты
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
            ├── colors_graphite.xml        ← графитовая палитра по умолчанию
            └── dimens.xml                 ← общие радиусы UI-элементов
```

## Назначение элементов

### `build.gradle.kts`

Подключает AndroidX Core и Material Components. Прямая зависимость на Material нужна самому модулю, потому что общие drawable- и color-ресурсы используют атрибуты темы `colorSurface` и `colorOnSurface`; модуль не полагается на транзитивные зависимости потребителей.

### `PendingView`

Медленно меняет прозрачность заглушки между двумя значениями и останавливает анимацию после отсоединения от окна. Без дополнительной настройки использует `bg_pending`. Экран может передать другую геометрию через `android:background`, но `PendingView` всё равно применит общий цвет `common_pending_background` и единую анимацию.

### Pending-ресурсы

`common_pending_background` строится на основе текущего `colorOnSurface`, поэтому заглушки сохраняют одинаковый контраст в разных темах и на разных поверхностях. `bg_pending_oval` подходит для круглого или вытянутого овального placeholder: итоговые пропорции определяются размерами самой View.

### Палитры тем

`colors_graphite.xml` содержит значения графитовой палитры по умолчанию. Общие палитры находятся в этом модуле, чтобы `app` мог выбирать их в своих стилях темы, а feature-модули — разрешать цветовые ресурсы при самостоятельной сборке без зависимости от `app`.
