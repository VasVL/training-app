package com.vasev.trainingapp.feature.workout.data.repository

import com.vasev.trainingapp.core.database.dao.WorkoutLogDao
import com.vasev.trainingapp.feature.workout.data.mapper.WorkoutMapper
import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLog
import com.vasev.trainingapp.feature.workout.domain.entity.type.WorkoutLogStatus
import com.vasev.trainingapp.feature.workout.domain.repository.WorkoutLogRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [WorkoutLogRepository] backed by the Room [WorkoutLogDao]. /
 * Реализация [WorkoutLogRepository] на основе Room [WorkoutLogDao].
 *
 * `@Inject` — Hilt creates this class and injects [workoutLogDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [workoutLogDao] и [mapper]
 */
class WorkoutLogRepositoryImpl @Inject constructor(
    private val mapper: WorkoutMapper,
    private val workoutLogDao: WorkoutLogDao,
) : WorkoutLogRepository {

    override fun observeByUser(userId: Long): Flow<List<WorkoutLog>> {
        return workoutLogDao.observeByUser(userId).map { list -> list.map { mapper.map(it) } }
    }

    override fun observeByUserAndStatus(status: WorkoutLogStatus, userId: Long): Flow<List<WorkoutLog>> {
        return workoutLogDao.observeByUserAndStatus(mapper.mapWorkoutLogStatus(status), userId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<WorkoutLog>> {
        return workoutLogDao.observeByUserAndDateRange(from, to, userId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): WorkoutLog? {
        return workoutLogDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByUser(userId: Long): List<WorkoutLog> {
        return workoutLogDao.getByUser(userId).map { mapper.map(it) }
    }

    override suspend fun getByUserAndStatus(status: WorkoutLogStatus, userId: Long): List<WorkoutLog> {
        return workoutLogDao.getByUserAndStatus(mapper.mapWorkoutLogStatus(status), userId).map {
            mapper.map(it)
        }
    }

    override suspend fun getByUserAndProgram(programId: Long, userId: Long): List<WorkoutLog> {
        return workoutLogDao.getByUserAndProgram(programId, userId).map { mapper.map(it) }
    }

    override suspend fun insert(log: WorkoutLog): Long {
        return workoutLogDao.insert(mapper.map(log))
    }

    override suspend fun update(log: WorkoutLog) {
        return workoutLogDao.update(mapper.map(log))
    }

    override suspend fun delete(log: WorkoutLog) {
        return workoutLogDao.delete(mapper.map(log))
    }
}
