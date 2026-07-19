package com.vasev.trainingapp.feature.workout.data.mapper

import com.vasev.trainingapp.core.database.entity.WorkoutLogEntity
import com.vasev.trainingapp.core.database.entity.WorkoutLogExerciseEntity
import com.vasev.trainingapp.core.database.entity.WorkoutLogSetEntity
import com.vasev.trainingapp.core.database.entity.types.SetType as EntitySetType
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogSetStatus as EntityWorkoutLogSetStatus
import com.vasev.trainingapp.core.database.entity.types.WorkoutLogStatus as EntityWorkoutLogStatus
import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLog
import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLogExercise
import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLogSet
import com.vasev.trainingapp.feature.workout.domain.entity.type.SetType
import com.vasev.trainingapp.feature.workout.domain.entity.type.WorkoutLogSetStatus
import com.vasev.trainingapp.feature.workout.domain.entity.type.WorkoutLogStatus
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entities (WorkoutLog*, WorkoutLogExercise*, WorkoutLogSet*) and domain
 * models. / Маппер между Room-сущностями (WorkoutLog*, WorkoutLogExercise*, WorkoutLogSet*) и
 * domain-моделями.
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class WorkoutMapper @Inject constructor() {

    fun map(entity: WorkoutLogEntity): WorkoutLog {
        return WorkoutLog(
            adjustmentPercent = entity.adjustmentPercent,
            comment = entity.comment,
            completedAt = entity.completedAt,
            dayId = entity.dayId,
            id = entity.id,
            microcycleId = entity.microcycleId,
            programId = entity.programId,
            scheduledDate = entity.scheduledDate,
            startedAt = entity.startedAt,
            status = mapWorkoutLogStatus(entity.status),
            title = entity.title,
            userId = entity.userId,
        )
    }

    fun map(domain: WorkoutLog): WorkoutLogEntity {
        return WorkoutLogEntity(
            adjustmentPercent = domain.adjustmentPercent,
            comment = domain.comment,
            completedAt = domain.completedAt,
            dayId = domain.dayId,
            id = domain.id,
            microcycleId = domain.microcycleId,
            programId = domain.programId,
            scheduledDate = domain.scheduledDate,
            startedAt = domain.startedAt,
            status = mapWorkoutLogStatus(domain.status),
            title = domain.title,
            userId = domain.userId,
        )
    }

    fun map(entity: WorkoutLogExerciseEntity): WorkoutLogExercise {
        return WorkoutLogExercise(
            durationSeconds = entity.durationSeconds,
            exerciseId = entity.exerciseId,
            id = entity.id,
            isSkipped = entity.isSkipped,
            order = entity.order,
            setType = mapSetType(entity.setType),
            supersetGroupId = entity.supersetGroupId,
            workoutLogId = entity.workoutLogId,
        )
    }

    fun map(domain: WorkoutLogExercise): WorkoutLogExerciseEntity {
        return WorkoutLogExerciseEntity(
            durationSeconds = domain.durationSeconds,
            exerciseId = domain.exerciseId,
            id = domain.id,
            isSkipped = domain.isSkipped,
            order = domain.order,
            setType = mapSetType(domain.setType),
            supersetGroupId = domain.supersetGroupId,
            workoutLogId = domain.workoutLogId,
        )
    }

    fun map(entity: WorkoutLogSetEntity): WorkoutLogSet {
        return WorkoutLogSet(
            actualReps = entity.actualReps,
            actualWeight = entity.actualWeight,
            comment = entity.comment,
            id = entity.id,
            order = entity.order,
            plannedReps = entity.plannedReps,
            plannedWeight = entity.plannedWeight,
            restTimeSeconds = entity.restTimeSeconds,
            status = mapWorkoutLogSetStatus(entity.status),
            workoutLogExerciseId = entity.workoutLogExerciseId,
        )
    }

    fun map(domain: WorkoutLogSet): WorkoutLogSetEntity {
        return WorkoutLogSetEntity(
            actualReps = domain.actualReps,
            actualWeight = domain.actualWeight,
            comment = domain.comment,
            id = domain.id,
            order = domain.order,
            plannedReps = domain.plannedReps,
            plannedWeight = domain.plannedWeight,
            restTimeSeconds = domain.restTimeSeconds,
            status = mapWorkoutLogSetStatus(domain.status),
            workoutLogExerciseId = domain.workoutLogExerciseId,
        )
    }

    fun mapWorkoutLogStatus(entity: EntityWorkoutLogStatus): WorkoutLogStatus {
        return WorkoutLogStatus.valueOf(entity.name)
    }

    fun mapWorkoutLogStatus(domain: WorkoutLogStatus): EntityWorkoutLogStatus {
        return EntityWorkoutLogStatus.valueOf(domain.name)
    }

    private fun mapSetType(entity: EntitySetType): SetType {
        return SetType.valueOf(entity.name)
    }

    private fun mapSetType(domain: SetType): EntitySetType {
        return EntitySetType.valueOf(domain.name)
    }

    private fun mapWorkoutLogSetStatus(entity: EntityWorkoutLogSetStatus): WorkoutLogSetStatus {
        return WorkoutLogSetStatus.valueOf(entity.name)
    }

    private fun mapWorkoutLogSetStatus(domain: WorkoutLogSetStatus): EntityWorkoutLogSetStatus {
        return EntityWorkoutLogSetStatus.valueOf(domain.name)
    }
}
