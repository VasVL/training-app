package com.vasev.trainingapp.feature.auth.ui.useredit.mapper

import android.content.Context
import android.text.format.DateUtils
import com.vasev.trainingapp.feature.auth.domain.entity.UserMaxWithExercise
import com.vasev.trainingapp.feature.auth.domain.entity.type.MeasurementUnit
import com.vasev.trainingapp.feature.auth.ui.R
import com.vasev.trainingapp.feature.auth.ui.useredit.entity.UserMaxesUiState
import com.vasev.trainingapp.feature.auth.ui.useredit.formatter.UserEditUiFormatterProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import java.time.Instant
import javax.inject.Inject
import kotlin.math.roundToLong

/**
 * Maps personal result domain data to screen-specific UI data.
 * Преобразует domain-данные личных результатов в UI-данные конкретного экрана.
 */
internal class UserMaxesUiMapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val formatterProvider: UserEditUiFormatterProvider,
) {

    fun map(
        maximums: List<UserMaxWithExercise>,
    ): List<UserMaxesUiState.Ready.Content.Maximum> {
        val formatters = formatterProvider.provide()
        return maximums.map { maximum ->
            UserMaxesUiState.Ready.Content.Maximum(
                dateText = formatters.dateFormatter.format(Instant.ofEpochMilli(maximum.measuredAt)),
                exerciseName = maximum.exerciseName,
                id = maximum.id,
                valueText = mapValueText(
                    decimalFormat = formatters.decimalFormat,
                    maximum = maximum,
                ),
            )
        }
    }

    private fun mapValueText(
        decimalFormat: DecimalFormat,
        maximum: UserMaxWithExercise,
    ): String {
        return when (maximum.unit) {
            MeasurementUnit.SECONDS -> {
                DateUtils.formatElapsedTime(maximum.maxValue.roundToLong())
            }

            else -> {
                val valueText = decimalFormat.format(maximum.maxValue)
                val unitText = context.getString(getUnitTextResId(maximum.unit))
                "$valueText $unitText"
            }
        }
    }

    private fun getUnitTextResId(unit: MeasurementUnit): Int {
        return when (unit) {
            MeasurementUnit.CM -> R.string.auth_unit_centimeters
            MeasurementUnit.KG -> R.string.auth_unit_kilograms
            MeasurementUnit.LBS -> R.string.auth_unit_pounds
            MeasurementUnit.METERS -> R.string.auth_unit_meters
            MeasurementUnit.REPS -> R.string.auth_unit_repetitions
            MeasurementUnit.SECONDS -> error("Seconds use elapsed-time format")
        }
    }
}
