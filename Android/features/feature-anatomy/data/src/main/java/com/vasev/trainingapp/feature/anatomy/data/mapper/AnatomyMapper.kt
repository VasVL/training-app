package com.vasev.trainingapp.feature.anatomy.data.mapper

import com.vasev.trainingapp.core.database.entity.ExerciseMuscleEntity
import com.vasev.trainingapp.core.database.entity.MuscleEntity
import com.vasev.trainingapp.core.database.entity.MuscleGroupEntity
import com.vasev.trainingapp.core.database.entity.MuscleRelationEntity
import com.vasev.trainingapp.core.database.entity.types.MuscleInvolvement as EntityMuscleInvolvement
import com.vasev.trainingapp.core.database.entity.types.MuscleRelation as EntityMuscleRelation
import com.vasev.trainingapp.feature.anatomy.domain.entity.ExerciseMuscle
import com.vasev.trainingapp.feature.anatomy.domain.entity.Muscle
import com.vasev.trainingapp.feature.anatomy.domain.entity.MuscleGroup
import com.vasev.trainingapp.feature.anatomy.domain.entity.MuscleRelationEntry
import com.vasev.trainingapp.feature.anatomy.domain.entity.type.MuscleInvolvement
import com.vasev.trainingapp.feature.anatomy.domain.entity.type.MuscleRelation
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entities (Muscle*, MuscleGroup*, ExerciseMuscle*, MuscleRelation*) and
 * domain models. / Маппер между Room-сущностями (Muscle*, MuscleGroup*, ExerciseMuscle*,
 * MuscleRelation*) и domain-моделями.
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class AnatomyMapper @Inject constructor() {

    fun map(entity: MuscleEntity): Muscle {
        return Muscle(
            description = entity.description,
            groupId = entity.groupId,
            id = entity.id,
            imageUrl = entity.imageUrl,
            name = entity.name,
            remoteId = entity.remoteId,
        )
    }

    fun map(domain: Muscle): MuscleEntity {
        return MuscleEntity(
            description = domain.description,
            groupId = domain.groupId,
            id = domain.id,
            imageUrl = domain.imageUrl,
            name = domain.name,
            remoteId = domain.remoteId,
        )
    }

    fun map(entity: MuscleGroupEntity): MuscleGroup {
        return MuscleGroup(
            id = entity.id,
            imageUrl = entity.imageUrl,
            name = entity.name,
            remoteId = entity.remoteId,
        )
    }

    fun map(domain: MuscleGroup): MuscleGroupEntity {
        return MuscleGroupEntity(
            id = domain.id,
            imageUrl = domain.imageUrl,
            name = domain.name,
            remoteId = domain.remoteId,
        )
    }

    fun map(entity: ExerciseMuscleEntity): ExerciseMuscle {
        return ExerciseMuscle(
            exerciseId = entity.exerciseId,
            involvement = mapMuscleInvolvement(entity.involvement),
            muscleId = entity.muscleId,
        )
    }

    fun map(domain: ExerciseMuscle): ExerciseMuscleEntity {
        return ExerciseMuscleEntity(
            exerciseId = domain.exerciseId,
            involvement = mapMuscleInvolvement(domain.involvement),
            muscleId = domain.muscleId,
        )
    }

    fun map(entity: MuscleRelationEntity): MuscleRelationEntry {
        return MuscleRelationEntry(
            muscleId = entity.muscleId,
            relatedMuscleId = entity.relatedMuscleId,
            relation = mapMuscleRelation(entity.relation),
        )
    }

    fun map(domain: MuscleRelationEntry): MuscleRelationEntity {
        return MuscleRelationEntity(
            muscleId = domain.muscleId,
            relatedMuscleId = domain.relatedMuscleId,
            relation = mapMuscleRelation(domain.relation),
        )
    }

    private fun mapMuscleInvolvement(entity: EntityMuscleInvolvement): MuscleInvolvement {
        return MuscleInvolvement.valueOf(entity.name)
    }

    private fun mapMuscleInvolvement(domain: MuscleInvolvement): EntityMuscleInvolvement {
        return EntityMuscleInvolvement.valueOf(domain.name)
    }

    private fun mapMuscleRelation(entity: EntityMuscleRelation): MuscleRelation {
        return MuscleRelation.valueOf(entity.name)
    }

    private fun mapMuscleRelation(domain: MuscleRelation): EntityMuscleRelation {
        return EntityMuscleRelation.valueOf(domain.name)
    }
}
