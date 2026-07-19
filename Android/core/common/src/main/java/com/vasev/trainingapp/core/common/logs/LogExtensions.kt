package com.vasev.trainingapp.core.common.logs

import timber.log.Timber

/**
 * Lazy logging extensions for Timber — the message lambda is only invoked when at least one
 * tree is planted, so string interpolation is skipped entirely in builds without logging.
 * Ленивые extension-функции для Timber — лямбда сообщения вызывается только когда посажено
 * хотя бы одно дерево, поэтому интерполяция строки полностью пропускается в сборках без логов.
 *
 * Why: `Timber.d("navigate: $screen")` always builds the string even if no tree is planted
 * (e.g. a release build with only an error-level tree would still allocate the message).
 * These extensions check `Timber.treeCount` first and only then call the lambda.
 * Зачем: `Timber.d("navigate: $screen")` всегда строит строку, даже если ни одного дерева нет
 * (например, release-сборка с только error-деревом всё равно аллоцировала бы сообщение).
 * Эти extension-функции сначала проверяют `Timber.treeCount` и только потом вызывают лямбду.
 *
 * NOTE: `Timber.treeCount` is a public API that returns the number of planted trees. We use it
 * as a cheap guard. The actual level filtering still happens inside Timber (a debug tree will
 * print d/i/w/e; our ReleaseErrorTree drops everything below ERROR).
 * ВАЖНО: `Timber.treeCount` — публичный API, возвращающий количество посаженных деревьев.
 * Мы используем его как дешёвую проверку. Фильтрация по уровню всё равно происходит внутри Timber
 * (debug-дерево печатает d/i/w/e; наше ReleaseErrorTree отбрасывает всё ниже ERROR).
 */

/** Debug-level lazy log / Лог уровня debug (ленивый). */
inline fun Timber.Forest.d(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.d(message())
    }
}

/** Error-level lazy log / Лог уровня error (ленивый). */
inline fun Timber.Forest.e(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.e(message())
    }
}

/** Error-level lazy log with a throwable / Лог уровня error (ленивый) с throwable. */
inline fun Timber.Forest.e(throwable: Throwable?, message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.e(throwable, message())
    }
}

/** Info-level lazy log / Лог уровня info (ленивый). */
inline fun Timber.Forest.i(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.i(message())
    }
}

/** Warning-level lazy log / Лог уровня warning (ленивый). */
inline fun Timber.Forest.w(message: () -> String) {
    if (Timber.treeCount > 0) {
        Timber.w(message())
    }
}
