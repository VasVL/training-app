package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Workout template inside a day (a day may contain more than one workout) /
 * Шаблон тренировки внутри дня (в дне может быть больше одной тренировки)
 */
@Entity(
    tableName = "workout_templates",
    foreignKeys = [
        ForeignKey(
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = MicrocycleDayEntity::class,
        ),
    ],
    indices = [
        Index(value = ["dayId"]),
    ],
)
data class WorkoutTemplateEntity(
    @ColumnInfo(name = "dayId") val dayId: Long,
    @ColumnInfo(name = "order") val order: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
