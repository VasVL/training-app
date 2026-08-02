package com.vasev.trainingapp.feature.auth.data.repository

import com.vasev.trainingapp.core.database.dao.UserMaxDao
import com.vasev.trainingapp.feature.auth.data.mapper.AuthMapper
import com.vasev.trainingapp.feature.auth.domain.entity.UserMax
import com.vasev.trainingapp.feature.auth.domain.entity.UserMaxWithExercise
import com.vasev.trainingapp.feature.auth.domain.repository.UserMaxRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [UserMaxRepository] backed by the Room [UserMaxDao]. /
 * Реализация [UserMaxRepository] на основе Room [UserMaxDao].
 *
 * `@Inject` — Hilt creates this class and injects [userMaxDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [userMaxDao] и [mapper]
 */
class UserMaxRepositoryImpl @Inject constructor(
    private val mapper: AuthMapper,
    private val userMaxDao: UserMaxDao,
) : UserMaxRepository {

    override fun observeByUser(userId: Long): Flow<List<UserMax>> {
        return userMaxDao.observeByUser(userId).map { list -> list.map { mapper.map(it) } }
    }

    override fun observeForUserProfile(userId: Long): Flow<List<UserMaxWithExercise>> {
        return userMaxDao.observeForUserProfile(userId).map { list ->
            list.map { projection -> mapper.map(projection) }
        }
    }

    override suspend fun getById(id: Long): UserMax? {
        return userMaxDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getLatestForExercise(exerciseId: Long, userId: Long): UserMax? {
        return userMaxDao.getLatestForExercise(exerciseId, userId)?.let { mapper.map(it) }
    }

    override suspend fun insert(userMax: UserMax): Long {
        return userMaxDao.insert(mapper.map(userMax))
    }

    override suspend fun update(userMax: UserMax) {
        return userMaxDao.update(mapper.map(userMax))
    }

    override suspend fun delete(userMax: UserMax) {
        return userMaxDao.delete(mapper.map(userMax))
    }
}
