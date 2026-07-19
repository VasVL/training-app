package com.vasev.trainingapp.feature.anatomy.domain.repository

import com.vasev.trainingapp.feature.anatomy.domain.entity.ExerciseMuscle

/**
 * Repository (interface) for accessing exercise-muscle links — the contract the `ui` layer
 * depends on / Репозиторий (интерфейс) доступа к связям упражнений и мышц — контракт, на который
 * опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ExerciseMuscleRepository {

    suspend fun getByExercise(exerciseId: Long): List<ExerciseMuscle>

    suspend fun getByMuscle(muscleId: Long): List<ExerciseMuscle>

    suspend fun insert(link: ExerciseMuscle)

    suspend fun delete(link: ExerciseMuscle)
}
