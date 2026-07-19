package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * An exercise entry within a workout log / Запись упражнения в дневнике тренировки
 *
 * `isSkipped=true` means the user marked this exercise as skipped (e.g. it was in the program but not done) /
 * `isSkipped=true` означает, что пользователь пометил упражнение как пропущенное (было в программе, но не выполнено)
 *
 * FK strategy: workoutLogId uses CASCADE (deleting a workout log deletes its exercises);
 * exerciseId uses RESTRICT (an exercise referenced by a log cannot be hard-deleted; soft-delete instead) /
 * Стратегия FK: workoutLogId использует CASCADE (удаление записи дневника удаляет её упражнения);
 * exerciseId использует RESTRICT (упражнение, на которое ссылается запись, нельзя жёстко удалить; мягкое удаление)
 */
@Entity(
    tableName = "workout_log_exercises",
    foreignKeys = [
        ForeignKey(
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
        ForeignKey(
            childColumns = ["workoutLogId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = WorkoutLogEntity::class,
        ),
    ],
    indices = [
        Index(value = ["exerciseId"]),
        Index(value = ["workoutLogId"]),
    ],
)
data class WorkoutLogExerciseEntity(
    @ColumnInfo(name = "durationSeconds") val durationSeconds: Long?,
    @ColumnInfo(name = "exerciseId") val exerciseId: Long,
    @ColumnInfo(name = "isSkipped") val isSkipped: Boolean,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "setType") val setType: SetType,
    @ColumnInfo(name = "supersetGroupId") val supersetGroupId: Long?,
    @ColumnInfo(name = "workoutLogId") val workoutLogId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
