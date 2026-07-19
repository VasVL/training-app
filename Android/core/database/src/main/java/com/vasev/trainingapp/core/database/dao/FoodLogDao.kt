package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.FoodLogEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `food_logs` — food log entries of users / DAO для `food_logs` — записи дневника питания пользователей
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface FoodLogDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(log: FoodLogEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(logs: List<FoodLogEntity>): List<Long>

    @Update
    suspend fun update(log: FoodLogEntity)

    @Delete
    suspend fun delete(log: FoodLogEntity)

    @Query("SELECT * FROM food_logs WHERE id = :id")
    suspend fun getById(id: Long): FoodLogEntity?

    @Query("SELECT * FROM food_logs WHERE userId = :userId ORDER BY loggedAt DESC")
    suspend fun getByUser(userId: Long): List<FoodLogEntity>

    @Query("SELECT * FROM food_logs WHERE userId = :userId AND loggedAt BETWEEN :from AND :to ORDER BY loggedAt ASC")
    suspend fun getByUserAndDateRange(from: Long, to: Long, userId: Long): List<FoodLogEntity>

    @Query("SELECT * FROM food_logs WHERE userId = :userId ORDER BY loggedAt DESC")
    fun observeByUser(userId: Long): Flow<List<FoodLogEntity>>

    @Query("SELECT * FROM food_logs WHERE userId = :userId AND loggedAt BETWEEN :from AND :to ORDER BY loggedAt ASC")
    fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<FoodLogEntity>>
}
