package com.vasev.trainingapp.feature.anatomy.data.di

import com.vasev.trainingapp.feature.anatomy.data.repository.ExerciseMuscleRepositoryImpl
import com.vasev.trainingapp.feature.anatomy.data.repository.MuscleGroupRepositoryImpl
import com.vasev.trainingapp.feature.anatomy.data.repository.MuscleRelationRepositoryImpl
import com.vasev.trainingapp.feature.anatomy.data.repository.MuscleRepositoryImpl
import com.vasev.trainingapp.feature.anatomy.domain.repository.ExerciseMuscleRepository
import com.vasev.trainingapp.feature.anatomy.domain.repository.MuscleGroupRepository
import com.vasev.trainingapp.feature.anatomy.domain.repository.MuscleRelationRepository
import com.vasev.trainingapp.feature.anatomy.domain.repository.MuscleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interfaces from `feature-anatomy:domain` to their
 * implementations in `feature-anatomy:data`. / Hilt-модуль, который биндит интерфейсы репозиториев
 * из `feature-anatomy:domain` к их реализациям в `feature-anatomy:data`.
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
abstract class AnatomyDataModule {

    @Binds
    abstract fun bindExerciseMuscleRepository(impl: ExerciseMuscleRepositoryImpl): ExerciseMuscleRepository

    @Binds
    abstract fun bindMuscleGroupRepository(impl: MuscleGroupRepositoryImpl): MuscleGroupRepository

    @Binds
    abstract fun bindMuscleRelationRepository(impl: MuscleRelationRepositoryImpl): MuscleRelationRepository

    @Binds
    abstract fun bindMuscleRepository(impl: MuscleRepositoryImpl): MuscleRepository
}
