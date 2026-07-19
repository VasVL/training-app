package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `exercises` — basic CRUD + queries, including soft-delete / DAO для `exercises` — базовые CRUD + запросы, включая мягкое удаление
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exercise: ExerciseEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(exercises: List<ExerciseEntity>): List<Long>

    @Update
    suspend fun update(exercise: ExerciseEntity)

    @Delete
    suspend fun delete(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercises WHERE id = :id")
    suspend fun getById(id: Long): ExerciseEntity?

    @Query("SELECT * FROM exercises WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: String): ExerciseEntity?

    @Query("SELECT * FROM exercises WHERE isDeleted = 0 ORDER BY name ASC")
    fun observeAll(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE isBuiltin = 1 AND isDeleted = 0 ORDER BY name ASC")
    fun observeBuiltin(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercises WHERE createdByUserId = :userId AND isDeleted = 0 ORDER BY name ASC")
    fun observeCreatedByUser(userId: Long): Flow<List<ExerciseEntity>>

    @Query("UPDATE exercises SET isDeleted = 1 WHERE id = :id AND isBuiltin = 0")
    suspend fun softDelete(id: Long): Int
}
