package com.vasev.trainingapp.core.referencedata.repository

import com.vasev.trainingapp.core.referencedata.model.MuscleGroup
import kotlinx.coroutines.flow.Flow

/**
 * Port (interface) for accessing muscle groups / Порт (интерфейс) доступа к группам мышц
 *
 * Implementations live in `core/database` (Room adapter) and map Entity → domain model /
 * Реализации живут в `core/database` (Room-адаптер) и маппят Entity → domain-модель
 */
interface MuscleGroupRepository {

    suspend fun getById(id: Long): MuscleGroup?

    fun observeAll(): Flow<List<MuscleGroup>>
}
