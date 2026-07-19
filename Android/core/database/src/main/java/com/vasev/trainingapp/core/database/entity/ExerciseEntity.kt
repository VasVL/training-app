package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.vasev.trainingapp.core.database.entity.types.ExerciseType

/**
 * Exercise (e.g. bench press, squat) / Упражнение (например, жим лёжа, присед)
 *
 * `isBuiltin=true` means the exercise is shipped with the app and cannot be deleted /
 * `isBuiltin=true` означает, что упражнение вшито в приложение и не может быть удалено
 *
 * `createdByUserId` is non-null for user-created exercises, null for builtin ones /
 * `createdByUserId` не null для пользовательских упражнений, null для вшитых
 *
 * `isDeleted=true` means soft-deleted: hidden from the exercise list, but kept in DB so programs/logs stay intact /
 * `isDeleted=true` означает мягкое удаление: скрыто из списка упражнений, но остаётся в БД, чтобы программы/дневник не разрушались
 *
 * On user deletion the exercise is NOT cascade-deleted; `createdByUserId` is set to NULL (SET_NULL) /
 * При удалении пользователя упражнение НЕ удаляется каскадом; `createdByUserId` обнуляется (SET_NULL)
 */
@Entity(
    tableName = "exercises",
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
data class ExerciseEntity(
    @ColumnInfo(name = "createdByUserId") val createdByUserId: Long?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "isBuiltin") val isBuiltin: Boolean,
    @ColumnInfo(name = "isDeleted") val isDeleted: Boolean,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @ColumnInfo(name = "type") val type: ExerciseType,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
