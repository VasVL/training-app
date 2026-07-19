package com.vasev.trainingapp.feature.programs.data.mapper

import com.vasev.trainingapp.core.database.entity.ExerciseSetEntity
import com.vasev.trainingapp.core.database.entity.MicrocycleDayEntity
import com.vasev.trainingapp.core.database.entity.MicrocycleEntity
import com.vasev.trainingapp.core.database.entity.ProgramCategoryEntity
import com.vasev.trainingapp.core.database.entity.ProgramEntity
import com.vasev.trainingapp.core.database.entity.ProgramPrerequisiteEntity
import com.vasev.trainingapp.core.database.entity.ProgramTagEntity
import com.vasev.trainingapp.core.database.entity.SetTemplateEntity
import com.vasev.trainingapp.core.database.entity.types.MicrocycleDayType as EntityMicrocycleDayType
import com.vasev.trainingapp.core.database.entity.types.PrerequisiteType as EntityPrerequisiteType
import com.vasev.trainingapp.core.database.entity.types.ProgramCategory as EntityProgramCategory
import com.vasev.trainingapp.core.database.entity.types.RepType as EntityRepType
import com.vasev.trainingapp.core.database.entity.types.SetType as EntitySetType
import com.vasev.trainingapp.core.database.entity.types.WeightType as EntityWeightType
import com.vasev.trainingapp.feature.programs.domain.entity.ExerciseSet
import com.vasev.trainingapp.feature.programs.domain.entity.Microcycle
import com.vasev.trainingapp.feature.programs.domain.entity.MicrocycleDay
import com.vasev.trainingapp.feature.programs.domain.entity.Program
import com.vasev.trainingapp.feature.programs.domain.entity.ProgramCategoryEntry
import com.vasev.trainingapp.feature.programs.domain.entity.ProgramPrerequisite
import com.vasev.trainingapp.feature.programs.domain.entity.ProgramTag
import com.vasev.trainingapp.feature.programs.domain.entity.SetTemplate
import com.vasev.trainingapp.feature.programs.domain.entity.type.MicrocycleDayType
import com.vasev.trainingapp.feature.programs.domain.entity.type.PrerequisiteType
import com.vasev.trainingapp.feature.programs.domain.entity.type.ProgramCategory
import com.vasev.trainingapp.feature.programs.domain.entity.type.RepType
import com.vasev.trainingapp.feature.programs.domain.entity.type.SetType
import com.vasev.trainingapp.feature.programs.domain.entity.type.WeightType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entities (Program*, Microcycle*, ExerciseSet*, SetTemplate*) and domain
 * models. / Маппер между Room-сущностями (Program*, Microcycle*, ExerciseSet*, SetTemplate*) и
 * domain-моделями.
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class ProgramsMapper @Inject constructor() {

    fun map(entity: ProgramEntity): Program {
        return Program(
            canSkipWorkouts = entity.canSkipWorkouts,
            createdAt = entity.createdAt,
            createdByUserId = entity.createdByUserId,
            description = entity.description,
            id = entity.id,
            isBuiltin = entity.isBuiltin,
            isFavorite = entity.isFavorite,
            recommendedAdjustmentPercent = entity.recommendedAdjustmentPercent,
            remoteId = entity.remoteId,
            title = entity.title,
        )
    }

    fun map(domain: Program): ProgramEntity {
        return ProgramEntity(
            canSkipWorkouts = domain.canSkipWorkouts,
            createdAt = domain.createdAt,
            createdByUserId = domain.createdByUserId,
            description = domain.description,
            id = domain.id,
            isBuiltin = domain.isBuiltin,
            isFavorite = domain.isFavorite,
            recommendedAdjustmentPercent = domain.recommendedAdjustmentPercent,
            remoteId = domain.remoteId,
            title = domain.title,
        )
    }

    fun map(entity: MicrocycleEntity): Microcycle {
        return Microcycle(
            description = entity.description,
            id = entity.id,
            order = entity.order,
            programId = entity.programId,
            remoteId = entity.remoteId,
            title = entity.title,
        )
    }

    fun map(domain: Microcycle): MicrocycleEntity {
        return MicrocycleEntity(
            description = domain.description,
            id = domain.id,
            order = domain.order,
            programId = domain.programId,
            remoteId = domain.remoteId,
            title = domain.title,
        )
    }

    fun map(entity: MicrocycleDayEntity): MicrocycleDay {
        return MicrocycleDay(
            id = entity.id,
            microcycleId = entity.microcycleId,
            order = entity.order,
            title = entity.title,
            type = mapMicrocycleDayType(entity.type),
        )
    }

    fun map(domain: MicrocycleDay): MicrocycleDayEntity {
        return MicrocycleDayEntity(
            id = domain.id,
            microcycleId = domain.microcycleId,
            order = domain.order,
            title = domain.title,
            type = mapMicrocycleDayType(domain.type),
        )
    }

    fun map(entity: ExerciseSetEntity): ExerciseSet {
        return ExerciseSet(
            dropsetReductions = entity.dropsetReductions,
            durationValue = entity.durationValue,
            exerciseId = entity.exerciseId,
            id = entity.id,
            order = entity.order,
            repType = mapRepType(entity.repType),
            repValue = entity.repValue,
            restTimeSeconds = entity.restTimeSeconds,
            rpeValue = entity.rpeValue,
            setType = mapSetType(entity.setType),
            supersetGroupId = entity.supersetGroupId,
            weightRefExerciseId = entity.weightRefExerciseId,
            weightType = mapWeightType(entity.weightType),
            weightValue = entity.weightValue,
            workoutTemplateId = entity.workoutTemplateId,
        )
    }

    fun map(domain: ExerciseSet): ExerciseSetEntity {
        return ExerciseSetEntity(
            dropsetReductions = domain.dropsetReductions,
            durationValue = domain.durationValue,
            exerciseId = domain.exerciseId,
            id = domain.id,
            order = domain.order,
            repType = mapRepType(domain.repType),
            repValue = domain.repValue,
            restTimeSeconds = domain.restTimeSeconds,
            rpeValue = domain.rpeValue,
            setType = mapSetType(domain.setType),
            supersetGroupId = domain.supersetGroupId,
            weightRefExerciseId = domain.weightRefExerciseId,
            weightType = mapWeightType(domain.weightType),
            weightValue = domain.weightValue,
            workoutTemplateId = domain.workoutTemplateId,
        )
    }

    fun map(entity: SetTemplateEntity): SetTemplate {
        return SetTemplate(
            durationValue = entity.durationValue,
            exerciseSetId = entity.exerciseSetId,
            id = entity.id,
            order = entity.order,
            repType = mapRepType(entity.repType),
            repValue = entity.repValue,
            rpeValue = entity.rpeValue,
            weightRefExerciseId = entity.weightRefExerciseId,
            weightType = mapWeightType(entity.weightType),
            weightValue = entity.weightValue,
        )
    }

    fun map(domain: SetTemplate): SetTemplateEntity {
        return SetTemplateEntity(
            durationValue = domain.durationValue,
            exerciseSetId = domain.exerciseSetId,
            id = domain.id,
            order = domain.order,
            repType = mapRepType(domain.repType),
            repValue = domain.repValue,
            rpeValue = domain.rpeValue,
            weightRefExerciseId = domain.weightRefExerciseId,
            weightType = mapWeightType(domain.weightType),
            weightValue = domain.weightValue,
        )
    }

    fun map(entity: ProgramCategoryEntity): ProgramCategoryEntry {
        return ProgramCategoryEntry(
            category = mapProgramCategory(entity.category),
            programId = entity.programId,
        )
    }

    fun map(domain: ProgramCategoryEntry): ProgramCategoryEntity {
        return ProgramCategoryEntity(
            category = mapProgramCategory(domain.category),
            programId = domain.programId,
        )
    }

    fun map(entity: ProgramTagEntity): ProgramTag {
        return ProgramTag(
            programId = entity.programId,
            tag = entity.tag,
        )
    }

    fun map(domain: ProgramTag): ProgramTagEntity {
        return ProgramTagEntity(
            programId = domain.programId,
            tag = domain.tag,
        )
    }

    fun map(entity: ProgramPrerequisiteEntity): ProgramPrerequisite {
        return ProgramPrerequisite(
            exerciseId = entity.exerciseId,
            id = entity.id,
            programId = entity.programId,
            requiredValue = entity.requiredValue,
            type = mapPrerequisiteType(entity.type),
        )
    }

    fun map(domain: ProgramPrerequisite): ProgramPrerequisiteEntity {
        return ProgramPrerequisiteEntity(
            exerciseId = domain.exerciseId,
            id = domain.id,
            programId = domain.programId,
            requiredValue = domain.requiredValue,
            type = mapPrerequisiteType(domain.type),
        )
    }

    fun mapProgramCategory(entity: EntityProgramCategory): ProgramCategory {
        return ProgramCategory.valueOf(entity.name)
    }

    fun mapProgramCategory(domain: ProgramCategory): EntityProgramCategory {
        return EntityProgramCategory.valueOf(domain.name)
    }

    private fun mapMicrocycleDayType(entity: EntityMicrocycleDayType): MicrocycleDayType {
        return MicrocycleDayType.valueOf(entity.name)
    }

    private fun mapMicrocycleDayType(domain: MicrocycleDayType): EntityMicrocycleDayType {
        return EntityMicrocycleDayType.valueOf(domain.name)
    }

    private fun mapPrerequisiteType(entity: EntityPrerequisiteType): PrerequisiteType {
        return PrerequisiteType.valueOf(entity.name)
    }

    private fun mapPrerequisiteType(domain: PrerequisiteType): EntityPrerequisiteType {
        return EntityPrerequisiteType.valueOf(domain.name)
    }

    private fun mapRepType(entity: EntityRepType): RepType {
        return RepType.valueOf(entity.name)
    }

    private fun mapRepType(domain: RepType): EntityRepType {
        return EntityRepType.valueOf(domain.name)
    }

    private fun mapSetType(entity: EntitySetType): SetType {
        return SetType.valueOf(entity.name)
    }

    private fun mapSetType(domain: SetType): EntitySetType {
        return EntitySetType.valueOf(domain.name)
    }

    private fun mapWeightType(entity: EntityWeightType): WeightType {
        return WeightType.valueOf(entity.name)
    }

    private fun mapWeightType(domain: WeightType): EntityWeightType {
        return EntityWeightType.valueOf(domain.name)
    }
}
