package com.vasev.trainingapp.feature.exercises.data.repository

import com.vasev.trainingapp.core.database.dao.ExerciseDao
import com.vasev.trainingapp.feature.exercises.data.mapper.ExercisesMapper
import com.vasev.trainingapp.feature.exercises.domain.entity.Exercise
import com.vasev.trainingapp.feature.exercises.domain.repository.ExerciseRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ExerciseRepository] backed by the Room [ExerciseDao]. /
 * Реализация [ExerciseRepository] на основе Room [ExerciseDao].
 *
 * `@Inject` — Hilt creates this class and injects [exerciseDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [exerciseDao] и [mapper]
 */
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val mapper: ExercisesMapper,
) : ExerciseRepository {

    override fun observeAll(): Flow<List<Exercise>> {
        return exerciseDao.observeAll().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeBuiltin(): Flow<List<Exercise>> {
        return exerciseDao.observeBuiltin().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeCreatedByUser(userId: Long): Flow<List<Exercise>> {
        return exerciseDao.observeCreatedByUser(userId).map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): Exercise? {
        return exerciseDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByRemoteId(remoteId: String): Exercise? {
        return exerciseDao.getByRemoteId(remoteId)?.let { mapper.map(it) }
    }

    override suspend fun insert(exercise: Exercise): Long {
        return exerciseDao.insert(mapper.map(exercise))
    }

    override suspend fun update(exercise: Exercise) {
        return exerciseDao.update(mapper.map(exercise))
    }

    override suspend fun softDelete(id: Long): Int {
        return exerciseDao.softDelete(id)
    }
}
