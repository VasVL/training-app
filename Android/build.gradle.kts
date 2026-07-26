plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    // Hilt plugin — declared here, applied in modules that use DI / Плагин Hilt — объявлен здесь, применяется в модулях с DI
    alias(libs.plugins.hilt) apply false
    // KSP plugin — Kotlin Symbol Processing, fast annotation processing (Hilt, Room) / Плагин KSP — Kotlin Symbol Processing, быстрая обработка аннотаций (Hilt, Room)
    alias(libs.plugins.ksp) apply false
    // kotlinx.serialization plugin — JSON serialization / Плагин kotlinx.serialization — сериализация JSON
    alias(libs.plugins.kotlin.serialization) apply false
    // Navigation Safe Args plugin — type-safe navigation / Плагин Navigation Safe Args — типобезопасная навигация
    alias(libs.plugins.navigation.safeargs) apply false
}
