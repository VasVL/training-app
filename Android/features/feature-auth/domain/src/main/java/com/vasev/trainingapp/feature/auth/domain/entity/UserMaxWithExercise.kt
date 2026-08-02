package com.vasev.trainingapp.feature.auth.domain.entity

import com.vasev.trainingapp.feature.auth.domain.entity.type.MeasurementUnit

/**
 * A personal maximum with the data of its exercise.
 * Личный максимум вместе с данными его упражнения.
 */
data class UserMaxWithExercise(
    val exerciseId: Long,
    val exerciseName: String,
    val id: Long,
    val maxValue: Double,
    val measuredAt: Long,
    val unit: MeasurementUnit,
)
