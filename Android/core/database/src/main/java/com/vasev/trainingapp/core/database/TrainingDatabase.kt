package com.vasev.trainingapp.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vasev.trainingapp.core.database.dao.ExerciseDao
import com.vasev.trainingapp.core.database.dao.ExerciseMuscleDao
import com.vasev.trainingapp.core.database.dao.ExerciseSetDao
import com.vasev.trainingapp.core.database.dao.FoodItemDao
import com.vasev.trainingapp.core.database.dao.FoodLogDao
import com.vasev.trainingapp.core.database.dao.MicrocycleDao
import com.vasev.trainingapp.core.database.dao.MicrocycleDayDao
import com.vasev.trainingapp.core.database.dao.MuscleDao
import com.vasev.trainingapp.core.database.dao.MuscleGroupDao
import com.vasev.trainingapp.core.database.dao.MuscleRelationDao
import com.vasev.trainingapp.core.database.dao.ProgramCategoryDao
import com.vasev.trainingapp.core.database.dao.ProgramDao
import com.vasev.trainingapp.core.database.dao.ProgramPrerequisiteDao
import com.vasev.trainingapp.core.database.dao.ProgramTagDao
import com.vasev.trainingapp.core.database.dao.SetTemplateDao
import com.vasev.trainingapp.core.database.dao.UserDao
import com.vasev.trainingapp.core.database.dao.UserMaxDao
import com.vasev.trainingapp.core.database.dao.WeightMeasurementDao
import com.vasev.trainingapp.core.database.dao.WorkoutLogDao
import com.vasev.trainingapp.core.database.dao.WorkoutLogExerciseDao
import com.vasev.trainingapp.core.database.dao.WorkoutLogSetDao
import com.vasev.trainingapp.core.database.dao.WorkoutTemplateDao
import com.vasev.trainingapp.core.database.entity.ExerciseEntity
import com.vasev.trainingapp.core.database.entity.ExerciseMuscleEntity
import com.vasev.trainingapp.core.database.entity.ExerciseSetEntity
import com.vasev.trainingapp.core.database.entity.FoodItemEntity
import com.vasev.trainingapp.core.database.entity.FoodLogEntity
import com.vasev.trainingapp.core.database.entity.MicrocycleDayEntity
import com.vasev.trainingapp.core.database.entity.MicrocycleEntity
import com.vasev.trainingapp.core.database.entity.MuscleEntity
import com.vasev.trainingapp.core.database.entity.MuscleGroupEntity
import com.vasev.trainingapp.core.database.entity.MuscleRelationEntity
import com.vasev.trainingapp.core.database.entity.ProgramCategoryEntity
import com.vasev.trainingapp.core.database.entity.ProgramEntity
import com.vasev.trainingapp.core.database.entity.ProgramPrerequisiteEntity
import com.vasev.trainingapp.core.database.entity.ProgramTagEntity
import com.vasev.trainingapp.core.database.entity.SetTemplateEntity
import com.vasev.trainingapp.core.database.entity.UserEntity
import com.vasev.trainingapp.core.database.entity.UserMaxEntity
import com.vasev.trainingapp.core.database.entity.WeightMeasurementEntity
import com.vasev.trainingapp.core.database.entity.WorkoutLogEntity
import com.vasev.trainingapp.core.database.entity.WorkoutLogExerciseEntity
import com.vasev.trainingapp.core.database.entity.WorkoutLogSetEntity
import com.vasev.trainingapp.core.database.entity.WorkoutTemplateEntity

/**
 * Single Room database for the whole TrainingApp app / Единая Room-база для всего приложения TrainingApp
 *
 * `@Database` — marks this abstract class as a Room database; lists all entities and DAOs /
 * `@Database` — помечает абстрактный класс как Room-базу; перечисляет все Entity и DAO
 *
 * `version = 1` — initial schema version; `exportSchema = true` — exports JSON schema to `schemas/` /
 * `version = 1` — начальная версия схемы; `exportSchema = true` — экспортирует JSON-схему в `schemas/`
 *
 * `@TypeConverters(Converters::class)` — registers enum converters for all entities /
 * `@TypeConverters(Converters::class)` — регистрирует конвертеры enum-ов для всех Entity
 */
@Database(
    entities = [
        ExerciseEntity::class,
        ExerciseMuscleEntity::class,
        ExerciseSetEntity::class,
        FoodItemEntity::class,
        FoodLogEntity::class,
        MicrocycleDayEntity::class,
        MicrocycleEntity::class,
        MuscleEntity::class,
        MuscleGroupEntity::class,
        MuscleRelationEntity::class,
        ProgramCategoryEntity::class,
        ProgramEntity::class,
        ProgramPrerequisiteEntity::class,
        ProgramTagEntity::class,
        SetTemplateEntity::class,
        UserEntity::class,
        UserMaxEntity::class,
        WeightMeasurementEntity::class,
        WorkoutLogEntity::class,
        WorkoutLogExerciseEntity::class,
        WorkoutLogSetEntity::class,
        WorkoutTemplateEntity::class,
    ],
    exportSchema = true,
    version = 1,
)
@TypeConverters(Converters::class)
internal abstract class TrainingDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    abstract fun exerciseMuscleDao(): ExerciseMuscleDao

    abstract fun exerciseSetDao(): ExerciseSetDao

    abstract fun foodItemDao(): FoodItemDao

    abstract fun foodLogDao(): FoodLogDao

    abstract fun microcycleDao(): MicrocycleDao

    abstract fun microcycleDayDao(): MicrocycleDayDao

    abstract fun muscleDao(): MuscleDao

    abstract fun muscleGroupDao(): MuscleGroupDao

    abstract fun muscleRelationDao(): MuscleRelationDao

    abstract fun programCategoryDao(): ProgramCategoryDao

    abstract fun programDao(): ProgramDao

    abstract fun programPrerequisiteDao(): ProgramPrerequisiteDao

    abstract fun programTagDao(): ProgramTagDao

    abstract fun setTemplateDao(): SetTemplateDao

    abstract fun userDao(): UserDao

    abstract fun userMaxDao(): UserMaxDao

    abstract fun weightMeasurementDao(): WeightMeasurementDao

    abstract fun workoutLogDao(): WorkoutLogDao

    abstract fun workoutLogExerciseDao(): WorkoutLogExerciseDao

    abstract fun workoutLogSetDao(): WorkoutLogSetDao

    abstract fun workoutTemplateDao(): WorkoutTemplateDao
}
