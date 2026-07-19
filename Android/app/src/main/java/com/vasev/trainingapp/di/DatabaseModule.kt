package com.vasev.trainingapp.di

import android.content.Context
import androidx.room.Room
import com.vasev.trainingapp.core.database.TrainingDatabase
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides the Room database and all DAOs to the rest of the app.
 * Hilt-модуль, который предоставляет Room-базу и все DAO остальному приложению.
 *
 * `@Module` — Hilt module — container for @Provides methods /
 * `@Module` — Модуль Hilt — контейнер для методов @Provides.
 *
 * `@InstallIn(SingletonComponent::class)` — Install in SingletonComponent — lives as long as
 * Application / Установить в SingletonComponent — живёт пока Application.
 *
 * SingletonComponent is the root Hilt component tied to the Application lifecycle.
 * Everything provided here is a singleton available to any `@Inject` site in the app.
 * SingletonComponent — корневой Hilt-компонент, привязанный к жизненному циклу Application.
 * Всё, что предоставляется здесь — синглтон, доступный в любом месте `@Inject` в приложении.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Database file name used by Room / Имя файла базы данных, используемое Room
    private const val DATABASE_NAME = "training.db"

    /**
     * Provides the single Room database instance for the whole app.
     * Предоставляет единственный экземпляр Room-базы для всего приложения.
     *
     * `@Singleton` — Single instance for whole app /
     * `@Singleton` — Единственный экземпляр для всего приложения.
     *
     * Room.databaseBuilder builds the database; `fallbackToDestructiveMigration()` drops and
     * recreates the DB if a migration is missing (acceptable during early development; replace
     * with explicit migrations before release).
     * Room.databaseBuilder строит базу; `fallbackToDestructiveMigration()` пересоздаёт БД,
     * если миграция отсутствует (приемлемо на ранней стадии; замените на явные миграции до релиза).
     */
    @Provides
    @Singleton
    fun provideTrainingDatabase(@ApplicationContext context: Context): TrainingDatabase =
        Room.databaseBuilder(
            context,
            TrainingDatabase::class.java,
            DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()

    /**
     * Provide DAO from database / Предоставить DAO из базы.
     *
     * Each `@Provides` method below asks Hilt for the [TrainingDatabase] (resolved via the
     * method above) and returns the corresponding DAO. Hilt caches the database singleton,
     * so all DAOs share the same DB instance.
     * Каждый метод `@Provides` ниже просит Hilt дать [TrainingDatabase] (разрешается методом выше)
     * и возвращает соответствующий DAO. Hilt кэширует синглтон базы, поэтому все DAO
     * используют один и тот же экземпляр БД.
     */
    @Provides
    fun provideExerciseDao(db: TrainingDatabase): ExerciseDao = db.exerciseDao()

    @Provides
    fun provideExerciseMuscleDao(db: TrainingDatabase): ExerciseMuscleDao = db.exerciseMuscleDao()

    @Provides
    fun provideExerciseSetDao(db: TrainingDatabase): ExerciseSetDao = db.exerciseSetDao()

    @Provides
    fun provideFoodItemDao(db: TrainingDatabase): FoodItemDao = db.foodItemDao()

    @Provides
    fun provideFoodLogDao(db: TrainingDatabase): FoodLogDao = db.foodLogDao()

    @Provides
    fun provideMicrocycleDao(db: TrainingDatabase): MicrocycleDao = db.microcycleDao()

    @Provides
    fun provideMicrocycleDayDao(db: TrainingDatabase): MicrocycleDayDao = db.microcycleDayDao()

    @Provides
    fun provideMuscleDao(db: TrainingDatabase): MuscleDao = db.muscleDao()

    @Provides
    fun provideMuscleGroupDao(db: TrainingDatabase): MuscleGroupDao = db.muscleGroupDao()

    @Provides
    fun provideMuscleRelationDao(db: TrainingDatabase): MuscleRelationDao = db.muscleRelationDao()

    @Provides
    fun provideProgramCategoryDao(db: TrainingDatabase): ProgramCategoryDao =
        db.programCategoryDao()

    @Provides
    fun provideProgramDao(db: TrainingDatabase): ProgramDao = db.programDao()

    @Provides
    fun provideProgramPrerequisiteDao(db: TrainingDatabase): ProgramPrerequisiteDao =
        db.programPrerequisiteDao()

    @Provides
    fun provideProgramTagDao(db: TrainingDatabase): ProgramTagDao = db.programTagDao()

    @Provides
    fun provideSetTemplateDao(db: TrainingDatabase): SetTemplateDao = db.setTemplateDao()

    @Provides
    fun provideUserDao(db: TrainingDatabase): UserDao = db.userDao()

    @Provides
    fun provideUserMaxDao(db: TrainingDatabase): UserMaxDao = db.userMaxDao()

    @Provides
    fun provideWeightMeasurementDao(db: TrainingDatabase): WeightMeasurementDao =
        db.weightMeasurementDao()

    @Provides
    fun provideWorkoutLogDao(db: TrainingDatabase): WorkoutLogDao = db.workoutLogDao()

    @Provides
    fun provideWorkoutLogExerciseDao(db: TrainingDatabase): WorkoutLogExerciseDao =
        db.workoutLogExerciseDao()

    @Provides
    fun provideWorkoutLogSetDao(db: TrainingDatabase): WorkoutLogSetDao = db.workoutLogSetDao()

    @Provides
    fun provideWorkoutTemplateDao(db: TrainingDatabase): WorkoutTemplateDao =
        db.workoutTemplateDao()
}
