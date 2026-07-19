plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.vasev.trainingapp.core.database"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        // Room schema export location / Room — каталог для экспорта схем
        ksp {
            arg("room.schemaLocation", "${projectDir}/schemas")
            arg("room.incremental", "true")
        }
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
    // Room (local DB) / Room (локальная БД)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Coroutines / Coroutines (для suspend DAO)
    implementation(libs.coroutines.core)

    // Internal modules / Внутренние модули
    implementation(project(":core:common"))
    implementation(project(":core:reference-data"))
}
