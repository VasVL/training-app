package com.vasev.trainingapp.feature.auth.domain.entity

import com.vasev.trainingapp.feature.auth.domain.entity.type.Gender
import com.vasev.trainingapp.feature.auth.domain.entity.type.HeightUnit
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import com.vasev.trainingapp.feature.auth.domain.entity.type.WeightUnit

/**
 * Domain model of a user of the app: owner (trainer) or trainee /
 * Domain-модель пользователя приложения: владелец (тренер) или подопечный
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class User(
    val age: Int,
    val createdAt: Long,
    val gender: Gender,
    val height: Double,
    val heightUnit: HeightUnit,
    val id: Long,
    val isDefault: Boolean,
    val name: String,
    val remoteId: String?,
    val role: UserRole,
    val weight: Double,
    val weightUnit: WeightUnit,
)
