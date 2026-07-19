package com.vasev.trainingapp.feature.anatomy.domain.entity

/**
 * Domain model of a single muscle belonging to a group / Domain-модель отдельной мышцы, принадлежащей группе
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class Muscle(
    val description: String?,
    val groupId: Long,
    val id: Long,
    val imageUrl: String?,
    val name: String,
    val remoteId: String?,
)
