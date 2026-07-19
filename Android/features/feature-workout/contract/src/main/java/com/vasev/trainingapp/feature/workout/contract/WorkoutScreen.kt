package com.vasev.trainingapp.feature.workout.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the workout feature (workout selection, session, exercise session).
 * Маршруты экранов фичи workout (выбор тренировки, сессия, выполнение упражнения).
 *
 * See APP_DESIGN.md, section "3. Текущая тренировка".
 * См. APP_DESIGN.md, раздел "3. Текущая тренировка".
 */
sealed interface WorkoutScreen : Screen {

    /**
     * Workout selection screen: continue an in-progress workout or pick a
     * planned workout from active programs (or start an empty one).
     * Экран выбора тренировки: продолжить начатую тренировку или выбрать
     * запланированную из активных программ (или начать пустую).
     */
    data object Selection : WorkoutScreen

    /**
     * Workout session screen: performing a workout (exercises, start/finish, results).
     * Экран прохождения тренировки: выполнение тренировки (упражнения, старт/финиш, результаты).
     *
     * @param workoutLogId id of the workout log entry being performed.
     *   id записи дневника тренировки, которая выполняется.
     */
    data class Session(
        val workoutLogId: Long,
    ) : WorkoutScreen

    /**
     * Exercise session screen: performing a single exercise (sets, statuses, history).
     * Экран выполнения упражнения: выполнение одного упражнения (подходы, статусы, история).
     *
     * @param workoutLogExerciseId id of the workout log exercise entry being performed.
     *   id записи упражнения в дневнике, которое выполняется.
     */
    data class ExerciseSession(
        val workoutLogExerciseId: Long,
    ) : WorkoutScreen
}
