package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.MicrocycleDayEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `microcycle_days` / DAO для `microcycle_days`
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface MicrocycleDayDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(day: MicrocycleDayEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(days: List<MicrocycleDayEntity>): List<Long>

    @Update
    suspend fun update(day: MicrocycleDayEntity)

    @Delete
    suspend fun delete(day: MicrocycleDayEntity)

    @Query("SELECT * FROM microcycle_days WHERE id = :id")
    suspend fun getById(id: Long): MicrocycleDayEntity?

    @Query("SELECT * FROM microcycle_days WHERE microcycleId = :microcycleId ORDER BY `order` ASC")
    suspend fun getByMicrocycle(microcycleId: Long): List<MicrocycleDayEntity>

    @Query("SELECT * FROM microcycle_days WHERE microcycleId = :microcycleId ORDER BY `order` ASC")
    fun observeByMicrocycle(microcycleId: Long): Flow<List<MicrocycleDayEntity>>
}
