package com.vasev.trainingapp.feature.programs.domain.repository

import com.vasev.trainingapp.feature.programs.domain.entity.ProgramPrerequisite
import kotlinx.coroutines.flow.Flow

/**
 * Repository (interface) for accessing program prerequisites — the contract the `ui` layer
 * depends on / Репозиторий (интерфейс) доступа к условиям программ — контракт, на который
 * опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface ProgramPrerequisiteRepository {

    fun observeByProgram(programId: Long): Flow<List<ProgramPrerequisite>>

    suspend fun getById(id: Long): ProgramPrerequisite?

    suspend fun getByProgram(programId: Long): List<ProgramPrerequisite>

    suspend fun insert(prerequisite: ProgramPrerequisite): Long

    suspend fun update(prerequisite: ProgramPrerequisite)

    suspend fun delete(prerequisite: ProgramPrerequisite)
}
