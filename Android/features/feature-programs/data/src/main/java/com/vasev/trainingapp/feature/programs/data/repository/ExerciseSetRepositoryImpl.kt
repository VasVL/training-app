package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.ExerciseSetDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.ExerciseSet
import com.vasev.trainingapp.feature.programs.domain.repository.ExerciseSetRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ExerciseSetRepository] backed by the Room [ExerciseSetDao]. /
 * Реализация [ExerciseSetRepository] на основе Room [ExerciseSetDao].
 *
 * `@Inject` — Hilt creates this class and injects [exerciseSetDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [exerciseSetDao] и [mapper]
 */
class ExerciseSetRepositoryImpl @Inject constructor(
    private val exerciseSetDao: ExerciseSetDao,
    private val mapper: ProgramsMapper,
) : ExerciseSetRepository {

    override fun observeByWorkoutTemplate(workoutTemplateId: Long): Flow<List<ExerciseSet>> {
        return exerciseSetDao.observeByWorkoutTemplate(workoutTemplateId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): ExerciseSet? {
        return exerciseSetDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByWorkoutTemplate(workoutTemplateId: Long): List<ExerciseSet> {
        return exerciseSetDao.getByWorkoutTemplate(workoutTemplateId).map { mapper.map(it) }
    }

    override suspend fun insert(set: ExerciseSet): Long {
        return exerciseSetDao.insert(mapper.map(set))
    }

    override suspend fun update(set: ExerciseSet) {
        return exerciseSetDao.update(mapper.map(set))
    }

    override suspend fun delete(set: ExerciseSet) {
        return exerciseSetDao.delete(mapper.map(set))
    }
}
