package com.vasev.trainingapp.feature.anatomy.domain.repository

import com.vasev.trainingapp.feature.anatomy.domain.entity.MuscleGroup
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing muscle groups — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к группам мышц — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface MuscleGroupRepository {

    fun observeAll(): Flow<List<MuscleGroup>>

    suspend fun getById(id: Long): MuscleGroup?

    suspend fun insert(group: MuscleGroup): Long

    suspend fun update(group: MuscleGroup)

    suspend fun delete(group: MuscleGroup)
}
