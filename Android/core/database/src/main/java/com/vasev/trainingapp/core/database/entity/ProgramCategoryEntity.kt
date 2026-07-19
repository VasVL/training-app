package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.vasev.trainingapp.core.database.entity.types.ProgramCategory

/**
 * Built-in category tag of a program (enum-based, stable key) / Вшитая категория программы (на enum, стабильный ключ)
 *
 * Composite primary key (programId + category) — a category appears once per program /
 * Составной первичный ключ (programId + category) — категория встречается один раз для программы
 */
@Entity(
    tableName = "program_categories",
    foreignKeys = [
        ForeignKey(
            childColumns = ["programId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = ProgramEntity::class,
        ),
    ],
    indices = [
        Index(value = ["programId"]),
    ],
    primaryKeys = ["programId", "category"],
)
data class ProgramCategoryEntity(
    @ColumnInfo(name = "category") val category: ProgramCategory,
    @ColumnInfo(name = "programId") val programId: Long,
)
