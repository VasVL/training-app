package com.vasev.trainingapp.feature.programs.data.di

import com.vasev.trainingapp.feature.programs.data.repository.ExerciseSetRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.MicrocycleDayRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.MicrocycleRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.ProgramCategoryRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.ProgramPrerequisiteRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.ProgramRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.ProgramTagRepositoryImpl
import com.vasev.trainingapp.feature.programs.data.repository.SetTemplateRepositoryImpl
import com.vasev.trainingapp.feature.programs.domain.repository.ExerciseSetRepository
import com.vasev.trainingapp.feature.programs.domain.repository.MicrocycleDayRepository
import com.vasev.trainingapp.feature.programs.domain.repository.MicrocycleRepository
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramCategoryRepository
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramPrerequisiteRepository
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramRepository
import com.vasev.trainingapp.feature.programs.domain.repository.ProgramTagRepository
import com.vasev.trainingapp.feature.programs.domain.repository.SetTemplateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interfaces from `feature-programs:domain` to their
 * implementations in `feature-programs:data`. / Hilt-модуль, который биндит интерфейсы репозиториев
 * из `feature-programs:domain` к их реализациям в `feature-programs:data`.
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
abstract class ProgramsDataModule {

    @Binds
    abstract fun bindExerciseSetRepository(impl: ExerciseSetRepositoryImpl): ExerciseSetRepository

    @Binds
    abstract fun bindMicrocycleDayRepository(impl: MicrocycleDayRepositoryImpl): MicrocycleDayRepository

    @Binds
    abstract fun bindMicrocycleRepository(impl: MicrocycleRepositoryImpl): MicrocycleRepository

    @Binds
    abstract fun bindProgramCategoryRepository(impl: ProgramCategoryRepositoryImpl): ProgramCategoryRepository

    @Binds
    abstract fun bindProgramPrerequisiteRepository(impl: ProgramPrerequisiteRepositoryImpl): ProgramPrerequisiteRepository

    @Binds
    abstract fun bindProgramRepository(impl: ProgramRepositoryImpl): ProgramRepository

    @Binds
    abstract fun bindProgramTagRepository(impl: ProgramTagRepositoryImpl): ProgramTagRepository

    @Binds
    abstract fun bindSetTemplateRepository(impl: SetTemplateRepositoryImpl): SetTemplateRepository
}
