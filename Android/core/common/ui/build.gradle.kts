plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.vasev.trainingapp.core.common.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // AndroidX core-ktx — Android Kotlin extensions /
    // AndroidX core-ktx — Kotlin-расширения Android
    implementation(libs.core.ktx)

    // Material Components — theme attributes used by shared resources /
    // Material Components — атрибуты темы, используемые общими ресурсами
    implementation(libs.material)
}
