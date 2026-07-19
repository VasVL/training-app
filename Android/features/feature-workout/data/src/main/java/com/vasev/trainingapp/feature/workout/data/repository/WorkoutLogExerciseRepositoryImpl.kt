package com.vasev.trainingapp.feature.workout.data.repository

import com.vasev.trainingapp.core.database.dao.WorkoutLogExerciseDao
import com.vasev.trainingapp.feature.workout.data.mapper.WorkoutMapper
import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLogExercise
import com.vasev.trainingapp.feature.workout.domain.repository.WorkoutLogExerciseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [WorkoutLogExerciseRepository] backed by the Room [WorkoutLogExerciseDao]. /
 * Реализация [WorkoutLogExerciseRepository] на основе Room [WorkoutLogExerciseDao].
 *
 * `@Inject` — Hilt creates this class and injects [workoutLogExerciseDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [workoutLogExerciseDao] и [mapper]
 */
class WorkoutLogExerciseRepositoryImpl @Inject constructor(
    private val mapper: WorkoutMapper,
    private val workoutLogExerciseDao: WorkoutLogExerciseDao,
) : WorkoutLogExerciseRepository {

    override fun observeByWorkoutLog(workoutLogId: Long): Flow<List<WorkoutLogExercise>> {
        return workoutLogExerciseDao.observeByWorkoutLog(workoutLogId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): WorkoutLogExercise? {
        return workoutLogExerciseDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByWorkoutLog(workoutLogId: Long): List<WorkoutLogExercise> {
        return workoutLogExerciseDao.getByWorkoutLog(workoutLogId).map { mapper.map(it) }
    }

    override suspend fun insert(exercise: WorkoutLogExercise): Long {
        return workoutLogExerciseDao.insert(mapper.map(exercise))
    }

    override suspend fun update(exercise: WorkoutLogExercise) {
        return workoutLogExerciseDao.update(mapper.map(exercise))
    }

    override suspend fun delete(exercise: WorkoutLogExercise) {
        return workoutLogExerciseDao.delete(mapper.map(exercise))
    }
}
