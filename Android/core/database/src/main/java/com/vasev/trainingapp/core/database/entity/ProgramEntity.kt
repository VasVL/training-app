package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Training program (a collection of microcycles) / Программа тренировок (набор микроциклов)
 *
 * Categories are stored as tags in `program_categories`, not as an enum here /
 * Категории хранятся тегами в `program_categories`, а не enum-ом здесь
 *
 * On user deletion the program is NOT cascade-deleted; `createdByUserId` is set to NULL (SET_NULL) /
 * При удалении пользователя программа НЕ удаляется каскадом; `createdByUserId` обнуляется (SET_NULL)
 */
@Entity(
    tableName = "programs",
    foreignKeys = [
        ForeignKey(
            childColumns = ["createdByUserId"],
            onDelete = ForeignKey.SET_NULL,
            parentColumns = ["id"],
            entity = UserEntity::class,
        ),
    ],
    indices = [
        Index(value = ["remoteId"], unique = true),
        Index(value = ["createdByUserId"]),
    ],
)
data class ProgramEntity(
    @ColumnInfo(name = "canSkipWorkouts") val canSkipWorkouts: Boolean,
    @ColumnInfo(name = "createdAt") val createdAt: Long,
    @ColumnInfo(name = "createdByUserId") val createdByUserId: Long?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "isBuiltin") val isBuiltin: Boolean,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
    @ColumnInfo(name = "recommendedAdjustmentPercent") val recommendedAdjustmentPercent: Double,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
