package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * User of the app: owner (trainer) or trainee / Пользователь приложения: владелец (тренер) или подопечный
 *
 * @Entity marks this data class as a Room table / @Entity помечает data class как таблицу Room
 */
@Entity(
    tableName = "users",
    indices = [
        Index(value = ["remoteId"], unique = true),
    ],
)
data class UserEntity(
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "createdAt") val createdAt: Long,
    @ColumnInfo(name = "gender") val gender: Gender,
    @ColumnInfo(name = "height") val height: Double,
    @ColumnInfo(name = "heightUnit") val heightUnit: HeightUnit,
    @ColumnInfo(name = "isDefault") val isDefault: Boolean,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @ColumnInfo(name = "role") val role: UserRole,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "weightUnit") val weightUnit: WeightUnit,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
