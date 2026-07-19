package com.vasev.trainingapp.feature.anatomy.domain.repository

import com.vasev.trainingapp.feature.anatomy.domain.entity.Muscle
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing muscles — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к мышцам — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface MuscleRepository {

    fun observeAll(): Flow<List<Muscle>>

    fun observeByGroup(groupId: Long): Flow<List<Muscle>>

    suspend fun getById(id: Long): Muscle?

    suspend fun insert(muscle: Muscle): Long

    suspend fun update(muscle: Muscle)

    suspend fun delete(muscle: Muscle)
}
