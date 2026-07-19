package com.vasev.trainingapp.core.database.entity.types

/**
 * Relation between two muscles for the anatomy atlas / Связь между двумя мышцами для анатомического атласа
 *
 * ANTAGONIST — muscles performing the opposite action; SYNERGIST — muscles assisting the same movement /
 * ANTAGONIST — мышцы, выполняющие противоположное действие; SYNERGIST — мышцы, помогающие тому же движению
 */
enum class MuscleRelation {
    ANTAGONIST,
    SYNERGIST,
}
