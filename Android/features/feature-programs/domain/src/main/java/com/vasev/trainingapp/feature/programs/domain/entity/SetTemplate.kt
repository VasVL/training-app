package com.vasev.trainingapp.feature.programs.domain.entity

import com.vasev.trainingapp.feature.programs.domain.entity.type.RepType
import com.vasev.trainingapp.feature.programs.domain.entity.type.WeightType

/**
 * Domain model of a single set template within an exercise entry /
 * Domain-модель шаблона одного подхода внутри упражнения
 *
 * `weightRefExerciseId` is the exercise whose one-rep-max is used for percent-based weight; for
 * same exercise it points to itself / `weightRefExerciseId` — упражнение, от разового максимума
 * которого считается вес в процентах; для того же упражнения ссылается на себя
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class SetTemplate(
    val durationValue: Long?,
    val exerciseSetId: Long,
    val id: Long,
    val order: Int,
    val repType: RepType,
    val repValue: Double?,
    val rpeValue: Double?,
    val weightRefExerciseId: Long,
    val weightType: WeightType,
    val weightValue: Double,
)
