package com.vasev.trainingapp.feature.programs.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the programs feature (programs, microcycles, days, exercises).
 * Маршруты экранов фичи programs (программы, микроциклы, дни, упражнения).
 *
 * See APP_DESIGN.md, sections "1. Список программ", "2. Создание программы"
 * and the editing screens (program / microcycle / day / exercise).
 * См. APP_DESIGN.md, разделы "1. Список программ", "2. Создание программы"
 * и экраны редактирования (программа / микроцикл / день / упражнение).
 */
sealed interface ProgramScreen : Screen {

    /**
     * Programs list screen (tabs by categories, pagination, filters, search).
     * Экран списка программ (табы по категориям, пагинация, фильтры, поиск).
     *
     * The active tab (category/tag) is UI state of the screen, not a route
     * parameter: it is restored from the screen's saved state / UiState on
     * back-stack return, not via navigation arguments.
     * Активный таб (категория/тег) — это состояние UI экрана, а не параметр
     * маршрута: он восстанавливается из сохранённого состояния экрана / UiState
     * при возврате по back stack, а не через аргументы навигации.
     */
    data object Programs : ProgramScreen

    /**
     * Program details screen (description, microcycles ViewPager, "Start" button).
     * Экран описания программы (описание, микроциклы ViewPager, кнопка "Начать").
     *
     * @param programId id of the program to show.
     *   id программы для отображения.
     */
    data class ProgramDetails(
        val programId: Long,
    ) : ProgramScreen

    /**
     * Program edit screen (create / edit). Same screen in two modes.
     * Экран редактирования программы (создание / редактирование). Один экран в двух режимах.
     *
     * @param programId id of the program to edit; `null` for create mode.
     *   id программы для редактирования; `null` для режима создания.
     */
    data class Edit(
        val programId: Long? = null,
    ) : ProgramScreen

    /**
     * Microcycle details screen (list of days: workout / rest).
     * Экран описания микроцикла (список дней: тренировочные / дни отдыха).
     *
     * @param microcycleId id of the microcycle to show.
     *   id микроцикла для отображения.
     */
    data class MicrocycleDetails(
        val microcycleId: Long,
    ) : ProgramScreen

    /**
     * Microcycle edit screen (create / edit): list of days, reorder, add day.
     * Экран редактирования микроцикла (создание / редактирование): список дней, перестановка, добавление дня.
     *
     * @param microcycleId id of the microcycle to edit; `null` for create mode.
     *   id микроцикла для редактирования; `null` для режима создания.
     */
    data class MicrocycleEdit(
        val microcycleId: Long? = null,
    ) : ProgramScreen

    /**
     * Day edit screen (one workout): list of exercises, reorder, add exercise.
     * Экран редактирования дня (одна тренировка): список упражнений, перестановка, добавление упражнения.
     *
     * @param microcycleDayId id of the day to edit; `null` for create mode.
     *   id дня для редактирования; `null` для режима создания.
     */
    data class DayEdit(
        val microcycleDayId: Long? = null,
    ) : ProgramScreen

    /**
     * Exercise edit screen (exercise / superset): choose exercise, sets, weight, reps.
     * Экран редактирования упражнения (упражнение / суперсет): выбор упражнения, подходы, вес, повторы.
     *
     * @param exerciseSetId id of the exercise set to edit; `null` for create mode.
     *   id набора упражнений для редактирования; `null` для режима создания.
     */
    data class ExerciseEdit(
        val exerciseSetId: Long? = null,
    ) : ProgramScreen
}
