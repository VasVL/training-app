package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.ProgramTag
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing user-defined program tags — the contract the `ui` layer
 * depends on / Репозиторий (интерфейс) доступа к пользовательским тегам программ — контракт, на
 * который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ProgramTagRepository {

    fun observeByProgram(programId: Long): Flow<List<ProgramTag>>

    suspend fun getByProgram(programId: Long): List<ProgramTag>

    suspend fun getByTag(tag: String): List<ProgramTag>

    suspend fun insert(tag: ProgramTag)

    suspend fun delete(tag: ProgramTag)
}
