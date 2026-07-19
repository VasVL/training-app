package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A body weight measurement of a user / Измерение веса тела пользователя
 *
 * FK strategy: userId uses CASCADE (deleting a user deletes their weight measurements) /
 * Стратегия FK: userId использует CASCADE (удаление пользователя удаляет его измерения веса)
 */
@Entity(
    tableName = "weight_measurements",
    foreignKeys = [
        ForeignKey(
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = UserEntity::class,
        ),
    ],
    indices = [
        Index(value = ["userId"]),
    ],
)
data class WeightMeasurementEntity(
    @ColumnInfo(name = "measuredAt") val measuredAt: Long,
    @ColumnInfo(name = "userId") val userId: Long,
    @ColumnInfo(name = "weight") val weight: Double,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
