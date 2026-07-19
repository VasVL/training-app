package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * One-rep-max (or other metric) of a user for a specific exercise / Разовый максимум (или иная метрика) пользователя для конкретного упражнения
 *
 * FK strategy: userId uses CASCADE (deleting a user deletes their maxes);
 * exerciseId uses RESTRICT (an exercise referenced by a max cannot be hard-deleted; soft-delete instead) /
 * Стратегия FK: userId использует CASCADE (удаление пользователя удаляет его максимумы);
 * exerciseId использует RESTRICT (упражнение, на которое ссылается максимум, нельзя жёстко удалить; мягкое удаление)
 */
@Entity(
    tableName = "user_maxes",
    foreignKeys = [
        ForeignKey(
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = UserEntity::class,
        ),
        ForeignKey(
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
    ],
    indices = [
        Index(value = ["userId"]),
        Index(value = ["exerciseId"]),
    ],
)
data class UserMaxEntity(
    @ColumnInfo(name = "maxValue") val maxValue: Double,
    @ColumnInfo(name = "measuredAt") val measuredAt: Long,
    @ColumnInfo(name = "unit") val unit: MeasurementUnit,
    @ColumnInfo(name = "exerciseId") val exerciseId: Long,
    @ColumnInfo(name = "userId") val userId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
