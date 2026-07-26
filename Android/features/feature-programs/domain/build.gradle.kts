plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Coroutines — Flow in repository interfaces / Coroutines — Flow в интерфейсах репозиториев
    implementation(libs.coroutines.core)
}
