package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.ProgramCategoryEntry
import com.vasev.trainingapp.feature.programs.domain.entity.type.ProgramCategory
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing built-in program category tags — the contract the `ui`
 * layer depends on / Репозиторий (интерфейс) доступа к вшитым категориям программ — контракт, на
 * который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ProgramCategoryRepository {

    fun observeByProgram(programId: Long): Flow<List<ProgramCategoryEntry>>

    suspend fun getByProgram(programId: Long): List<ProgramCategoryEntry>

    suspend fun getByCategory(category: ProgramCategory): List<ProgramCategoryEntry>

    suspend fun insert(category: ProgramCategoryEntry)

    suspend fun delete(category: ProgramCategoryEntry)
}
