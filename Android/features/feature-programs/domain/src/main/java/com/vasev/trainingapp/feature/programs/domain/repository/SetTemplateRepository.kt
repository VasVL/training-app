package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.SetTemplate
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing set templates within exercise entries — the contract the
 * `ui` layer depends on / Репозиторий (интерфейс) доступа к шаблонам подходов внутри упражнений —
 * контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface SetTemplateRepository {

    fun observeByExerciseSet(exerciseSetId: Long): Flow<List<SetTemplate>>

    suspend fun getById(id: Long): SetTemplate?

    suspend fun getByExerciseSet(exerciseSetId: Long): List<SetTemplate>

    suspend fun insert(template: SetTemplate): Long

    suspend fun update(template: SetTemplate)

    suspend fun delete(template: SetTemplate)
}
