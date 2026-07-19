package com.vasev.trainingapp.feature.workout.domain.repository

import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLogSet
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing individual sets within workout log exercises — the contract
 * the `ui` layer depends on / Репозиторий (интерфейс) доступа к подходам в дневнике тренировок —
 * контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface WorkoutLogSetRepository {

    fun observeByWorkoutLogExercise(workoutLogExerciseId: Long): Flow<List<WorkoutLogSet>>

    suspend fun getById(id: Long): WorkoutLogSet?

    suspend fun getByWorkoutLogExercise(workoutLogExerciseId: Long): List<WorkoutLogSet>

    suspend fun insert(set: WorkoutLogSet): Long

    suspend fun update(set: WorkoutLogSet)

    suspend fun delete(set: WorkoutLogSet)
}
