package com.vasev.trainingapp.feature.nutrition.data.mapper

import com.vasev.trainingapp.core.database.entity.FoodItemEntity
import com.vasev.trainingapp.core.database.entity.FoodLogEntity
import com.vasev.trainingapp.feature.nutrition.domain.entity.FoodItem
import com.vasev.trainingapp.feature.nutrition.domain.entity.FoodLog
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entities (FoodItemEntity, FoodLogEntity) and domain models (FoodItem,
 * FoodLog). / Маппер между Room-сущностями (FoodItemEntity, FoodLogEntity) и domain-моделями
 * (FoodItem, FoodLog).
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class NutritionMapper @Inject constructor() {

    fun map(entity: FoodItemEntity): FoodItem {
        return FoodItem(
            calories = entity.calories,
            carbs = entity.carbs,
            category = entity.category,
            fat = entity.fat,
            id = entity.id,
            imageUrl = entity.imageUrl,
            name = entity.name,
            protein = entity.protein,
            remoteId = entity.remoteId,
        )
    }

    fun map(domain: FoodItem): FoodItemEntity {
        return FoodItemEntity(
            calories = domain.calories,
            carbs = domain.carbs,
            category = domain.category,
            fat = domain.fat,
            id = domain.id,
            imageUrl = domain.imageUrl,
            name = domain.name,
            protein = domain.protein,
            remoteId = domain.remoteId,
        )
    }

    fun map(entity: FoodLogEntity): FoodLog {
        return FoodLog(
            amount = entity.amount,
            foodId = entity.foodId,
            id = entity.id,
            loggedAt = entity.loggedAt,
            userId = entity.userId,
        )
    }

    fun map(domain: FoodLog): FoodLogEntity {
        return FoodLogEntity(
            amount = domain.amount,
            foodId = domain.foodId,
            id = domain.id,
            loggedAt = domain.loggedAt,
            userId = domain.userId,
        )
    }
}
