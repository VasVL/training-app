package com.vasev.trainingapp.core.referencedata.model

/**
 * Domain model of a link between an exercise and a muscle with involvement level /
 * Domain-модель связи упражнения и мышцы с уровнем участия
 *
 * No Room annotations — this is a pure domain model used by feature modules /
 * Без Room-аннотаций — чистая domain-модель, используется feature-модулями
 */
data class ExerciseMuscle(
    val exerciseId: Long,
    val involvement: MuscleInvolvement,
    val muscleId: Long,
)
