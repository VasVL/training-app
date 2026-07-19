plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.vasev.trainingapp.feature.nutrition.ui"
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
    // Internal modules (alphabetical) / Внутренние модули (по алфавиту)

    // feature-nutrition:contract — screen routes / feature-nutrition:contract — маршруты экранов
    implementation(project(":features:feature-nutrition:contract"))

    // feature-nutrition:domain — repository interfaces and domain models /
    // feature-nutrition:domain — интерфейсы репозиториев и domain-модели
    implementation(project(":features:feature-nutrition:domain"))
}
