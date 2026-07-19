package com.vasev.trainingapp.feature.exercises.data.di

import com.vasev.trainingapp.feature.exercises.data.repository.ExerciseRepositoryImpl
import com.vasev.trainingapp.feature.exercises.domain.repository.ExerciseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interface from `feature-exercises:domain` to its
 * implementation in `feature-exercises:data`. / Hilt-модуль, который биндит интерфейс репозитория
 * из `feature-exercises:domain` к его реализации в `feature-exercises:data`.
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
abstract class ExercisesDataModule {

    @Binds
    abstract fun bindExerciseRepository(impl: ExerciseRepositoryImpl): ExerciseRepository
}
