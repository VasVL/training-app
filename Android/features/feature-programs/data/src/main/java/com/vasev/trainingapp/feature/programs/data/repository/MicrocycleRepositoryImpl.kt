package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.MicrocycleDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.Microcycle
import com.vasev.trainingapp.feature.programs.domain.repository.MicrocycleRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [MicrocycleRepository] backed by the Room [MicrocycleDao]. /
 * Реализация [MicrocycleRepository] на основе Room [MicrocycleDao].
 *
 * `@Inject` — Hilt creates this class and injects [microcycleDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [microcycleDao] и [mapper]
 */
class MicrocycleRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val microcycleDao: MicrocycleDao,
) : MicrocycleRepository {

    override fun observeByProgram(programId: Long): Flow<List<Microcycle>> {
        return microcycleDao.observeByProgram(programId).map { list -> list.map { mapper.map(it) } }
    }

    override fun observeStandalone(): Flow<List<Microcycle>> {
        return microcycleDao.observeStandalone().map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): Microcycle? {
        return microcycleDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun insert(microcycle: Microcycle): Long {
        return microcycleDao.insert(mapper.map(microcycle))
    }

    override suspend fun update(microcycle: Microcycle) {
        return microcycleDao.update(mapper.map(microcycle))
    }

    override suspend fun delete(microcycle: Microcycle) {
        return microcycleDao.delete(mapper.map(microcycle))
    }
}
