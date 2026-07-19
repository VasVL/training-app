package com.vasev.trainingapp.core.referencedata.model

/**
 * Domain model of a relation between two muscles (antagonist or synergist) /
 * Domain-модель связи между двумя мышцами (антагонист или синергист)
 *
 * No Room annotations — this is a pure domain model used by feature modules /
 * Без Room-аннотаций — чистая domain-модель, используется feature-модулями
 */
data class MuscleRelationEntry(
    val muscleId: Long,
    val relatedMuscleId: Long,
    val relation: MuscleRelation,
)
