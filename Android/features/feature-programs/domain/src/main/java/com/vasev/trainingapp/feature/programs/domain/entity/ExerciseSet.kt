package com.vasev.trainingapp.feature.programs.domain.entity

import com.vasev.trainingapp.feature.programs.domain.entity.type.RepType
import com.vasev.trainingapp.feature.programs.domain.entity.type.SetType
import com.vasev.trainingapp.feature.programs.domain.entity.type.WeightType

/**
 * Domain model of an exercise entry within a workout template (with set type and weight/rep
 * configuration) / Domain-модель упражнения в шаблоне тренировки (с типом подхода и настройкой
 * веса/повторений)
 *
 * `supersetGroupId` groups exercises into a superset; null for single exercises /
 * `supersetGroupId` группирует упражнения в суперсет; null для одиночных упражнений
 *
 * `dropsetReductions` is the number of weight reductions in a dropset; null otherwise /
 * `dropsetReductions` — число сбросов веса в дропсете; иначе null
 *
 * `weightRefExerciseId` is the exercise whose one-rep-max is used for percent-based weight; for
 * same exercise it points to itself / `weightRefExerciseId` — упражнение, от разового максимума
 * которого считается вес в процентах; для того же упражнения ссылается на себя
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class ExerciseSet(
    val dropsetReductions: Int?,
    val durationValue: Long?,
    val exerciseId: Long,
    val id: Long,
    val order: Int,
    val repType: RepType,
    val repValue: Double?,
    val restTimeSeconds: Int?,
    val rpeValue: Double?,
    val setType: SetType,
    val supersetGroupId: Long?,
    val weightRefExerciseId: Long,
    val weightType: WeightType,
    val weightValue: Double,
    val workoutTemplateId: Long,
)
