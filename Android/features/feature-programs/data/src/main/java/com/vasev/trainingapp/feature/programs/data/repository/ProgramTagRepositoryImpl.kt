package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.ProgramTagDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.ProgramTag
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramTagRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ProgramTagRepository] backed by the Room [ProgramTagDao]. /
 * Реализация [ProgramTagRepository] на основе Room [ProgramTagDao].
 *
 * `@Inject` — Hilt creates this class and injects [programTagDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [programTagDao] и [mapper]
 */
class ProgramTagRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val programTagDao: ProgramTagDao,
) : ProgramTagRepository {

    override fun observeByProgram(programId: Long): Flow<List<ProgramTag>> {
        return programTagDao.observeByProgram(programId).map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getByProgram(programId: Long): List<ProgramTag> {
        return programTagDao.getByProgram(programId).map { mapper.map(it) }
    }

    override suspend fun getByTag(tag: String): List<ProgramTag> {
        return programTagDao.getByTag(tag).map { mapper.map(it) }
    }

    override suspend fun insert(tag: ProgramTag) {
        return programTagDao.insert(mapper.map(tag))
    }

    override suspend fun delete(tag: ProgramTag) {
        return programTagDao.delete(mapper.map(tag))
    }
}
