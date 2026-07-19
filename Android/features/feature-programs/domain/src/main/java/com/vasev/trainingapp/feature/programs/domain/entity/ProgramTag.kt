package com.vasev.trainingapp.feature.programs.domain.entity

/**
 * Domain model of a user-defined tag of a program (free-form string) /
 * Domain-модель пользовательского тега программы (произвольная строка)
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class ProgramTag(
    val programId: Long,
    val tag: String,
)
