package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.ExerciseSet
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing exercise entries within workout templates — the contract
 * the `ui` layer depends on / Репозиторий (интерфейс) доступа к упражнениям в шаблонах тренировок —
 * контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ExerciseSetRepository {

    fun observeByWorkoutTemplate(workoutTemplateId: Long): Flow<List<ExerciseSet>>

    suspend fun getById(id: Long): ExerciseSet?

    suspend fun getByWorkoutTemplate(workoutTemplateId: Long): List<ExerciseSet>

    suspend fun insert(set: ExerciseSet): Long

    suspend fun update(set: ExerciseSet)

    suspend fun delete(set: ExerciseSet)
}
