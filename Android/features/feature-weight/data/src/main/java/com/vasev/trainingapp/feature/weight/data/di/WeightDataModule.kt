package com.vasev.trainingapp.feature.weight.data.di

import com.vasev.trainingapp.feature.weight.data.repository.WeightRepositoryImpl
import com.vasev.trainingapp.feature.weight.domain.repository.WeightRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interface from `feature-weight:domain` to its
 * implementation in `feature-weight:data`. / Hilt-модуль, который биндит интерфейс репозитория
 * из `feature-weight:domain` к его реализации в `feature-weight:data`.
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
abstract class WeightDataModule {

    @Binds
    abstract fun bindWeightRepository(impl: WeightRepositoryImpl): WeightRepository
}
