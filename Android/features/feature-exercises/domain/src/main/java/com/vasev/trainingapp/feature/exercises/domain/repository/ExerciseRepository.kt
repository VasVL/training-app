package com.vasev.trainingapp.feature.exercises.domain.repository

import com.vasev.trainingapp.feature.exercises.domain.entity.Exercise
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing exercises — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к упражнениям — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ExerciseRepository {

    fun observeAll(): Flow<List<Exercise>>

    fun observeBuiltin(): Flow<List<Exercise>>

    fun observeCreatedByUser(userId: Long): Flow<List<Exercise>>

    suspend fun getById(id: Long): Exercise?

    suspend fun getByRemoteId(remoteId: String): Exercise?

    suspend fun insert(exercise: Exercise): Long

    suspend fun update(exercise: Exercise)

    suspend fun softDelete(id: Long): Int
}
