package com.vasev.trainingapp.feature.programs.domain.entity

import com.vasev.trainingapp.feature.programs.domain.entity.type.MicrocycleDayType

/**
 * Domain model of a day within a microcycle: either a workout day or a rest day /
 * Domain-модель дня внутри микроцикла: тренировочный или день отдыха
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class MicrocycleDay(
    val id: Long,
    val microcycleId: Long,
    val order: Int,
    val title: String,
    val type: MicrocycleDayType,
)
