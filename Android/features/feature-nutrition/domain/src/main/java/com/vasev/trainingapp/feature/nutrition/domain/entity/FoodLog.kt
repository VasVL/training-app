package com.vasev.trainingapp.feature.nutrition.domain.entity

/**
 * Domain model of a food log entry: a user ate some amount of a food item at a given time /
 * Domain-модель записи в дневнике питания: пользователь съел некоторое количество продукта в указанное время
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class FoodLog(
    val amount: Double,
    val foodId: Long,
    val id: Long,
    val loggedAt: Long,
    val userId: Long,
)
