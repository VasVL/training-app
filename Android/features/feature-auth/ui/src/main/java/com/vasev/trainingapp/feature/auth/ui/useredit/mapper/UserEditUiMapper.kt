package com.vasev.trainingapp.feature.auth.ui.useredit.mapper

import com.vasev.trainingapp.feature.auth.domain.entity.User
import com.vasev.trainingapp.feature.auth.domain.entity.type.Gender
import com.vasev.trainingapp.feature.auth.domain.entity.type.HeightUnit
import com.vasev.trainingapp.feature.auth.domain.entity.type.UserRole
import com.vasev.trainingapp.feature.auth.domain.entity.type.WeightUnit
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserEditUiState
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import javax.inject.Inject

/**
 * Maps a domain user to the state of the profile editing screen.
 * Преобразует domain-пользователя в состояние экрана редактирования профиля.
 *
 * `@Inject` — Hilt creates this stateless mapper for the view model.
 * `@Inject` — Hilt создаёт этот маппер без состояния для view model.
 */
internal class UserEditUiMapper @Inject constructor() {

    fun map(user: User): UserEditUiState.Ready {
        val decimalFormat = DecimalFormat(
            /* pattern = */ "0.##",
            /* symbols = */ DecimalFormatSymbols.getInstance(),
        )

        return UserEditUiState.Ready(
            birthDate = user.birthDate,
            gender = mapGender(user.gender),
            heightInput = decimalFormat.format(user.height),
            heightUnit = mapHeightUnit(user.heightUnit),
            nameInput = user.name,
            role = mapRole(user.role),
            weightInput = decimalFormat.format(user.weight),
            weightUnit = mapWeightUnit(user.weightUnit),
        )
    }

    private fun mapGender(gender: Gender): UserEditUiState.Ready.Gender {
        return when (gender) {
            Gender.FEMALE -> UserEditUiState.Ready.Gender.FEMALE
            Gender.MALE -> UserEditUiState.Ready.Gender.MALE
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
