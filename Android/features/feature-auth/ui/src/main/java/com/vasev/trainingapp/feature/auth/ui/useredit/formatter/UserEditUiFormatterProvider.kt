package com.vasev.trainingapp.feature.auth.ui.useredit.formatter

import android.content.Context
import androidx.core.os.ConfigurationCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import javax.inject.Inject

/**
 * Creates locale-aware formatters for user-editing UI.
 * Создаёт форматтеры с учётом локали для UI редактирования пользователя.
 *
 * The provider reads the locale from the application resources, so per-app Android locales are
 * respected as well as the system locale. / Провайдер читает локаль из ресурсов приложения,
 * поэтому учитывает и локаль приложения Android, и системную локаль.
 */
internal class UserEditUiFormatterProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private var cachedFormatters: Formatters? = null

    @Synchronized
    fun provide(): Formatters {
        val locale = currentLocale()
        val zoneId = ZoneId.systemDefault()
        val cachedFormatters = cachedFormatters
        if (cachedFormatters?.locale == locale && cachedFormatters.zoneId == zoneId) {
            return cachedFormatters
        }

        return Formatters(
            decimalFormat = DecimalFormat(
                /* pattern = */ "0.##",
                /* symbols = */ DecimalFormatSymbols.getInstance(locale),
            ),
            dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(locale)
                .withZone(zoneId),
            locale = locale,
            zoneId = zoneId,
        ).also { newFormatters ->
            this.cachedFormatters = newFormatters
        }
    }

    /**
     * Pair of formatters and the locale they were created for.
     * Пара форматтеров и локаль, для которой они созданы.
     */
    internal data class Formatters(
        val decimalFormat: DecimalFormat,
        val dateFormatter: DateTimeFormatter,
        val locale: Locale,
        val zoneId: ZoneId,
    )

    private fun currentLocale(): Locale {
        return ConfigurationCompat.getLocales(context.resources.configuration)[0]
            ?: Locale.getDefault()
    }
}
