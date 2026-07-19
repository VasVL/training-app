package com.vasev.trainingapp.feature.auth.domain.entity

import com.vasev.trainingapp.feature.auth.domain.entity.type.MeasurementUnit

/**
 * Domain model of a one-rep-max (or other metric) of a user for a specific exercise /
 * Domain-модель разового максимума (или иной метрики) пользователя для конкретного упражнения
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class UserMax(
    val exerciseId: Long,
    val id: Long,
    val maxValue: Double,
    val measuredAt: Long,
    val unit: MeasurementUnit,
    val userId: Long,
)
