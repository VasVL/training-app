package com.vasev.trainingapp.feature.anatomy.domain.repository

import com.vasev.trainingapp.feature.anatomy.domain.entity.MuscleRelationEntry

/**
 * Repository (interface) for accessing muscle relations (antagonist/synergist) — the contract the
 * `ui` layer depends on / Репозиторий (интерфейс) доступа к связям между мышцами (антагонист/синергист) —
 * контракт, на который опирается слой `ui`
 *
 * The implementation lives in the `data` module. / Реализация живёт в модуле `data`.
 */
interface MuscleRelationRepository {

    suspend fun getByMuscle(muscleId: Long): List<MuscleRelationEntry>

    suspend fun insert(relation: MuscleRelationEntry)

    suspend fun delete(relation: MuscleRelationEntry)
}
