package com.vasev.trainingapp.core.referencedata.model

/**
 * Domain model of a single muscle belonging to a group / Domain-модель отдельной мышцы, принадлежащей группе
 *
 * No Room annotations — this is a pure domain model used by feature modules /
 * Без Room-аннотаций — чистая domain-модель, используется feature-модулями
 */
data class Muscle(
    val description: String?,
    val groupId: Long,
    val id: Long,
    val imageUrl: String?,
    val name: String,
)
