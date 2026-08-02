package com.vasev.trainingapp.feature.auth.ui.useredit.mapper

import com.vasev.trainingapp.core.common.domain.MeasurementConversion
import com.vasev.trainingapp.feature.auth.domain.entity.User
import com.vasev.trainingapp.feature.auth.domain.entity.type.Gender
import com.vasev.trainingapp.feature.auth.domain.entity.type.HeightUnit
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import com.vasev.trainingapp.feature.auth.domain.entity.type.WeightUnit
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.formatter.UserEditUiFormatterProvider
import java.text.DecimalFormat
import javax.inject.Inject

/**
 * Maps a domain user to the state of the profile editing screen.
 * Преобразует domain-пользователя в состояние экрана редактирования профиля.
 *
 * `@Inject` — Hilt creates this stateless mapper for the view model.
 * `@Inject` — Hilt создаёт этот маппер без состояния для view model.
 */
internal class UserEditUiMapper @Inject constructor(
    private val formatterProvider: UserEditUiFormatterProvider,
) {

    fun map(
        mode: UserEditUiState.Ready.Mode.EditUser,
        user: User,
    ): UserEditUiState.Ready {
        val decimalFormat = formatterProvider.provide().decimalFormat

        return UserEditUiState.Ready(
            birthDate = user.birthDate,
            gender = mapGender(user.gender),
            heightInput = formatHeight(
                decimalFormat = decimalFormat,
                height = user.height,
                unit = user.heightUnit,
            ),
            heightUnit = mapHeightUnit(user.heightUnit),
            mode = mode,
            nameInput = user.name,
            role = mapRole(user.role),
            weightInput = formatWeight(
                decimalFormat = decimalFormat,
                unit = user.weightUnit,
                weight = user.weight,
            ),
            weightUnit = mapWeightUnit(user.weightUnit),
        )
    }

    private fun formatHeight(
        decimalFormat: DecimalFormat,
        height: Double,
        unit: HeightUnit,
    ): String {
        if (height == 0.0) {
            return ""
        }
        val displayHeight = when (unit) {
            HeightUnit.CM -> height / MeasurementConversion.MILLIMETERS_PER_CENTIMETER
            HeightUnit.INCHES -> height / MeasurementConversion.MILLIMETERS_PER_INCH
        }
        return decimalFormat.format(displayHeight)
    }

    private fun formatWeight(
        decimalFormat: DecimalFormat,
        unit: WeightUnit,
        weight: Double,
    ): String {
        if (weight == 0.0) {
            return ""
        }
        val displayWeight = when (unit) {
            WeightUnit.KG -> weight / MeasurementConversion.GRAMS_PER_KILOGRAM
            WeightUnit.LBS -> weight / MeasurementConversion.GRAMS_PER_POUND
        }
        return decimalFormat.format(displayWeight)
    }

    private fun mapGender(gender: Gender): UserEditUiState.Ready.Gender {
        return when (gender) {
            Gender.FEMALE -> UserEditUiState.Ready.Gender.FEMALE
            Gender.MALE -> UserEditUiState.Ready.Gender.MALE
            Gender.UNKNOWN -> UserEditUiState.Ready.Gender.UNKNOWN
        }
    }

    private fun mapHeightUnit(heightUnit: HeightUnit): UserEditUiState.Ready.HeightUnit {
        return when (heightUnit) {
            HeightUnit.CM -> UserEditUiState.Ready.HeightUnit.CENTIMETERS
            HeightUnit.INCHES -> UserEditUiState.Ready.HeightUnit.INCHES
        }
    }

    private fun mapRole(role: UserRole): UserEditUiState.Ready.Role {
        return when (role) {
            UserRole.OWNER -> UserEditUiState.Ready.Role.OWNER
            UserRole.TRAINEE -> UserEditUiState.Ready.Role.TRAINEE
        }
    }

    private fun mapWeightUnit(weightUnit: WeightUnit): UserEditUiState.Ready.WeightUnit {
        return when (weightUnit) {
            WeightUnit.KG -> UserEditUiState.Ready.WeightUnit.KILOGRAMS
            WeightUnit.LBS -> UserEditUiState.Ready.WeightUnit.POUNDS
        }
    }

}
