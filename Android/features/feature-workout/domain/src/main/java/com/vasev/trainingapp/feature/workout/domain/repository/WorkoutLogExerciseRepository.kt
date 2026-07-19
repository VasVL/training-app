package com.vasev.trainingapp.feature.workout.domain.repository

import com.vasev.trainingapp.feature.workout.domain.entity.WorkoutLogExercise
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing exercise entries within workout logs — the contract the
 * `ui` layer depends on / Репозиторий (интерфейс) доступа к упражнениям в дневнике тренировок —
 * контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface WorkoutLogExerciseRepository {

    fun observeByWorkoutLog(workoutLogId: Long): Flow<List<WorkoutLogExercise>>

    suspend fun getById(id: Long): WorkoutLogExercise?

    suspend fun getByWorkoutLog(workoutLogId: Long): List<WorkoutLogExercise>

    suspend fun insert(exercise: WorkoutLogExercise): Long

    suspend fun update(exercise: WorkoutLogExercise)

    suspend fun delete(exercise: WorkoutLogExercise)
}
