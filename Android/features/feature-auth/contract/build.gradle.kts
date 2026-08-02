plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Coroutines — Flow in the active-user public contract /
    // Coroutines — Flow в публичном контракте активного пользователя
    implementation(libs.coroutines.core)

    // core:navigation — common navigation contracts /
    // core:navigation — общие контракты навигации
    implementation(project(":core:navigation"))
}
