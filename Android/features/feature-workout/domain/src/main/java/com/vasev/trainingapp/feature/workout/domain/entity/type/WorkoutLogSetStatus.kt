package com.vasev.trainingapp.feature.workout.domain.entity.type

/**
 * Status of a single set in a workout log / Статус одного подхода в дневнике тренировки
 */
enum class WorkoutLogSetStatus {
    COMPLETED,
    FAILED,
    NOT_STARTED,
    PARTIAL,
}
