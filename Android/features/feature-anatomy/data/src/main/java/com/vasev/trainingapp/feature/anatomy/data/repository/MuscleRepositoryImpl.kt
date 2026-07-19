package com.vasev.trainingapp.feature.anatomy.data.repository

import com.vasev.trainingapp.core.database.dao.MuscleDao
import com.vasev.trainingapp.feature.anatomy.data.mapper.AnatomyMapper
import com.vasev.trainingapp.feature.anatomy.domain.entity.Muscle
import com.vasev.trainingapp.feature.anatomy.domain.repository.MuscleRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [MuscleRepository] backed by the Room [MuscleDao]. /
 * Реализация [MuscleRepository] на основе Room [MuscleDao].
 *
 * `@Inject` — Hilt creates this class and injects [muscleDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [muscleDao] и [mapper]
 */
class MuscleRepositoryImpl @Inject constructor(
    private val mapper: AnatomyMapper,
    private val muscleDao: MuscleDao,
) : MuscleRepository {

    override fun observeAll(): Flow<List<Muscle>> {
        return muscleDao.observeAll().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeByGroup(groupId: Long): Flow<List<Muscle>> {
        return muscleDao.observeByGroup(groupId).map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): Muscle? {
        return muscleDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun insert(muscle: Muscle): Long {
        return muscleDao.insert(mapper.map(muscle))
    }

    override suspend fun update(muscle: Muscle) {
        return muscleDao.update(mapper.map(muscle))
    }

    override suspend fun delete(muscle: Muscle) {
        return muscleDao.delete(mapper.map(muscle))
    }
}
