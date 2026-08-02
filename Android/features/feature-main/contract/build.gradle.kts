plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // core:navigation — common navigation contracts /
    // core:navigation — общие контракты навигации
    implementation(project(":core:navigation"))
}
