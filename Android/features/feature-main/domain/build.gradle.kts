plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Coroutines — Flow in domain use cases / Coroutines — Flow в domain-сценариях
    implementation(libs.coroutines.core)

    // feature-auth:contract — active-user public contract /
    // feature-auth:contract — публичный контракт активного пользователя
    implementation(project(":features:feature-auth:contract"))
}
