package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Microcycle — a block of training/rest days inside a program (or standalone) /
 * Микроцикл — блок тренировочных дней и дней отдыха внутри программы (или отдельный)
 *
 * `programId` is nullable for standalone microcycles / `programId` nullable для отдельных микроциклов
 */
@Entity(
    tableName = "microcycles",
    foreignKeys = [
        ForeignKey(
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = ProgramEntity::class,
        ),
    ],
    indices = [
        Index(value = ["remoteId"], unique = true),
        Index(value = ["programId"]),
    ],
)
data class MicrocycleEntity(
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "programId") val programId: Long?,
    @ColumnInfo(name = "remoteId") val remoteId: String?,
    @ColumnInfo(name = "title") val title: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
