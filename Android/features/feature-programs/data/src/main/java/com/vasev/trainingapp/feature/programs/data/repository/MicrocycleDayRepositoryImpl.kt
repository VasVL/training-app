package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.MicrocycleDayDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.MicrocycleDay
import com.vasev.trainingapp.feature.programs.domain.repository.MicrocycleDayRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [MicrocycleDayRepository] backed by the Room [MicrocycleDayDao]. /
 * Реализация [MicrocycleDayRepository] на основе Room [MicrocycleDayDao].
 *
 * `@Inject` — Hilt creates this class and injects [microcycleDayDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [microcycleDayDao] и [mapper]
 */
class MicrocycleDayRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val microcycleDayDao: MicrocycleDayDao,
) : MicrocycleDayRepository {

    override fun observeByMicrocycle(microcycleId: Long): Flow<List<MicrocycleDay>> {
        return microcycleDayDao.observeByMicrocycle(microcycleId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): MicrocycleDay? {
        return microcycleDayDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByMicrocycle(microcycleId: Long): List<MicrocycleDay> {
        return microcycleDayDao.getByMicrocycle(microcycleId).map { mapper.map(it) }
    }

    override suspend fun insert(day: MicrocycleDay): Long {
        return microcycleDayDao.insert(mapper.map(day))
    }

    override suspend fun update(day: MicrocycleDay) {
        return microcycleDayDao.update(mapper.map(day))
    }

    override suspend fun delete(day: MicrocycleDay) {
        return microcycleDayDao.delete(mapper.map(day))
    }
}
