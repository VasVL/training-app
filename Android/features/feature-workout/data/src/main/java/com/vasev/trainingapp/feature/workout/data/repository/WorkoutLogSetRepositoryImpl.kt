package com.vasev.trainingapp.feature.workout.data.repository

import com.vasev.trainingapp.core.database.dao.WorkoutLogSetDao
import com.vasev.trainingapp.feature.workout.data.mapper.WorkoutMapper
import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLogSet
import com.vasev.trainingapp.feature.workout.domain.repository.WorkoutLogSetRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [WorkoutLogSetRepository] backed by the Room [WorkoutLogSetDao]. /
 * Реализация [WorkoutLogSetRepository] на основе Room [WorkoutLogSetDao].
 *
 * `@Inject` — Hilt creates this class and injects [workoutLogSetDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [workoutLogSetDao] и [mapper]
 */
class WorkoutLogSetRepositoryImpl @Inject constructor(
    private val mapper: WorkoutMapper,
    private val workoutLogSetDao: WorkoutLogSetDao,
) : WorkoutLogSetRepository {

    override fun observeByWorkoutLogExercise(workoutLogExerciseId: Long): Flow<List<WorkoutLogSet>> {
        return workoutLogSetDao.observeByWorkoutLogExercise(workoutLogExerciseId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): WorkoutLogSet? {
        return workoutLogSetDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByWorkoutLogExercise(workoutLogExerciseId: Long): List<WorkoutLogSet> {
        return workoutLogSetDao.getByWorkoutLogExercise(workoutLogExerciseId).map { mapper.map(it) }
    }

    override suspend fun insert(set: WorkoutLogSet): Long {
        return workoutLogSetDao.insert(mapper.map(set))
    }

    override suspend fun update(set: WorkoutLogSet) {
        return workoutLogSetDao.update(mapper.map(set))
    }

    override suspend fun delete(set: WorkoutLogSet) {
        return workoutLogSetDao.delete(mapper.map(set))
    }
}
