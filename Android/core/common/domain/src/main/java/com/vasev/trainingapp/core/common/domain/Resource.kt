package com.vasev.trainingapp.core.common.domain

/**
 * Generic wrapper for repository/UI state — represents the outcome of an operation.
 * Универсальная обёртка для состояния репозитория/UI — представляет результат операции.
 */
sealed interface Resource<out T> {

    /** Loading state — operation is in progress. / Состояние загрузки — операция выполняется. */
    data object Loading : Resource<Nothing>

    /** Success state — [data] is available. / Состояние успеха — [data] доступна. */
    data class Success<T>(val data: T) : Resource<T>

    /** Error state — operation failed. / Состояние ошибки — операция завершилась с ошибкой. */
    data class Error(
        val cause: Throwable? = null,
        val message: String,
    ) : Resource<Nothing>
}
