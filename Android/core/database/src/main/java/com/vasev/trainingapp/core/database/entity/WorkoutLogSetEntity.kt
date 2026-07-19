package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogSetStatus

/**
 * A single set in a workout log (planned vs actual values, status) /
 * Один подход в записи дневника (плановые vs фактические значения, статус)
 *
 * FK strategy: workoutLogExerciseId uses CASCADE (deleting an exercise entry deletes its sets) /
 * Стратегия FK: workoutLogExerciseId использует CASCADE (удаление упражнения удаляет его подходы)
 */
@Entity(
    tableName = "workout_log_sets",
    foreignKeys = [
        ForeignKey(
            childColumns = ["workoutLogExerciseId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = WorkoutLogExerciseEntity::class,
        ),
    ],
    indices = [
        Index(value = ["workoutLogExerciseId"]),
    ],
)
data class WorkoutLogSetEntity(
    @ColumnInfo(name = "actualReps") val actualReps: Int?,
    @ColumnInfo(name = "actualWeight") val actualWeight: Double?,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "plannedReps") val plannedReps: Int?,
    @ColumnInfo(name = "plannedWeight") val plannedWeight: Double?,
    @ColumnInfo(name = "restTimeSeconds") val restTimeSeconds: Int?,
    @ColumnInfo(name = "status") val status: WorkoutLogSetStatus,
    @ColumnInfo(name = "workoutLogExerciseId") val workoutLogExerciseId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
