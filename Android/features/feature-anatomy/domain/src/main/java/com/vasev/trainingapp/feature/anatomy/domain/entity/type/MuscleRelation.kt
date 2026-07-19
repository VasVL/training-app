package com.vasev.trainingapp.feature.anatomy.domain.entity.type

/**
 * Relation between two muscles for the anatomy atlas / Связь между мышцами для анатомического атласа
 *
 * ANTAGONIST — opposite action; SYNERGIST — assists the same movement /
 * ANTAGONIST — противоположное действие; SYNERGIST — помогает тому же движению
 */
enum class MuscleRelation {
    ANTAGONIST,
    SYNERGIST,
}
