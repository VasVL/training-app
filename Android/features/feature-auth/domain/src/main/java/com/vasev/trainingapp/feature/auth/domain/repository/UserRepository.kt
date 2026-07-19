package com.vasev.trainingapp.feature.auth.domain.repository

import com.vasev.trainingapp.feature.auth.domain.entity.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing users — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к пользователям — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface UserRepository {

    fun observeAll(): Flow<List<User>>

    fun observeById(id: Long): Flow<User?>

    suspend fun getById(id: Long): User?

    suspend fun getByRemoteId(remoteId: String): User?

    suspend fun getDefault(): User?

    suspend fun insert(user: User): Long

    suspend fun update(user: User)

    suspend fun delete(user: User)
}
