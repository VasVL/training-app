package com.vasev.trainingapp.feature.workout.domain.repository

import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLog
import com.vasev.trainingapp.feature.workout.domain.entity.type.WorkoutLogStatus
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing workout log entries — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к записям дневника тренировок — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface WorkoutLogRepository {

    fun observeByUser(userId: Long): Flow<List<WorkoutLog>>

    fun observeByUserAndStatus(status: WorkoutLogStatus, userId: Long): Flow<List<WorkoutLog>>

    fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<WorkoutLog>>

    suspend fun getById(id: Long): WorkoutLog?

    suspend fun getByUser(userId: Long): List<WorkoutLog>

    suspend fun getByUserAndStatus(status: WorkoutLogStatus, userId: Long): List<WorkoutLog>

    suspend fun getByUserAndProgram(programId: Long, userId: Long): List<WorkoutLog>

    suspend fun insert(log: WorkoutLog): Long

    suspend fun update(log: WorkoutLog)

    suspend fun delete(log: WorkoutLog)
}
