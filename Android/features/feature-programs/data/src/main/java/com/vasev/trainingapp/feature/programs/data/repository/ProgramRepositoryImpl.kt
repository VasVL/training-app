package com.vasev.trainingapp.feature.programs.data.repository

import com.vasev.trainingapp.core.database.dao.ProgramDao
import com.vasev.trainingapp.feature.programs.data.mapper.ProgramsMapper
import com.vasev.trainingapp.feature.programs.domain.entity.Program
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [ProgramRepository] backed by the Room [ProgramDao]. /
 * Реализация [ProgramRepository] на основе Room [ProgramDao].
 *
 * `@Inject` — Hilt creates this class and injects [programDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [programDao] и [mapper]
 */
class ProgramRepositoryImpl @Inject constructor(
    private val mapper: ProgramsMapper,
    private val programDao: ProgramDao,
) : ProgramRepository {

    override fun observeAll(): Flow<List<Program>> {
        return programDao.observeAll().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeBuiltin(): Flow<List<Program>> {
        return programDao.observeBuiltin().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeFavorites(): Flow<List<Program>> {
        return programDao.observeFavorites().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeCreatedByUser(userId: Long): Flow<List<Program>> {
        return programDao.observeCreatedByUser(userId).map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): Program? {
        return programDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByRemoteId(remoteId: String): Program? {
        return programDao.getByRemoteId(remoteId)?.let { mapper.map(it) }
    }

    override suspend fun insert(program: Program): Long {
        return programDao.insert(mapper.map(program))
    }

    override suspend fun update(program: Program) {
        return programDao.update(mapper.map(program))
    }

    override suspend fun delete(program: Program) {
        return programDao.delete(mapper.map(program))
    }

    override suspend fun setFavorite(id: Long, isFavorite: Boolean) {
        return programDao.setFavorite(id, isFavorite)
    }
}
