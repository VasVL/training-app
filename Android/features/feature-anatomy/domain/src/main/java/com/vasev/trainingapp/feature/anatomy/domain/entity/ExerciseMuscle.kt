package com.vasev.trainingapp.feature.anatomy.domain.entity

import com.vasev.trainingapp.feature.anatomy.domain.entity.type.MuscleInvolvement

/**
 * Domain model of a link between an exercise and a muscle with involvement level /
 * Domain-модель связи упражнения и мышцы с уровнем участия
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class ExerciseMuscle(
    val exerciseId: Long,
    val involvement: MuscleInvolvement,
    val muscleId: Long,
)
