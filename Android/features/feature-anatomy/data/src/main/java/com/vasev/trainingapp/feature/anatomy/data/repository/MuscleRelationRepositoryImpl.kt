package com.vasev.trainingapp.feature.anatomy.data.repository

import com.vasev.trainingapp.core.database.dao.MuscleRelationDao
import com.vasev.trainingapp.feature.anatomy.data.mapper.AnatomyMapper
import com.vasev.trainingapp.feature.anatomy.domain.entity.MuscleRelationEntry
import com.vasev.trainingapp.feature.anatomy.domain.repository.MuscleRelationRepository
import javax.inject.Inject

/**
 * Implementation of [MuscleRelationRepository] backed by the Room [MuscleRelationDao]. /
 * Реализация [MuscleRelationRepository] на основе Room [MuscleRelationDao].
 *
 * `@Inject` — Hilt creates this class and injects [muscleRelationDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [muscleRelationDao] и [mapper]
 */
class MuscleRelationRepositoryImpl @Inject constructor(
    private val mapper: AnatomyMapper,
    private val muscleRelationDao: MuscleRelationDao,
) : MuscleRelationRepository {

    override suspend fun getByMuscle(muscleId: Long): List<MuscleRelationEntry> {
        return muscleRelationDao.getByMuscle(muscleId).map { mapper.map(it) }
    }

    override suspend fun insert(relation: MuscleRelationEntry) {
        return muscleRelationDao.insert(mapper.map(relation))
    }

    override suspend fun delete(relation: MuscleRelationEntry) {
        return muscleRelationDao.delete(mapper.map(relation))
    }
}
