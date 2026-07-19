package com.vasev.trainingapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vasev.trainingapp.core.database.entity.WorkoutLogEntity
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogStatus
import kotlinx.coroutines.flow.Flow

/**
 * DAO for `workout_logs` — workout log entries / DAO для `workout_logs` — записи дневника тренировок
 *
 * `internal` — visible only inside the `core/database` module / `internal` — виден только внутри модуля `core/database`
 */
@Dao
internal interface WorkoutLogDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(log: WorkoutLogEntity): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(logs: List<WorkoutLogEntity>): List<Long>

    @Update
    suspend fun update(log: WorkoutLogEntity)

    @Delete
    suspend fun delete(log: WorkoutLogEntity)

    @Query("SELECT * FROM workout_logs WHERE id = :id")
    suspend fun getById(id: Long): WorkoutLogEntity?

    @Query("SELECT * FROM workout_logs WHERE userId = :userId ORDER BY scheduledDate DESC")
    suspend fun getByUser(userId: Long): List<WorkoutLogEntity>

    @Query("SELECT * FROM workout_logs WHERE userId = :userId AND status = :status")
    suspend fun getByUserAndStatus(status: WorkoutLogStatus, userId: Long): List<WorkoutLogEntity>

    @Query("SELECT * FROM workout_logs WHERE userId = :userId AND programId = :programId ORDER BY scheduledDate DESC")
    suspend fun getByUserAndProgram(programId: Long, userId: Long): List<WorkoutLogEntity>

    @Query("SELECT * FROM workout_logs WHERE userId = :userId ORDER BY scheduledDate DESC")
    fun observeByUser(userId: Long): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_logs WHERE userId = :userId AND status = :status")
    fun observeByUserAndStatus(status: WorkoutLogStatus, userId: Long): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_logs WHERE userId = :userId AND scheduledDate BETWEEN :from AND :to ORDER BY scheduledDate ASC")
    fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<WorkoutLogEntity>>
}
