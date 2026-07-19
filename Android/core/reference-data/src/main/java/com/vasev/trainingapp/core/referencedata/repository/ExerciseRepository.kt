package com.vasev.trainingapp.core.referencedata.repository

import com.vasev.trainingapp.core.referencedata.model.Exercise
import com.vasev.trainingapp.core.referencedata.model.ExerciseMuscle
import kotlinx.coroutines.flow.Flow

/**
 * Port (interface) for accessing exercises / Порт (интерфейс) доступа к упражнениям
 *
 * Implementations live in `core/database` (Room adapter) and map Entity → domain model /
 * Реализации живут в `core/database` (Room-адаптер) и маппят Entity → domain-модель
 */
interface ExerciseRepository {

    suspend fun getById(id: Long): Exercise?

    suspend fun getByRemoteId(remoteId: String): Exercise?

    suspend fun getMusclesForExercise(exerciseId: Long): List<ExerciseMuscle>

    suspend fun softDelete(id: Long): Int

    fun observeAll(): Flow<List<Exercise>>

    fun observeBuiltin(): Flow<List<Exercise>>

    fun observeCreatedByUser(userId: Long): Flow<List<Exercise>>
}
