package com.vasev.trainingapp.feature.anatomy.domain.entity

/**
 * Domain model of a muscle group (e.g. back, chest, legs) / Domain-модель группы мышц (например, спина, грудь, ноги)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class MuscleGroup(
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val remoteId: String?,
)
