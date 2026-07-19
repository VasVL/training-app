package com.vasev.trainingapp.feature.workout.data.di

import com.vasev.trainingapp.feature.workout.data.repository.WorkoutLogExerciseRepositoryImpl
import com.vasev.trainingapp.feature.workout.data.repository.WorkoutLogRepositoryImpl
import com.vasev.trainingapp.feature.workout.data.repository.WorkoutLogSetRepositoryImpl
import com.vasev.trainingapp.feature.workout.domain.repository.WorkoutLogExerciseRepository
import com.vasev.trainingapp.feature.workout.domain.repository.WorkoutLogRepository
import com.vasev.trainingapp.feature.workout.domain.repository.WorkoutLogSetRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interfaces from `feature-workout:domain` to their
 * implementations in `feature-workout:data`. / Hilt-модуль, который биндит интерфейсы репозиториев
 * из `feature-workout:domain` к их реализациям в `feature-workout:data`.
 *
 * `@Module` — Hilt module — container for @Binds methods /
 * `@Module` — Модуль Hilt — контейнер для методов @Binds.
 *
 * `@InstallIn(SingletonComponent::class)` — Install in SingletonComponent — lives as long as
 * Application / Установить в SingletonComponent — живёт пока Application.
 *
 * `@Binds` — tells Hilt which implementation to use for a given interface (more efficient than
 * @Provides for interface→impl bindings). / `@Binds` — говорит Hilt, какую реализацию
 * использовать для интерфейса (эффективнее @Provides для биндинга интерфейс→реализация).
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class WorkoutDataModule {

    @Binds
    abstract fun bindWorkoutLogExerciseRepository(impl: WorkoutLogExerciseRepositoryImpl): WorkoutLogExerciseRepository

    @Binds
    abstract fun bindWorkoutLogRepository(impl: WorkoutLogRepositoryImpl): WorkoutLogRepository

    @Binds
    abstract fun bindWorkoutLogSetRepository(impl: WorkoutLogSetRepositoryImpl): WorkoutLogSetRepository
}
