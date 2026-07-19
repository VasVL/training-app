package com.vasev.trainingapp.core.referencedata.model

import com.vasev.trainingapp.core.referencedata.model.types.ExerciseType

/**
 * Domain model of an exercise (e.g. bench press, squat) / Domain-модель упражнения (например, жим лёжа, присед)
 *
 * `isBuiltin=true` — shipped with the app, cannot be deleted; `isDeleted=true` — soft-deleted, hidden from lists /
 * `isBuiltin=true` — вшито в приложение, нельзя удалить; `isDeleted=true` — мягко удалено, скрыто из списков
 *
 * No Room annotations — this is a pure domain model used by feature modules /
 * Без Room-аннотаций — чистая domain-модель, используется feature-модулями
 */
data class Exercise(
    val createdByUserId: Long?,
    val description: String?,
    val id: Long,
    val imageUrl: String?,
    val isBuiltin: Boolean,
    val isDeleted: Boolean,
    val name: String,
    val type: ExerciseType,
)
