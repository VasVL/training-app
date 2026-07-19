package com.vasev.trainingapp.core.database.entity

/**
 * Measurement unit for one-rep-max and other metrics / Единица измерения для разового максимума и других метрик
 *
 * Includes both small and large distance units: CM for jumps, METERS for long distances (marathon, etc.) /
 * Включает единицы расстояния для малых и больших значений: CM для прыжков, METERS для длинных дистанций (марафон и т.д.)
 */
enum class MeasurementUnit {
    CM,
    KG,
    LBS,
    METERS,
    REPS,
    SECONDS,
}
