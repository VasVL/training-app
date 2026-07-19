package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.ProgramPrerequisiteDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.ProgramPrerequisite
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramPrerequisiteRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ProgramPrerequisiteRepository] backed by the Room [ProgramPrerequisiteDao]. /
 * Реализация [ProgramPrerequisiteRepository] на основе Room [ProgramPrerequisiteDao].
 *
 * `@Inject` — Hilt creates this class and injects [programPrerequisiteDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [programPrerequisiteDao] и [mapper]
 */
class ProgramPrerequisiteRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val programPrerequisiteDao: ProgramPrerequisiteDao,
) : ProgramPrerequisiteRepository {

    override fun observeByProgram(programId: Long): Flow<List<ProgramPrerequisite>> {
        return programPrerequisiteDao.observeByProgram(programId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): ProgramPrerequisite? {
        return programPrerequisiteDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByProgram(programId: Long): List<ProgramPrerequisite> {
        return programPrerequisiteDao.getByProgram(programId).map { mapper.map(it) }
    }

    override suspend fun insert(prerequisite: ProgramPrerequisite): Long {
        return programPrerequisiteDao.insert(mapper.map(prerequisite))
    }

    override suspend fun update(prerequisite: ProgramPrerequisite) {
        return programPrerequisiteDao.update(mapper.map(prerequisite))
    }

    override suspend fun delete(prerequisite: ProgramPrerequisite) {
        return programPrerequisiteDao.delete(mapper.map(prerequisite))
    }
}
