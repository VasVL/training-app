package com.vasev.trainingapp.feature.workout.domain.entity

import com.vasev.trainingapp.feature.workout.domain.entity.type.WorkoutLogStatus

/**
 * Domain model of a workout log entry (a planned, in-progress, completed or skipped workout) /
 * Domain-модель записи в дневнике тренировок (запланированная, в процессе, завершённая или пропущенная)
 *
 * `adjustmentPercent` is the actual adjustment percent chosen by the user at workout start /
 * `adjustmentPercent` — фактический процент корректировки, выбранный пользователем при старте тренировки
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class WorkoutLog(
    val adjustmentPercent: Double,
    val comment: String?,
    val completedAt: Long?,
    val dayId: Long?,
    val id: Long,
    val microcycleId: Long?,
    val programId: Long?,
    val scheduledDate: Long,
    val startedAt: Long?,
    val status: WorkoutLogStatus,
    val title: String,
    val userId: Long,
)
