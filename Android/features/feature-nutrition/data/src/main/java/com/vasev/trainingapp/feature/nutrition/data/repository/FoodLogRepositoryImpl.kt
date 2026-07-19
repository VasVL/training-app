package com.vasev.trainingapp.feature.nutrition.data.repository

import com.vasev.trainingapp.core.database.dao.FoodLogDao
import com.vasev.trainingapp.feature.nutrition.data.mapper.NutritionMapper
import com.vasev.trainingapp.feature.nutrition.domain.entity.FoodLog
import com.vasev.trainingapp.feature.nutrition.domain.repository.FoodLogRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [FoodLogRepository] backed by the Room [FoodLogDao]. /
 * Реализация [FoodLogRepository] на основе Room [FoodLogDao].
 *
 * `@Inject` — Hilt creates this class and injects [foodLogDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [foodLogDao] и [mapper]
 */
class FoodLogRepositoryImpl @Inject constructor(
    private val foodLogDao: FoodLogDao,
    private val mapper: NutritionMapper,
) : FoodLogRepository {

    override fun observeByUser(userId: Long): Flow<List<FoodLog>> {
        return foodLogDao.observeByUser(userId).map { list -> list.map { mapper.map(it) } }
    }

    override fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<FoodLog>> {
        return foodLogDao.observeByUserAndDateRange(from, to, userId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): FoodLog? {
        return foodLogDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByUser(userId: Long): List<FoodLog> {
        return foodLogDao.getByUser(userId).map { mapper.map(it) }
    }

    override suspend fun getByUserAndDateRange(from: Long, to: Long, userId: Long): List<FoodLog> {
        return foodLogDao.getByUserAndDateRange(from, to, userId).map { mapper.map(it) }
    }

    override suspend fun insert(log: FoodLog): Long {
        return foodLogDao.insert(mapper.map(log))
    }

    override suspend fun update(log: FoodLog) {
        return foodLogDao.update(mapper.map(log))
    }

    override suspend fun delete(log: FoodLog) {
        return foodLogDao.delete(mapper.map(log))
    }
}
