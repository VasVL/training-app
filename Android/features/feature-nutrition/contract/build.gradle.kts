plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // core:navigation — Screen marker interface / core:navigation — маркерный интерфейс Screen
    implementation(project(":core:navigation"))
}
