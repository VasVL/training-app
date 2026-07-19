package com.vasev.trainingapp.feature.anatomy.data.repository

import com.vasev.trainingapp.core.database.dao.ExerciseMuscleDao
import com.vasev.trainingapp.feature.anatomy.data.mapper.AnatomyMapper
import com.vasev.trainingapp.feature.anatomy.domain.entity.ExerciseMuscle
import com.vasev.trainingapp.feature.anatomy.domain.repository.ExerciseMuscleRepository
import javax.inject.Inject

/**
 * Implementation of [ExerciseMuscleRepository] backed by the Room [ExerciseMuscleDao]. /
 * Реализация [ExerciseMuscleRepository] на основе Room [ExerciseMuscleDao].
 *
 * `@Inject` — Hilt creates this class and injects [exerciseMuscleDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [exerciseMuscleDao] и [mapper]
 */
class ExerciseMuscleRepositoryImpl @Inject constructor(
    private val exerciseMuscleDao: ExerciseMuscleDao,
    private val mapper: AnatomyMapper,
) : ExerciseMuscleRepository {

    override suspend fun getByExercise(exerciseId: Long): List<ExerciseMuscle> {
        return exerciseMuscleDao.getByExercise(exerciseId).map { mapper.map(it) }
    }

    override suspend fun getByMuscle(muscleId: Long): List<ExerciseMuscle> {
        return exerciseMuscleDao.getByMuscle(muscleId).map { mapper.map(it) }
    }

    override suspend fun insert(link: ExerciseMuscle) {
        return exerciseMuscleDao.insert(mapper.map(link))
    }

    override suspend fun delete(link: ExerciseMuscle) {
        return exerciseMuscleDao.delete(mapper.map(link))
    }
}
