package com.vasev.trainingapp.feature.auth.contract

import com.vasev.trainingapp.feature.auth.contract.entity.ActiveUserSummary
import kotlinx.coroutines.flow.Flow

/**
 * Provides the currently active user profile to other features.
 * Предоставляет текущий активный профиль пользователя другим фичам.
 */
interface ActiveUserProvider {

    /**
     * Observes the active user profile summary.
     * Наблюдает за сводкой активного профиля пользователя.
     *
     * Emits `null` when no active profile exists.
     * Эмитит `null`, когда активного профиля нет.
     */
    fun observeActiveUser(): Flow<ActiveUserSummary?>
}
