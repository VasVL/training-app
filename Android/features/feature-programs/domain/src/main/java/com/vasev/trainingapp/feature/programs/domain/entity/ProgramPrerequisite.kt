package com.vasev.trainingapp.feature.programs.domain.entity

import com.vasev.trainingapp.feature.programs.domain.entity.type.PrerequisiteType

/**
 * Domain model of a prerequisite to start a program (e.g. required one-rep-max or required reps) /
 * Domain-модель условия для начала программы (например, нужный разовый максимум или число повторений)
 *
 * `exerciseId` is nullable: some prerequisites (e.g. bodyweight pull-ups) reference an exercise,
 * others don't / `exerciseId` nullable: часть условий (например, подтягивания с весом тела)
 * ссылается на упражнение, часть — нет
 *
 * Pure domain model used by the `ui` module; the `data` module is responsible for producing and
 * persisting instances of this class. / Чистая domain-модель, используемая модулем `ui`;
 * модуль `data` отвечает за создание и сохранение экземпляров этого класса.
 */
data class ProgramPrerequisite(
    val exerciseId: Long?,
    val id: Long,
    val programId: Long,
    val requiredValue: Double,
    val type: PrerequisiteType,
)
