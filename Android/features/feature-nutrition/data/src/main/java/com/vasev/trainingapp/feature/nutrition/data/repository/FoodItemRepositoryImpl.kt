package com.vasev.trainingapp.feature.nutrition.data.repository

import com.vasev.trainingapp.core.database.dao.FoodItemDao
import com.vasev.trainingapp.feature.nutrition.data.mapper.NutritionMapper
import com.vasev.trainingapp.feature.nutrition.domain.entity.FoodItem
import com.vasev.trainingapp.feature.nutrition.domain.repository.FoodItemRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [FoodItemRepository] backed by the Room [FoodItemDao]. /
 * Реализация [FoodItemRepository] на основе Room [FoodItemDao].
 *
 * `@Inject` — Hilt creates this class and injects [foodItemDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [foodItemDao] и [mapper]
 */
class FoodItemRepositoryImpl @Inject constructor(
    private val foodItemDao: FoodItemDao,
    private val mapper: NutritionMapper,
) : FoodItemRepository {

    override fun observeAll(): Flow<List<FoodItem>> {
        return foodItemDao.observeAll().map { list -> list.map { mapper.map(it) } }
    }

    override fun observeByCategory(category: String): Flow<List<FoodItem>> {
        return foodItemDao.observeByCategory(category).map { list -> list.map { mapper.map(it) } }
    }

    override suspend fun getById(id: Long): FoodItem? {
        return foodItemDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByRemoteId(remoteId: String): FoodItem? {
        return foodItemDao.getByRemoteId(remoteId)?.let { mapper.map(it) }
    }

    override suspend fun searchByName(query: String): List<FoodItem> {
        return foodItemDao.searchByName(query).map { mapper.map(it) }
    }

    override suspend fun insert(item: FoodItem): Long {
        return foodItemDao.insert(mapper.map(item))
    }

    override suspend fun update(item: FoodItem) {
        return foodItemDao.update(mapper.map(item))
    }

    override suspend fun delete(item: FoodItem) {
        return foodItemDao.delete(mapper.map(item))
    }
}
