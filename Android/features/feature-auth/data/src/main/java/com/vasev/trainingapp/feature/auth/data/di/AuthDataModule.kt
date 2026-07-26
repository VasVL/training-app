package com.vasev.trainingapp.feature.auth.data.di

import com.vasev.trainingapp.feature.auth.data.repository.UserMaxRepositoryImpl
import com.vasev.trainingapp.feature.auth.data.repository.UserRepositoryImpl
import com.vasev.trainingapp.feature.auth.domain.repository.UserMaxRepository
import com.vasev.trainingapp.feature.auth.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt module that binds the repository interfaces from `feature-auth:domain` to their
 * implementations in `feature-auth:data`. / Hilt-модуль, который биндит интерфейсы репозиториев
 * из `feature-auth:domain` к их реализациям в `feature-auth:data`.
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
abstract class AuthDataModule {

    @Binds
    abstract fun bindUserMaxRepository(impl: UserMaxRepositoryImpl): UserMaxRepository

    @Binds
    internal abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
