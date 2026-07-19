package com.vasev.trainingapp.feature.workout.domain.entity

import com.vasev.trainingapp.feature.workout.domain.entity.type.SetType

/**
 * Domain model of an exercise entry within a workout log /
 * Domain-модель записи упражнения в дневнике тренировки
 *
 * `isSkipped=true` means the user marked this exercise as skipped (e.g. it was in the program but
 * not done) / `isSkipped=true` означает, что пользователь пометил упражнение как пропущенное
 * (было в программе, но не выполнено)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class WorkoutLogExercise(
    val durationSeconds: Long?,
    val exerciseId: Long,
    val id: Long,
    val isSkipped: Boolean,
    val order: Int,
    val setType: SetType,
    val supersetGroupId: Long?,
    val workoutLogId: Long,
)
