package com.vasev.trainingapp.feature.auth.domain.entity.type

/**
 * Measurement unit for one-rep-max and other metrics / Единица измерения для разового максимума и других метрик
 *
 * Includes both small and large distance units: CM for jumps, METERS for long distances /
 * Включает единицы расстояния для малых и больших значений: CM для прыжков, METERS для длинных
 */
enum class MeasurementUnit {
    CM,
    KG,
    LBS,
    METERS,
    REPS,
    SECONDS,
}
