package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Link between an exercise and a muscle with involvement level / Связь упражнения и мышцы с уровнем участия
 *
 * Composite primary key (exerciseId + muscleId) — one muscle appears once per exercise /
 * Составной первичный ключ (exerciseId + muscleId) — одна мышца встречается один раз для упражнения
 */
@Entity(
    tableName = "exercise_muscles",
    foreignKeys = [
        ForeignKey(
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
        ForeignKey(
            childColumns = ["muscleId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = MuscleEntity::class,
        ),
    ],
    indices = [
        Index(value = ["exerciseId"]),
        Index(value = ["muscleId"]),
    ],
    primaryKeys = ["exerciseId", "muscleId"],
)
data class ExerciseMuscleEntity(
    @ColumnInfo(name = "exerciseId") val exerciseId: Long,
    @ColumnInfo(name = "involvement") val involvement: MuscleInvolvement,
    @ColumnInfo(name = "muscleId") val muscleId: Long,
)
