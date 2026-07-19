package com.vasev.trainingapp.logs

import android.util.Log
import timber.log.Timber

/**
 * Timber tree for release builds — only forwards ERROR-level logs to Logcat.
 * Дерево Timber для release-сборок — пропускает только логи уровня ERROR в Logcat.
 *
 * In production we do not want verbose/debug/info logs (privacy, noise, performance),
 * but we still want to see crashes and fatal errors in Logcat. This tree filters by
 * `Log.ERROR` priority. For richer crash reporting (with stack traces, breadcrumbs,
 * user feedback) a dedicated tool like Firebase Crashlytics can be planted later —
 * see the ROADMAP note about error reporting / "report a bug" button.
 * В проде нам не нужны verbose/debug/info логи (приватность, шум, производительность),
 * но мы хотим видеть краши и фатальные ошибки в Logcat. Это дерево фильтрует по приоритету
 * `Log.ERROR`. Для более богатой отчётности о крашах (со стектрейсами, хлебными крошками,
 * обратной связью пользователя) позже можно подключить Firebase Crashlytics —
 * см. заметку в ROADMAP про отчёт об ошибках / кнопку "сообщить о баге".
 */
class ReleaseErrorTree : Timber.Tree() {

    /**
     * Only log when priority is ERROR or higher (ASSERT). Everything else is dropped.
     * Логируем только при приоритете ERROR и выше (ASSERT). Всё остальное отбрасывается.
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // NOTE: we use `android.util.Log` directly (not `Timber.*`) because this class IS
        // the Timber tree implementation. Calling `Timber.e(...)` here would recurse
        // infinitely (Timber → tree → Timber → tree ...). `Log` is the platform sink.
        // ВАЖНО: используем `android.util.Log` напрямую (не `Timber.*`), потому что этот
        // класс сам является реализацией дерева Timber. Вызов `Timber.e(...)` здесь привёл бы
        // к бесконечной рекурсии (Timber → дерево → Timber → дерево ...). `Log` — платформенный приёмник.
        if (priority < Log.ERROR) return
        Log.println(priority, tag, message)
        if (t != null) {
            Log.println(priority, tag, Log.getStackTraceString(t))
        }
    }
}
