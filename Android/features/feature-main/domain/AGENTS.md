# Контекст модуля `feature-main:domain`

## Назначение

Чистый Kotlin-модуль с domain-сценариями главной оболочки. Он получает только безопасные сведения об активном профиле через публичный контракт `feature-auth` и не знает о Room или Android UI.

Полная внутренняя структура находится в [`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md).

## Правила

- Domain-модели размещать в `entity/`, enum-ы — в `entity/type/`.
- Сценарии размещать в `usecase/` только при появлении первого сценария.
- Не добавлять Room, Android API, XML-ресурсы и реализации доступа к данным.
- При изменении структуры обновлять [`MODULE_STRUCTURE.md`](MODULE_STRUCTURE.md).
