package com.vasev.trainingapp.feature.exercises.data.mapper

import com.vasev.trainingapp.core.database.entity.ExerciseEntity
import com.vasev.trainingapp.core.database.entity.types.ExerciseType as EntityExerciseType
import com.vasev.trainingapp.feature.exercises.domain.entity.Exercise
import com.vasev.trainingapp.feature.exercises.domain.entity.type.ExerciseType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entity [ExerciseEntity] and domain model [Exercise]. /
 * Маппер между Room-сущностью [ExerciseEntity] и domain-моделью [Exercise].
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class ExercisesMapper @Inject constructor() {

    fun map(entity: ExerciseEntity): Exercise {
        return Exercise(
            createdByUserId = entity.createdByUserId,
            description = entity.description,
            id = entity.id,
            imageUrl = entity.imageUrl,
            isBuiltin = entity.isBuiltin,
            isDeleted = entity.isDeleted,
            name = entity.name,
            remoteId = entity.remoteId,
            type = mapExerciseType(entity.type),
        )
    }

    fun map(domain: Exercise): ExerciseEntity {
        return ExerciseEntity(
            createdByUserId = domain.createdByUserId,
            description = domain.description,
            id = domain.id,
            imageUrl = domain.imageUrl,
            isBuiltin = domain.isBuiltin,
            isDeleted = domain.isDeleted,
            name = domain.name,
            remoteId = domain.remoteId,
            type = mapExerciseType(domain.type),
        )
    }

    private fun mapExerciseType(entity: EntityExerciseType): ExerciseType {
        return ExerciseType.valueOf(entity.name)
    }

    private fun mapExerciseType(domain: ExerciseType): EntityExerciseType {
        return EntityExerciseType.valueOf(domain.name)
    }
}
