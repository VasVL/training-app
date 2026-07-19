package com.vasev.trainingapp.feature.nutrition.domain.entity

/**
 * Domain model of a food item from the nutrition database /
 * Domain-модель продукта из базы питания
 *
 * Stores nutritional values per 100g (or per serving — to be decided in the nutrition feature) /
 * Хранит nutritional values на 100г (или на порцию — решим в фиче питания)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class FoodItem(
    val calories: Double,
    val carbs: Double,
    val category: String?,
    val fat: Double,
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val protein: Double,
    val remoteId: String?,
)
