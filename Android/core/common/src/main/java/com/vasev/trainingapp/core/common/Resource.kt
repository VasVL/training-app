package com.vasev.trainingapp.core.common

/**
 * Generic wrapper for repository/UI state — represents the outcome of an operation.
 * Универсальный обёртка для состояния репозитория/UI — представляет результат операции.
 *
 * Used by repositories to wrap data fetched from DB/network so that ViewModels
 * can distinguish loading, success and error states in a type-safe way.
 * Используется репозиториями для обёртки данных из БД/сети, чтобы ViewModel
 * мог различать состояния загрузки, успеха и ошибки типобезопасно.
 *
 * Pattern: sealed interface → exhaustive `when` in ViewModel/UI.
 * Паттерн: sealed interface → исчерпывающий `when` в ViewModel/UI.
 */
sealed interface Resource<out T> {

    /**
     * Loading state — operation is in progress, no data yet.
     * Состояние загрузки — операция выполняется, данных пока нет.
     */
    object Loading : Resource<Nothing>

    /**
     * Success state — operation completed, [data] is available.
     * Состояние успеха — операция завершена, [data] доступна.
     */
    data class Success<T>(val data: T) : Resource<T>

    /**
     * Error state — operation failed. [message] is a human-readable text,
     * [cause] is the optional underlying throwable (for logging/debugging).
     * Состояние ошибки — операция провалена. [message] — человекочитаемый текст,
     * [cause] — опциональная исходная ошибка (для логирования/отладки).
     */
    data class Error(
        val cause: Throwable? = null,
        val message: String,
    ) : Resource<Nothing>
}
