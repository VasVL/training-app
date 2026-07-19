package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * An exercise entry within a workout template (with set type and weight/rep configuration) /
 * Упражнение в шаблоне тренировки (с типом подхода и настройкой веса/повторений)
 *
 * `supersetGroupId` groups exercises into a superset; null for single exercises /
 * `supersetGroupId` группирует упражнения в суперсет; null для одиночных упражнений
 *
 * `dropsetReductions` is the number of weight reductions in a dropset; null otherwise /
 * `dropsetReductions` — число сбросов веса в дропсете; иначе null
 *
 * `weightRefExerciseId` is the exercise whose one-rep-max is used for percent-based weight; for same exercise it points to itself /
 * `weightRefExerciseId` — упражнение, от разового максимума которого считается вес в процентах; для того же упражнения ссылается на себя
 *
 * FK strategy: exerciseId and weightRefExerciseId use RESTRICT (an exercise referenced by a set cannot be hard-deleted;
 * soft-delete via `isDeleted` is used instead). workoutTemplateId uses CASCADE (deleting a workout deletes its sets) /
 * Стратегия FK: exerciseId и weightRefExerciseId используют RESTRICT (упражнение, на которое ссылается подход,
 * нельзя жёстко удалить; вместо этого — мягкое удаление через `isDeleted`). workoutTemplateId использует CASCADE
 * (удаление тренировки удаляет её подходы)
 */
@Entity(
    tableName = "exercise_sets",
    foreignKeys = [
        ForeignKey(
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
        ForeignKey(
            childColumns = ["weightRefExerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
        ForeignKey(
            childColumns = ["workoutTemplateId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = WorkoutTemplateEntity::class,
        ),
    ],
    indices = [
        Index(value = ["exerciseId"]),
        Index(value = ["weightRefExerciseId"]),
        Index(value = ["workoutTemplateId"]),
    ],
)
data class ExerciseSetEntity(
    @ColumnInfo(name = "dropsetReductions") val dropsetReductions: Int?,
    @ColumnInfo(name = "durationValue") val durationValue: Long?,
    @ColumnInfo(name = "exerciseId") val exerciseId: Long,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "repType") val repType: RepType,
    @ColumnInfo(name = "repValue") val repValue: Double?,
    @ColumnInfo(name = "restTimeSeconds") val restTimeSeconds: Int?,
    @ColumnInfo(name = "rpeValue") val rpeValue: Double?,
    @ColumnInfo(name = "setType") val setType: SetType,
    @ColumnInfo(name = "supersetGroupId") val supersetGroupId: Long?,
    @ColumnInfo(name = "weightRefExerciseId") val weightRefExerciseId: Long,
    @ColumnInfo(name = "weightType") val weightType: WeightType,
    @ColumnInfo(name = "weightValue") val weightValue: Double,
    @ColumnInfo(name = "workoutTemplateId") val workoutTemplateId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
