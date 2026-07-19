package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.WeightMeasurementEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `weight_measurements` — body weight measurements of users / DAO для `weight_measurements` — измерения веса пользователей
 *
 * `public` — visible to the `app` module so that the Hilt `DatabaseModule` can provide it /
 * `public` — виден модулю `app`, чтобы Hilt-модуль `DatabaseModule` мог его предоставлять
 */
@Dao
interface WeightMeasurementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(measurement: WeightMeasurementEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(measurements: List<WeightMeasurementEntity>): List<Long>

    @Update
    suspend fun update(measurement: WeightMeasurementEntity)

    @Delete
    suspend fun delete(measurement: WeightMeasurementEntity)

    @Query("SELECT * FROM weight_measurements WHERE id = :id")
    suspend fun getById(id: Long): WeightMeasurementEntity?

    @Query("SELECT * FROM weight_measurements WHERE userId = :userId ORDER BY measuredAt DESC")
    suspend fun getByUser(userId: Long): List<WeightMeasurementEntity>

    @Query("SELECT * FROM weight_measurements WHERE userId = :userId ORDER BY measuredAt DESC")
    fun observeByUser(userId: Long): Flow<List<WeightMeasurementEntity>>

    @Query("SELECT * FROM weight_measurements WHERE userId = :userId AND measuredAt BETWEEN :from AND :to ORDER BY measuredAt ASC")
    fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<WeightMeasurementEntity>>
}
