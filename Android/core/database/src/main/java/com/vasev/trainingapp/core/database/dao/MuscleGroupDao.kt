package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.MuscleGroupEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `muscle_groups` / DAO для `muscle_groups`
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface MuscleGroupDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(group: MuscleGroupEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(groups: List<MuscleGroupEntity>): List<Long>

    @Update
    suspend fun update(group: MuscleGroupEntity)

    @Delete
    suspend fun delete(group: MuscleGroupEntity)

    @Query("SELECT * FROM muscle_groups WHERE id = :id")
    suspend fun getById(id: Long): MuscleGroupEntity?

    @Query("SELECT * FROM muscle_groups ORDER BY name ASC")
    fun observeAll(): Flow<List<MuscleGroupEntity>>
}
