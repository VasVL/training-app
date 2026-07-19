package com.vasev.trainingapp.feature.nutrition.domain.repository

import com.vasev.trainingapp.feature.nutrition.domain.entity.FoodItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing food items — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к продуктам — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface FoodItemRepository {

    fun observeAll(): Flow<List<FoodItem>>

    fun observeByCategory(category: String): Flow<List<FoodItem>>

    suspend fun getById(id: Long): FoodItem?

    suspend fun getByRemoteId(remoteId: String): FoodItem?

    suspend fun searchByName(query: String): List<FoodItem>

    suspend fun insert(item: FoodItem): Long

    suspend fun update(item: FoodItem)

    suspend fun delete(item: FoodItem)
}
