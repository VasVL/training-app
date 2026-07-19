package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A food item from the nutrition database (second phase) / Продукт из базы питания (вторая фаза)
 *
 * Stores nutritional values per 100g (or per serving — to be decided in the nutrition feature) /
 * Хранит nutritional values на 100г (или на порцию — решим в фиче питания)
 */
@Entity(
    tableName = "food_items",
    indices = [
        Index(value = ["remoteId"], unique = true),
    ],
)
data class FoodItemEntity(
    @ColumnInfo(name = "calories") val calories: Double,
    @ColumnInfo(name = "carbs") val carbs: Double,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "fat") val fat: Double,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "protein") val protein: Double,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
