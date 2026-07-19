package com.vasev.trainingapp.feature.programs.domain.entity

/**
 * Domain model of a microcycle — a block of training/rest days inside a program (or standalone) /
 * Domain-модель микроцикла — блок тренировочных дней и дней отдыха внутри программы (или отдельный)
 *
 * `programId` is nullable for standalone microcycles / `programId` nullable для отдельных микроциклов
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class Microcycle(
    val description: String?,
    val id: Long,
    val order: Int,
    val programId: Long?,
    val remoteId: String?,
    val title: String,
)
