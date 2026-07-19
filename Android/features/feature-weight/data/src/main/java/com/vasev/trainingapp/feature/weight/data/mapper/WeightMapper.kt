package com.vasev.trainingapp.feature.weight.data.mapper

import com.vasev.trainingapp.core.database.entity.WeightMeasurementEntity
import com.vasev.trainingapp.feature.weight.domain.entity.WeightMeasurement
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entity [WeightMeasurementEntity] and domain model [WeightMeasurement]. /
 * Маппер между Room-сущностью [WeightMeasurementEntity] и domain-моделью [WeightMeasurement].
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class WeightMapper @Inject constructor() {

    fun map(entity: WeightMeasurementEntity): WeightMeasurement {
        return WeightMeasurement(
            id = entity.id,
            measuredAt = entity.measuredAt,
            userId = entity.userId,
            weight = entity.weight,
        )
    }

    fun map(domain: WeightMeasurement): WeightMeasurementEntity {
        return WeightMeasurementEntity(
            id = domain.id,
            measuredAt = domain.measuredAt,
            userId = domain.userId,
            weight = domain.weight,
        )
    }
}
