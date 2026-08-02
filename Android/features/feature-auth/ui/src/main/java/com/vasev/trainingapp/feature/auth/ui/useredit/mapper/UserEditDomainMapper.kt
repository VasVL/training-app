package com.vasev.trainingapp.feature.auth.ui.useredit.mapper

import com.vasev.trainingapp.core.common.domain.MeasurementConversion
import com.vasev.trainingapp.feature.auth.domain.entity.User
import com.vasev.trainingapp.feature.auth.domain.entity.type.Gender
import com.vasev.trainingapp.feature.auth.domain.entity.type.HeightUnit
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import com.vasev.trainingapp.feature.auth.domain.entity.type.WeightUnit
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import javax.inject.Inject

/**
 * Maps profile form data to a domain user.
 * Преобразует данные формы профиля в domain-пользователя.
 *
 * `@Inject` — Hilt creates this stateless mapper for the view model.
 * `@Inject` — Hilt создаёт этот маппер без состояния для view model.
 */
internal class UserEditDomainMapper @Inject constructor() {

    fun mapNew(
        createdAt: Long,
        isDefault: Boolean,
        state: UserEditUiState.Ready,
    ): User {
        return User(
            birthDate = state.birthDate,
            createdAt = createdAt,
            gender = mapGender(state.gender),
            height = mapHeight(
                heightInput = state.heightInput,
                heightUnit = state.heightUnit,
            ),
            heightUnit = mapHeightUnit(state.heightUnit),
            id = 0,
            isDefault = isDefault,
            name = state.nameInput.trim(),
            remoteId = null,
            role = mapRole(state.role),
            weight = mapWeight(
                weightInput = state.weightInput,
                weightUnit = state.weightUnit,
            ),
            weightUnit = mapWeightUnit(state.weightUnit),
        )
    }

    fun mapUpdated(
        state: UserEditUiState.Ready,
        user: User,
    ): User {
        return user.copy(
            birthDate = state.birthDate,
            gender = mapGender(state.gender),
            height = mapHeight(
                heightInput = state.heightInput,
                heightUnit = state.heightUnit,
            ),
            heightUnit = mapHeightUnit(state.heightUnit),
            name = state.nameInput.trim(),
            role = mapRole(state.role),
            weight = mapWeight(
                weightInput = state.weightInput,
                weightUnit = state.weightUnit,
            ),
        )
    }

    private fun mapGender(gender: UserEditUiState.Ready.Gender): Gender {
        return when (gender) {
            UserEditUiState.Ready.Gender.FEMALE -> Gender.FEMALE
            UserEditUiState.Ready.Gender.MALE -> Gender.MALE
            UserEditUiState.Ready.Gender.UNKNOWN -> Gender.UNKNOWN
        }
    }

    private fun mapHeight(
        heightInput: String,
        heightUnit: UserEditUiState.Ready.HeightUnit,
    ): Double {
        val height = parseOptionalDecimal(input = heightInput)
        return when (heightUnit) {
            UserEditUiState.Ready.HeightUnit.CENTIMETERS -> {
                height * MeasurementConversion.MILLIMETERS_PER_CENTIMETER
            }

            UserEditUiState.Ready.HeightUnit.INCHES -> {
                height * MeasurementConversion.MILLIMETERS_PER_INCH
            }
        }
    }

    private fun mapHeightUnit(
        heightUnit: UserEditUiState.Ready.HeightUnit,
    ): HeightUnit {
        return when (heightUnit) {
            UserEditUiState.Ready.HeightUnit.CENTIMETERS -> HeightUnit.CM
            UserEditUiState.Ready.HeightUnit.INCHES -> HeightUnit.INCHES
        }
    }

    private fun mapRole(role: UserEditUiState.Ready.Role): UserRole {
        return when (role) {
            UserEditUiState.Ready.Role.OWNER -> UserRole.OWNER
            UserEditUiState.Ready.Role.TRAINEE -> UserRole.TRAINEE
            UserEditUiState.Ready.Role.UNKNOWN -> error("Unknown profile role")
        }
    }

    private fun mapWeight(
        weightInput: String,
        weightUnit: UserEditUiState.Ready.WeightUnit,
    ): Double {
        val weight = parseOptionalDecimal(input = weightInput)
        return when (weightUnit) {
            UserEditUiState.Ready.WeightUnit.KILOGRAMS -> {
                weight * MeasurementConversion.GRAMS_PER_KILOGRAM
            }

            UserEditUiState.Ready.WeightUnit.POUNDS -> {
                weight * MeasurementConversion.GRAMS_PER_POUND
            }
        }
    }

    private fun mapWeightUnit(
        weightUnit: UserEditUiState.Ready.WeightUnit,
    ): WeightUnit {
        return when (weightUnit) {
            UserEditUiState.Ready.WeightUnit.KILOGRAMS -> WeightUnit.KG
            UserEditUiState.Ready.WeightUnit.POUNDS -> WeightUnit.LBS
        }
    }

    private fun parseOptionalDecimal(input: String): Double {
        return input.replace(',', '.')
            .toDoubleOrNull()
            ?: 0.0
    }
}
