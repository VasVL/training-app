package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.MicrocycleEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `microcycles` / DAO для `microcycles`
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface MicrocycleDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(microcycle: MicrocycleEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(microcycles: List<MicrocycleEntity>): List<Long>

    @Update
    suspend fun update(microcycle: MicrocycleEntity)

    @Delete
    suspend fun delete(microcycle: MicrocycleEntity)

    @Query("SELECT * FROM microcycles WHERE id = :id")
    suspend fun getById(id: Long): MicrocycleEntity?

    @Query("SELECT * FROM microcycles WHERE programId = :programId ORDER BY `order` ASC")
    fun observeByProgram(programId: Long): Flow<List<MicrocycleEntity>>

    @Query("SELECT * FROM microcycles WHERE programId IS NULL ORDER BY title ASC")
    fun observeStandalone(): Flow<List<MicrocycleEntity>>
}
