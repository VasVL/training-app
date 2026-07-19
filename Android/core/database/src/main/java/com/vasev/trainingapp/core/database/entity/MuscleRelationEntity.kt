package com.vasev.trainingapp.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Relation between two muscles for the anatomy atlas (antagonist or synergist) /
 * Связь между двумя мышцами для анатомического атласа (антагонист или синергист)
 *
 * Composite primary key (muscleId + relatedMuscleId + relation) — allows both relation types between the same pair /
 * Составной первичный ключ (muscleId + relatedMuscleId + relation) — допускает оба типа связи между одной парой
 */
@Entity(
    tableName = "muscle_relations",
    foreignKeys = [
        ForeignKey(
            childColumns = ["muscleId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = MuscleEntity::class,
        ),
        ForeignKey(
            childColumns = ["relatedMuscleId"],
            onDelete = ForeignKey.CASCADE,
            parentColumns = ["id"],
            entity = MuscleEntity::class,
        ),
    ],
    indices = [
        Index(value = ["muscleId"]),
        Index(value = ["relatedMuscleId"]),
    ],
    primaryKeys = ["muscleId", "relatedMuscleId", "relation"],
)
data class MuscleRelationEntity(
    @ColumnInfo(name = "muscleId") val muscleId: Long,
    @ColumnInfo(name = "relatedMuscleId") val relatedMuscleId: Long,
    @ColumnInfo(name = "relation") val relation: MuscleRelation,
)
