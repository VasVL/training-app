package com.vasev.trainingapp.feature.exercises.domain.entity

import com.vasev.trainingapp.feature.exercises.domain.entity.type.ExerciseType

/**
 * Domain model of an exercise (e.g. bench press, squat) / Domain-модель упражнения (например, жим лёжа, присед)
 *
 * `isBuiltin=true` — shipped with the app, cannot be deleted; `isDeleted=true` — soft-deleted,
 * hidden from lists / `isBuiltin=true` — вшито в приложение, нельзя удалить; `isDeleted=true` —
 * мягко удалено, скрыто из списков
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class Exercise(
    val createdByUserId: Long?,
    val description: String?,
    val id: Long,
    val imageUrl: String?,
    val isBuiltin: Boolean,
    val isDeleted: Boolean,
    val name: String,
    val remoteId: String?,
    val type: ExerciseType,
)
