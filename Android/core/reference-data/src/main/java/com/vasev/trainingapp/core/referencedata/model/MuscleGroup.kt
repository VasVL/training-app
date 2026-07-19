package com.vasev.trainingapp.core.referencedata.model

/**
 * Domain model of a muscle group (e.g. back, chest, legs) / Domain-модель группы мышц (например, спина, грудь, ноги)
 *
 * No Room annotations — this is a pure domain model used by feature modules /
 * Без Room-аннотаций — чистая domain-модель, используется feature-модулями
 */
data class MuscleGroup(
    val id: Long,
    val imageUrl: String?,
    val name: String,
)
