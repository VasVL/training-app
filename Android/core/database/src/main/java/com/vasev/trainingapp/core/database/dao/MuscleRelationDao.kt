package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasev.trainingapp.core.database.entity.MuscleRelationEntity

/**
 * DAO for `muscle_relations` — antagonist/synergist links between muscles / DAO для `muscle_relations` — связи антагонистов/синергистов
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface MuscleRelationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(relation: MuscleRelationEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(relations: List<MuscleRelationEntity>)

    @Delete
    suspend fun delete(relation: MuscleRelationEntity)

    @Query("SELECT * FROM muscle_relations WHERE muscleId = :muscleId")
    suspend fun getByMuscle(muscleId: Long): List<MuscleRelationEntity>
}
