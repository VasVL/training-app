package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.ProgramCategoryDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.ProgramCategoryEntry
import com.vasev.trainingapp.feature.programs.domain.entity.type.ProgramCategory
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramCategoryRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ProgramCategoryRepository] backed by the Room [ProgramCategoryDao]. /
 * Реализация [ProgramCategoryRepository] на основе Room [ProgramCategoryDao].
 *
 * `@Inject` — Hilt creates this class and injects [programCategoryDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [programCategoryDao] и [mapper]
 */
class ProgramCategoryRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val programCategoryDao: ProgramCategoryDao,
) : ProgramCategoryRepository {

    override fun observeByProgram(programId: Long): Flow<List<ProgramCategoryEntry>> {
        return programCategoryDao.observeByProgram(programId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getByProgram(programId: Long): List<ProgramCategoryEntry> {
        return programCategoryDao.getByProgram(programId).map { mapper.map(it) }
    }

    override suspend fun getByCategory(category: ProgramCategory): List<ProgramCategoryEntry> {
        return programCategoryDao.getByCategory(mapper.mapProgramCategory(category)).map {
            mapper.map(it)
        }
    }

    override suspend fun insert(category: ProgramCategoryEntry) {
        return programCategoryDao.insert(mapper.map(category))
    }

    override suspend fun delete(category: ProgramCategoryEntry) {
        return programCategoryDao.delete(mapper.map(category))
    }
}
