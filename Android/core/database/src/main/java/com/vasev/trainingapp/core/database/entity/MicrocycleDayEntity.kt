package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vasev.trainingapp.core.database.entity.types.MicrocycleDayType

/**
 * A day within a microcycle: either a workout day or a rest day / День внутри микроцикла: тренировочный или день отдыха
 */
@Entity(
    tableName = "microcycle_days",
    foreignKeys = [
        ForeignKey(
            childColumns = ["microcycleId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = MicrocycleEntity::class,
        ),
    ],
    indices = [
        Index(value = ["microcycleId"]),
    ],
)
data class MicrocycleDayEntity(
    @ColumnInfo(name = "microcycleId") val microcycleId: Long,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "type") val type: MicrocycleDayType,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
