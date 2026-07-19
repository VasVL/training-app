package com.vasev.trainingapp.feature.nutrition.data.di

import com.vasev.trainingapp.feature.nutrition.data.repository.FoodItemRepositoryImpl
import com.vasev.trainingapp.feature.nutrition.data.repository.FoodLogRepositoryImpl
import com.vasev.trainingapp.feature.nutrition.domain.repository.FoodItemRepository
import com.vasev.trainingapp.feature.nutrition.domain.repository.FoodLogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interfaces from `feature-nutrition:domain` to their
 * implementations in `feature-nutrition:data`. / Hilt-модуль, который биндит интерфейсы
 * репозиториев из `feature-nutrition:domain` к их реализациям в `feature-nutrition:data`.
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
abstract class NutritionDataModule {

    @Binds
    abstract fun bindFoodItemRepository(impl: FoodItemRepositoryImpl): FoodItemRepository

    @Binds
    abstract fun bindFoodLogRepository(impl: FoodLogRepositoryImpl): FoodLogRepository
}
