package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Muscle group (e.g. back, chest, legs) / Группа мышц (например, спина, грудь, ноги)
 */
@Entity(
    tableName = "muscle_groups",
    indices = [
        Index(value = ["remoteId"], unique = true),
    ],
)
data class MuscleGroupEntity(
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
