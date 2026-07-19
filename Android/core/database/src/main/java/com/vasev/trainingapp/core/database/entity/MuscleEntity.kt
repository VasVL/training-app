package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * A single muscle belonging to a muscle group / Отдельная мышца, принадлежащая группе мышц
 *
 * @ForeignKey with CASCADE delete: when a group is deleted, its muscles are deleted too /
 * @ForeignKey с каскадным удалением: при удалении группы её мышцы тоже удаляются
 */
@Entity(
    tableName = "muscles",
    foreignKeys = [
        ForeignKey(
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = MuscleGroupEntity::class,
        ),
    ],
    indices = [
        Index(value = ["remoteId"], unique = true),
        Index(value = ["groupId"]),
    ],
)
data class MuscleEntity(
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "groupId") val groupId: Long,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
