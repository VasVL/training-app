package com.vasev.trainingapp.feature.weight.domain.entity

/**
 * Domain model of a body weight measurement of a user /
 * Domain-модель измерения веса тела пользователя
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class WeightMeasurement(
    val id: Long,
    val measuredAt: Long,
    val userId: Long,
    val weight: Double,
)
