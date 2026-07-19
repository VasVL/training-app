package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.Microcycle
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing microcycles — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к микроциклам — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface MicrocycleRepository {

    fun observeByProgram(programId: Long): Flow<List<Microcycle>>

    fun observeStandalone(): Flow<List<Microcycle>>

    suspend fun getById(id: Long): Microcycle?

    suspend fun insert(microcycle: Microcycle): Long

    suspend fun update(microcycle: Microcycle)

    suspend fun delete(microcycle: Microcycle)
}
