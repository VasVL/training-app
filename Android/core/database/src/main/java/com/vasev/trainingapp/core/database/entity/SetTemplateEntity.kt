package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A single set template within an exercise entry / Шаблон одного подхода внутри упражнения
 *
 * `weightRefExerciseId` is the exercise whose one-rep-max is used for percent-based weight; for same exercise it points to itself /
 * `weightRefExerciseId` — упражнение, от разового максимума которого считается вес в процентах; для того же упражнения ссылается на себя
 *
 * FK strategy: exerciseSetId uses CASCADE (deleting an exercise entry deletes its set templates);
 * weightRefExerciseId uses RESTRICT (an exercise referenced by a set template cannot be hard-deleted) /
 * Стратегия FK: exerciseSetId использует CASCADE (удаление упражнения удаляет его шаблоны подходов);
 * weightRefExerciseId использует RESTRICT (упражнение, на которое ссылается шаблон, нельзя жёстко удалить)
 */
@Entity(
    tableName = "set_templates",
    foreignKeys = [
        ForeignKey(
            childColumns = ["exerciseSetId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = ExerciseSetEntity::class,
        ),
        ForeignKey(
            childColumns = ["weightRefExerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
    ],
    indices = [
        Index(value = ["exerciseSetId"]),
        Index(value = ["weightRefExerciseId"]),
    ],
)
data class SetTemplateEntity(
    @ColumnInfo(name = "durationValue") val durationValue: Long?,
    @ColumnInfo(name = "exerciseSetId") val exerciseSetId: Long,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "repType") val repType: RepType,
    @ColumnInfo(name = "repValue") val repValue: Double?,
    @ColumnInfo(name = "rpeValue") val rpeValue: Double?,
    @ColumnInfo(name = "weightRefExerciseId") val weightRefExerciseId: Long,
    @ColumnInfo(name = "weightType") val weightType: WeightType,
    @ColumnInfo(name = "weightValue") val weightValue: Double,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
