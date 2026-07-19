package com.vasev.trainingapp.feature.weight.domain.repository

import com.vasev.trainingapp.feature.weight.domain.entity.WeightMeasurement
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing body weight measurements — the contract the `ui` layer
 * depends on / Репозиторий (интерфейс) доступа к измерениям веса тела — контракт, на который
 * опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface WeightRepository {

    fun observeByUser(userId: Long): Flow<List<WeightMeasurement>>

    fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<WeightMeasurement>>

    suspend fun getById(id: Long): WeightMeasurement?

    suspend fun getByUser(userId: Long): List<WeightMeasurement>

    suspend fun insert(measurement: WeightMeasurement): Long

    suspend fun update(measurement: WeightMeasurement)

    suspend fun delete(measurement: WeightMeasurement)
}
