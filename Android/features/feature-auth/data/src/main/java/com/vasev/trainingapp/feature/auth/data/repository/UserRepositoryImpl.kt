package com.vasev.trainingapp.feature.auth.data.repository

import com.vasev.trainingapp.core.database.dao.UserDao
import com.vasev.trainingapp.feature.auth.data.mapper.AuthMapper
import com.vasev.trainingapp.feature.auth.data.mapper.UserListItemMapper
import com.vasev.trainingapp.feature.auth.domain.entity.User
import com.vasev.trainingapp.feature.auth.domain.entity.UserListItem
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [UserRepository] backed by the Room [UserDao]. /
 * Реализация [UserRepository] на основе Room [UserDao].
 *
 * `@Inject` — Hilt creates this class and injects its dependencies /
 * `@Inject` — Hilt создаёт этот класс и внедряет его зависимости
 */
internal class UserRepositoryImpl @Inject constructor(
    private val mapper: AuthMapper,
    private val userDao: UserDao,
    private val userListItemMapper: UserListItemMapper,
) : UserRepository {

    override fun observeAll(): Flow<List<User>> {
        return userDao.observeAll().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeForSelection(): Flow<List<UserListItem>> {
        return userDao.observeForSelection().map { list -> list.map { userListItemMapper.map(it) } }
    }

    override fun observeById(id: Long): Flow<User?> {
        return userDao.observeById(id).map { entity -> entity?.let { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): User? {
        return userDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByRemoteId(remoteId: String): User? {
        return userDao.getByRemoteId(remoteId)?.let { mapper.map(it) }
    }

    override suspend fun getDefault(): User? {
        return userDao.getDefault()?.let { mapper.map(it) }
    }

    override suspend fun insert(user: User): Long {
        return userDao.insert(mapper.map(user))
    }

    override suspend fun setActive(id: Long): Boolean {
        return userDao.setActive(id) > 0
    }

    override suspend fun update(user: User) {
        return userDao.update(mapper.map(user))
    }

    override suspend fun requestDeletion(id: Long): Boolean {
        return userDao.markPendingDeletion(id) > 0
    }

    override suspend fun cancelDeletion(id: Long): Boolean {
        return userDao.restorePendingDeletion(id) > 0
    }

    override suspend fun finalizeDeletion(id: Long): Boolean {
        return userDao.deletePendingUser(id) > 0
    }

    override suspend fun finalizePendingDeletions(): Int {
        return userDao.deleteAllPendingUsers()
    }
}
