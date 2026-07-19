package com.vasev.trainingapp.feature.workout.domain.entity

import com.vasev.trainingapp.feature.workout.domain.entity.type.WorkoutLogSetStatus

/**
 * Domain model of a single set in a workout log (planned vs actual values, status) /
 * Domain-модель одного подхода в записи дневника (плановые vs фактические значения, статус)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class WorkoutLogSet(
    val actualReps: Int?,
    val actualWeight: Double?,
    val comment: String?,
    val id: Long,
    val order: Int,
    val plannedReps: Int?,
    val plannedWeight: Double?,
    val restTimeSeconds: Int?,
    val status: WorkoutLogSetStatus,
    val workoutLogExerciseId: Long,
)
