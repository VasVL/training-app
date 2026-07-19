package com.vasev.trainingapp.core.database.entity

/**
 * Built-in program category (stable key, display name comes from strings.xml) /
 * Вшитая категория программы (стабильный ключ, отображаемое имя берётся из strings.xml)
 *
 * Using an enum guarantees that "powerlifting" and "пауэрлифтинг" are the same category /
 * Использование enum гарантирует, что "powerlifting" и "пауэрлифтинг" — одна категория
 */
enum class ProgramCategory {
    ARMLIFTING,
    ARMWRESTLING,
    BODYBUILDING,
    CALISTHENICS,
    CARDIO,
    CROSSFIT,
    ENDURANCE,
    FITNESS,
    FUNCTIONAL,
    MOBILITY,
    OLYMPIC_WEIGHTLIFTING,
    POWERLIFTING,
    REHAB,
    STRENGTH,
    STREETLIFTING,
    STRONGMAN,
    STRETCHING,
    WEIGHTLIFTING,
}
