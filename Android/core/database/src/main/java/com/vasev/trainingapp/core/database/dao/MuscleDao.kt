package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.MuscleEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `muscles` / DAO для `muscles`
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface MuscleDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(muscle: MuscleEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(muscles: List<MuscleEntity>): List<Long>

    @Update
    suspend fun update(muscle: MuscleEntity)

    @Delete
    suspend fun delete(muscle: MuscleEntity)

    @Query("SELECT * FROM muscles WHERE id = :id")
    suspend fun getById(id: Long): MuscleEntity?

    @Query("SELECT * FROM muscles WHERE groupId = :groupId ORDER BY name ASC")
    fun observeByGroup(groupId: Long): Flow<List<MuscleEntity>>

    @Query("SELECT * FROM muscles ORDER BY name ASC")
    fun observeAll(): Flow<List<MuscleEntity>>
}
