package com.vasev.trainingapp.feature.nutrition.domain.repository

import com.vasev.trainingapp.feature.nutrition.domain.entity.FoodLog
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing food log entries — the contract the `ui` layer depends on /
 * Репозиторий (интерфейс) доступа к записям дневника питания — контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface FoodLogRepository {

    fun observeByUser(userId: Long): Flow<List<FoodLog>>

    fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<FoodLog>>

    suspend fun getById(id: Long): FoodLog?

    suspend fun getByUser(userId: Long): List<FoodLog>

    suspend fun getByUserAndDateRange(from: Long, to: Long, userId: Long): List<FoodLog>

    suspend fun insert(log: FoodLog): Long

    suspend fun update(log: FoodLog)

    suspend fun delete(log: FoodLog)
}
