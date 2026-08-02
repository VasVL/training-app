package com.vasev.trainingapp.core.database.entity.projection

import com.vasev.trainingapp.core.database.entity.types.MeasurementUnit

/**
 * Minimal maximum and exercise fields required by the profile maximums list.
 * Минимальные поля максимума и упражнения, нужные списку максимумов профиля.
 */
data class UserMaxWithExerciseProjection(
    val exerciseId: Long,
    val exerciseName: String,
    val id: Long,
    val maxValue: Double,
    val measuredAt: Long,
    val unit: MeasurementUnit,
)
