package com.vasev.trainingapp.feature.auth.domain.repository

import com.vasev.trainingapp.feature.auth.domain.entity.UserMax
import com.vasev.trainingapp.feature.auth.domain.entity.UserMaxWithExercise
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing user one-rep-max records — the contract the `ui` layer
 * depends on / Репозиторий (интерфейс) доступа к записям разовых максимумов пользователей —
 * контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface UserMaxRepository {

    fun observeByUser(userId: Long): Flow<List<UserMax>>

    fun observeForUserProfile(userId: Long): Flow<List<UserMaxWithExercise>>

    suspend fun getById(id: Long): UserMax?

    suspend fun getLatestForExercise(exerciseId: Long, userId: Long): UserMax?

    suspend fun insert(userMax: UserMax): Long

    suspend fun update(userMax: UserMax)

    suspend fun delete(userMax: UserMax)
}
