package com.vasev.trainingapp.core.common.logging

import timber.log.Timber

/**
 * Lazy logging extensions for Timber — message creation is skipped without planted trees.
 * Ленивые расширения Timber — создание сообщения пропускается без подключённых деревьев.
 */

/** Debug-level lazy log / Ленивый лог уровня debug. */
inline fun Timber.Forest.d(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.d(message())
    }
}

/** Error-level lazy log / Ленивый лог уровня error. */
inline fun Timber.Forest.e(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.e(message())
    }
}

/** Error-level lazy log with throwable / Ленивый лог error с исключением. */
inline fun Timber.Forest.e(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.e(throwable, message())
    }
}

/** Info-level lazy log / Ленивый лог уровня info. */
inline fun Timber.Forest.i(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.i(message())
    }
}

/** Warning-level lazy log / Ленивый лог уровня warning. */
inline fun Timber.Forest.w(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.w(message())
    }
}
