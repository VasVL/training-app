package com.vasev.trainingapp.feature.exercises.contract

import com.vasev.trainingapp.core.navigation.Screen

/**
 * Screen routes for the exercises feature (exercise list / muscle groups + search).
 * Маршруты экранов фичи exercises (список упражнений / группы мышц + поиск).
 *
 * See APP_DESIGN.md, "Дополнительные экраны" and "Экран групп мышц".
 * См. APP_DESIGN.md, "Дополнительные экраны" и "Экран групп мышц".
 */
sealed interface ExerciseScreen : Screen {

    /**
     * Muscle groups screen: list of groups (back/chest/legs/...).
     * Экран групп мышц: список групп (спина/грудь/ноги/...).
     *
     * Tap on a group navigates to [Exercises] with the group id.
     * Тап по группе ведёт на [Exercises] с id группы.
     */
    data object MuscleGroups : ExerciseScreen

    /**
     * Exercises screen: list of exercises, optionally filtered by muscle group.
     * Экран упражнений: список упражнений, опционально отфильтрованный по группе мышц.
     *
     * @param muscleGroupId id of the muscle group to filter by; `null` for the
     *   general list (all exercises + search). When non-null, the screen shows
     *   exercises of that group (search is scoped to the group/tag).
     *   id группы мышц для фильтрации; `null` для общего списка (все упражнения + поиск).
     *   При ненулевом значении экран показывает упражнения этой группы
     *   (поиск ограничен группой/тегом).
     */
    data class Exercises(
        val muscleGroupId: Long? = null,
    ) : ExerciseScreen
}
