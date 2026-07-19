package com.vasev.trainingapp.feature.anatomy.domain.entity

import com.vasev.trainingapp.feature.anatomy.domain.entity.type.MuscleRelation

/**
 * Domain model of a relation between two muscles (antagonist or synergist) /
 * Domain-модель связи между двумя мышцами (антагонист или синергист)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class MuscleRelationEntry(
    val muscleId: Long,
    val relatedMuscleId: Long,
    val relation: MuscleRelation,
)
