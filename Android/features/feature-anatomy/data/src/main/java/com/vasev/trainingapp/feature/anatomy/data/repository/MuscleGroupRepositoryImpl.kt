package com.vasev.trainingapp.feature.anatomy.data.repository

import com.vasev.trainingapp.core.database.dao.MuscleGroupDao
import com.vasev.trainingapp.feature.anatomy.data.mapper.AnatomyMapper
import com.vasev.trainingapp.feature.anatomy.domain.entity.MuscleGroup
import com.vasev.trainingapp.feature.anatomy.domain.repository.MuscleGroupRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [MuscleGroupRepository] backed by the Room [MuscleGroupDao]. /
 * Реализация [MuscleGroupRepository] на основе Room [MuscleGroupDao].
 *
 * `@Inject` — Hilt creates this class and injects [muscleGroupDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [muscleGroupDao] и [mapper]
 */
class MuscleGroupRepositoryImpl @Inject constructor(
    private val mapper: AnatomyMapper,
    private val muscleGroupDao: MuscleGroupDao,
) : MuscleGroupRepository {

    override fun observeAll(): Flow<List<MuscleGroup>> {
        return muscleGroupDao.observeAll().map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): MuscleGroup? {
        return muscleGroupDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun insert(group: MuscleGroup): Long {
        return muscleGroupDao.insert(mapper.map(group))
    }

    override suspend fun update(group: MuscleGroup) {
        return muscleGroupDao.update(mapper.map(group))
    }

    override suspend fun delete(group: MuscleGroup) {
        return muscleGroupDao.delete(mapper.map(group))
    }
}
