package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.MicrocycleDay
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing microcycle days — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к дням микроциклов — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface MicrocycleDayRepository {

    fun observeByMicrocycle(microcycleId: Long): Flow<List<MicrocycleDay>>

    suspend fun getById(id: Long): MicrocycleDay?

    suspend fun getByMicrocycle(microcycleId: Long): List<MicrocycleDay>

    suspend fun insert(day: MicrocycleDay): Long

    suspend fun update(day: MicrocycleDay)

    suspend fun delete(day: MicrocycleDay)
}
