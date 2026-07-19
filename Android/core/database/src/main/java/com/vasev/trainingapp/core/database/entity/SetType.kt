package com.vasev.trainingapp.core.database.entity

/**
 * Set type: single exercise, superset (multiple exercises per round) or dropset (weight reduction) / Тип подхода: одиночное, суперсет (несколько упражнений по кругу) или дропсет (сброс веса)
 */
enum class SetType {
    DROPSET,
    SINGLE,
    SUPERSET,
}
