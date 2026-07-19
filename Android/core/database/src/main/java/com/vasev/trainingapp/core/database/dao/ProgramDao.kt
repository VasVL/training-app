package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.ProgramEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `programs` / DAO для `programs`
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface ProgramDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(program: ProgramEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(programs: List<ProgramEntity>): List<Long>

    @Update
    suspend fun update(program: ProgramEntity)

    @Delete
    suspend fun delete(program: ProgramEntity)

    @Query("SELECT * FROM programs WHERE id = :id")
    suspend fun getById(id: Long): ProgramEntity?

    @Query("SELECT * FROM programs WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: String): ProgramEntity?

    @Query("SELECT * FROM programs ORDER BY title ASC")
    fun observeAll(): Flow<List<ProgramEntity>>

    @Query("SELECT * FROM programs WHERE isBuiltin = 1 ORDER BY title ASC")
    fun observeBuiltin(): Flow<List<ProgramEntity>>

    @Query("SELECT * FROM programs WHERE isFavorite = 1 ORDER BY title ASC")
    fun observeFavorites(): Flow<List<ProgramEntity>>

    @Query("SELECT * FROM programs WHERE createdByUserId = :userId ORDER BY title ASC")
    fun observeCreatedByUser(userId: Long): Flow<List<ProgramEntity>>

    @Query("UPDATE programs SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Long, isFavorite: Boolean)
}
