package com.vasev.trainingapp.feature.weight.data.repository

import com.vasev.trainingapp.core.database.dao.WeightMeasurementDao
import com.vasev.trainingapp.feature.weight.data.mapper.WeightMapper
import com.vasev.trainingapp.feature.weight.domain.entity.WeightMeasurement
import com.vasev.trainingapp.feature.weight.domain.repository.WeightRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [WeightRepository] backed by the Room [WeightMeasurementDao]. /
 * Реализация [WeightRepository] на основе Room [WeightMeasurementDao].
 *
 * `@Inject` — Hilt creates this class and injects [weightMeasurementDao] and [mapper] /
 * `@Inject` — Hilt создаёт этот класс и внедряет [weightMeasurementDao] и [mapper]
 */
class WeightRepositoryImpl @Inject constructor(
    private val mapper: WeightMapper,
    private val weightMeasurementDao: WeightMeasurementDao,
) : WeightRepository {

    override fun observeByUser(userId: Long): Flow<List<WeightMeasurement>> {
        return weightMeasurementDao.observeByUser(userId).map { list -> list.map { mapper.map(it) } }
    }

    override fun observeByUserAndDateRange(from: Long, to: Long, userId: Long): Flow<List<WeightMeasurement>> {
        return weightMeasurementDao.observeByUserAndDateRange(from, to, userId).map { list ->
            list.map { mapper.map(it) }
        }
    }

    override suspend fun getById(id: Long): WeightMeasurement? {
        return weightMeasurementDao.getById(id)?.let { mapper.map(it) }
    }

    override suspend fun getByUser(userId: Long): List<WeightMeasurement> {
        return weightMeasurementDao.getByUser(userId).map { mapper.map(it) }
    }

    override suspend fun insert(measurement: WeightMeasurement): Long {
        return weightMeasurementDao.insert(mapper.map(measurement))
    }

    override suspend fun update(measurement: WeightMeasurement) {
        return weightMeasurementDao.update(mapper.map(measurement))
    }

    override suspend fun delete(measurement: WeightMeasurement) {
        return weightMeasurementDao.delete(mapper.map(measurement))
    }
}
