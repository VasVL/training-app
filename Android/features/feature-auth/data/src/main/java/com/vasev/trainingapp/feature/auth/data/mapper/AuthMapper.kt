package com.vasev.trainingapp.feature.auth.data.mapper

import com.vasev.trainingapp.core.database.entity.UserEntity
import com.vasev.trainingapp.core.database.entity.UserMaxEntity
import com.vasev.trainingapp.core.database.entity.types.Gender as EntityGender
import com.vasev.trainingapp.core.database.entity.types.HeightUnit as EntityHeightUnit
import com.vasev.trainingapp.core.database.entity.types.MeasurementUnit as EntityMeasurementUnit
import com.vasev.trainingapp.core.database.entity.types.UserRole as EntityUserRole
import com.vasev.trainingapp.core.database.entity.types.WeightUnit as EntityWeightUnit
import com.vasev.trainingapp.feature.auth.domain.entity.User
import com.vasev.trainingapp.feature.auth.domain.entity.UserMax
import com.vasev.trainingapp.feature.auth.domain.entity.type.Gender
import com.vasev.trainingapp.feature.auth.domain.entity.type.HeightUnit
import com.vasev.trainingapp.feature.auth.domain.entity.type.MeasurementUnit
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import com.vasev.trainingapp.feature.auth.domain.entity.type.WeightUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper between Room entities ([UserEntity], [UserMaxEntity]) and domain models
 * ([User], [UserMax]). / Маппер между Room-сущностями ([UserEntity], [UserMaxEntity]) и
 * domain-моделями ([User], [UserMax]).
 *
 * `@Singleton` — stateless mapper, one instance for the whole app /
 * `@Singleton` — маппер без состояния, один экземпляр на всё приложение
 *
 * `@Inject` — Hilt creates and injects this class where needed /
 * `@Inject` — Hilt создаёт и внедряет этот класс там, где нужно
 */
@Singleton
class AuthMapper @Inject constructor() {

    fun map(entity: UserEntity): User {
        return User(
            age = entity.age,
            createdAt = entity.createdAt,
            gender = mapGender(entity.gender),
            height = entity.height,
            heightUnit = mapHeightUnit(entity.heightUnit),
            id = entity.id,
            isDefault = entity.isDefault,
            name = entity.name,
            remoteId = entity.remoteId,
            role = mapUserRole(entity.role),
            weight = entity.weight,
            weightUnit = mapWeightUnit(entity.weightUnit),
        )
    }

    fun map(domain: User): UserEntity {
        return UserEntity(
            age = domain.age,
            createdAt = domain.createdAt,
            gender = mapGender(domain.gender),
            height = domain.height,
            heightUnit = mapHeightUnit(domain.heightUnit),
            id = domain.id,
            isDefault = domain.isDefault,
            name = domain.name,
            remoteId = domain.remoteId,
            role = mapUserRole(domain.role),
            weight = domain.weight,
            weightUnit = mapWeightUnit(domain.weightUnit),
        )
    }

    fun map(entity: UserMaxEntity): UserMax {
        return UserMax(
            exerciseId = entity.exerciseId,
            id = entity.id,
            maxValue = entity.maxValue,
            measuredAt = entity.measuredAt,
            unit = mapMeasurementUnit(entity.unit),
            userId = entity.userId,
        )
    }

    fun map(domain: UserMax): UserMaxEntity {
        return UserMaxEntity(
            exerciseId = domain.exerciseId,
            id = domain.id,
            maxValue = domain.maxValue,
            measuredAt = domain.measuredAt,
            unit = mapMeasurementUnit(domain.unit),
            userId = domain.userId,
        )
    }

    private fun mapGender(entity: EntityGender): Gender {
        return when (entity) {
            EntityGender.FEMALE -> Gender.FEMALE
            EntityGender.MALE -> Gender.MALE
        }
    }

    private fun mapGender(domain: Gender): EntityGender {
        return when (domain) {
            Gender.FEMALE -> EntityGender.FEMALE
            Gender.MALE -> EntityGender.MALE
        }
    }

    private fun mapHeightUnit(entity: EntityHeightUnit): HeightUnit {
        return when (entity) {
            EntityHeightUnit.CM -> HeightUnit.CM
            EntityHeightUnit.INCHES -> HeightUnit.INCHES
        }
    }

    private fun mapHeightUnit(domain: HeightUnit): EntityHeightUnit {
        return when (domain) {
            HeightUnit.CM -> EntityHeightUnit.CM
            HeightUnit.INCHES -> EntityHeightUnit.INCHES
        }
    }

    private fun mapMeasurementUnit(entity: EntityMeasurementUnit): MeasurementUnit {
        return when (entity) {
            EntityMeasurementUnit.CM -> MeasurementUnit.CM
            EntityMeasurementUnit.KG -> MeasurementUnit.KG
            EntityMeasurementUnit.LBS -> MeasurementUnit.LBS
            EntityMeasurementUnit.METERS -> MeasurementUnit.METERS
            EntityMeasurementUnit.REPS -> MeasurementUnit.REPS
            EntityMeasurementUnit.SECONDS -> MeasurementUnit.SECONDS
        }
    }

    private fun mapMeasurementUnit(domain: MeasurementUnit): EntityMeasurementUnit {
        return when (domain) {
            MeasurementUnit.CM -> EntityMeasurementUnit.CM
            MeasurementUnit.KG -> EntityMeasurementUnit.KG
            MeasurementUnit.LBS -> EntityMeasurementUnit.LBS
            MeasurementUnit.METERS -> EntityMeasurementUnit.METERS
            MeasurementUnit.REPS -> EntityMeasurementUnit.REPS
            MeasurementUnit.SECONDS -> EntityMeasurementUnit.SECONDS
        }
    }

    private fun mapUserRole(entity: EntityUserRole): UserRole {
        return when (entity) {
            EntityUserRole.OWNER -> UserRole.OWNER
            EntityUserRole.TRAINEE -> UserRole.TRAINEE
        }
    }

    private fun mapUserRole(domain: UserRole): EntityUserRole {
        return when (domain) {
            UserRole.OWNER -> EntityUserRole.OWNER
            UserRole.TRAINEE -> EntityUserRole.TRAINEE
        }
    }

    private fun mapWeightUnit(entity: EntityWeightUnit): WeightUnit {
        return when (entity) {
            EntityWeightUnit.KG -> WeightUnit.KG
            EntityWeightUnit.LBS -> WeightUnit.LBS
        }
    }

    private fun mapWeightUnit(domain: WeightUnit): EntityWeightUnit {
        return when (domain) {
            WeightUnit.KG -> EntityWeightUnit.KG
            WeightUnit.LBS -> EntityWeightUnit.LBS
        }
    }
}
