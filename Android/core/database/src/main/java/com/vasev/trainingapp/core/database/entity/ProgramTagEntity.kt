package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * User-defined tag of a program (free-form string) / Пользовательский тег программы (произвольная строка)
 *
 * Composite primary key (programId + tag) — a tag appears once per program /
 * Составной первичный ключ (programId + tag) — тег встречается один раз для программы
 */
@Entity(
    tableName = "program_tags",
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
    primaryKeys = ["programId", "tag"],
)
data class ProgramTagEntity(
    @ColumnInfo(name = "programId") val programId: Long,
    @ColumnInfo(name = "tag") val tag: String,
)
