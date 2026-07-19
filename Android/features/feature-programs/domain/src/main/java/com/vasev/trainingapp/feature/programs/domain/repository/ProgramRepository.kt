package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.Program
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing training programs — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к программам тренировок — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ProgramRepository {

    fun observeAll(): Flow<List<Program>>

    fun observeBuiltin(): Flow<List<Program>>

    fun observeFavorites(): Flow<List<Program>>

    fun observeCreatedByUser(userId: Long): Flow<List<Program>>

    suspend fun getById(id: Long): Program?

    suspend fun getByRemoteId(remoteId: String): Program?

    suspend fun insert(program: Program): Long

    suspend fun update(program: Program)

    suspend fun delete(program: Program)

    suspend fun setFavorite(id: Long, isFavorite: Boolean)
}
