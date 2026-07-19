package com.vasev.trainingapp.feature.programs.domain.entity

import com.vasev.trainingapp.feature.programs.domain.entity.type.ProgramCategory

/**
 * Domain model of a built-in category tag of a program (enum-based, stable key) /
 * Domain-модель вшитой категории программы (на enum, стабильный ключ)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class ProgramCategoryEntry(
    val category: ProgramCategory,
    val programId: Long,
)
