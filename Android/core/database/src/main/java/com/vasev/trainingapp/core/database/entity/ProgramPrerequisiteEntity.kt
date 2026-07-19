package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Prerequisite to start a program (e.g. required one-rep-max or required reps) /
 * Условие для начала программы (например, нужный разовый максимум или число повторений)
 *
 * `exerciseId` is nullable: some prerequisites (e.g. bodyweight pull-ups) reference an exercise, others don't /
 * `exerciseId` nullable: часть условий (например, подтягивания с весом тела) ссылается на упражнение, часть — нет
 */
@Entity(
    tableName = "program_prerequisites",
    foreignKeys = [
        ForeignKey(
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = ProgramEntity::class,
        ),
        ForeignKey(
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.RESTRICT,
            parentColumns = ["id"],
            entity = ExerciseEntity::class,
        ),
    ],
    indices = [
        Index(value = ["programId"]),
        Index(value = ["exerciseId"]),
    ],
)
data class ProgramPrerequisiteEntity(
    @ColumnInfo(name = "exerciseId") val exerciseId: Long?,
    @ColumnInfo(name = "programId") val programId: Long,
    @ColumnInfo(name = "requiredValue") val requiredValue: Double,
    @ColumnInfo(name = "type") val type: PrerequisiteType,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
