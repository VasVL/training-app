package com.vasev.trainingapp.feature.auth.data.provider

import com.vasev.trainingapp.core.database.dao.UserDao
import com.vasev.trainingapp.feature.auth.contract.ActiveUserProvider
import com.vasev.trainingapp.feature.auth.contract.entity.ActiveUserSummary
import com.vasev.trainingapp.feature.auth.data.mapper.ActiveUserContractMapper
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber

/**
 * Provides the active-user contract directly from the minimal Room projection.
 * Предоставляет contract активного пользователя напрямую из минимальной Room-проекции.
 *
 * `@Inject` — Hilt creates this adapter and supplies its dependencies.
 * `@Inject` — Hilt создаёт этот адаптер и передаёт ему зависимости.
 */
internal class ActiveUserProviderImpl @Inject constructor(
    private val activeUserContractMapper: ActiveUserContractMapper,
    private val userDao: UserDao,
) : ActiveUserProvider {

    override fun observeActiveUser(): Flow<ActiveUserSummary?> {
        Timber.d("observeActiveUser")
        return userDao.observeActiveUser().map { projection ->
            projection?.let { activeUserContractMapper.map(it) }
        }
    }
}
