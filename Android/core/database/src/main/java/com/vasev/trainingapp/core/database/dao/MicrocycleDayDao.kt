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
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface MicrocycleDayDao {

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
