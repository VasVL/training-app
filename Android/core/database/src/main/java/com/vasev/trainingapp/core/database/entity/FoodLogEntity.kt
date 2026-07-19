package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A food log entry: a user ate some amount of a food item at a given time /
 * Запись в дневнике питания: пользователь съел некоторое количество продукта в указанное время
 *
 * FK strategy: userId uses CASCADE (deleting a user deletes their food logs);
 * foodId uses RESTRICT (a food item referenced by a log cannot be hard-deleted) /
 * Стратегия FK: userId использует CASCADE (удаление пользователя удаляет его дневник питания);
 * foodId использует RESTRICT (продукт, на который ссылается запись, нельзя жёстко удалить)
 */
@Entity(
    tableName = "food_logs",
    foreignKeys = [
        ForeignKey(
            childColumns = ["foodId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = FoodItemEntity::class,
        ),
        ForeignKey(
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = UserEntity::class,
        ),
    ],
    indices = [
        Index(value = ["foodId"]),
        Index(value = ["userId"]),
    ],
)
data class FoodLogEntity(
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "foodId") val foodId: Long,
    @ColumnInfo(name = "loggedAt") val loggedAt: Long,
    @ColumnInfo(name = "userId") val userId: Long,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
