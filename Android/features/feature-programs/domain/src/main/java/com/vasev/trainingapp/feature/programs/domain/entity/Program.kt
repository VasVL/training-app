package com.vasev.trainingapp.feature.programs.domain.entity

/**
 * Domain model of a training program (a collection of microcycles) /
 * Domain-модель программы тренировок (набор микроциклов)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class Program(
    val canSkipWorkouts: Boolean,
    val createdAt: Long,
    val createdByUserId: Long?,
    val description: String?,
    val id: Long,
    val isBuiltin: Boolean,
    val isFavorite: Boolean,
    val recommendedAdjustmentPercent: Double,
    val remoteId: String?,
    val title: String,
)
