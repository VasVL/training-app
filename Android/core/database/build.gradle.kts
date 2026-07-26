plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Hilt plugin — processing @Module, @Provides, @Inject annotations in core:database /
    // Плагин Hilt — обработка аннотаций @Module, @Provides, @Inject в core:database
    alias(libs.plugins.hilt)
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
    // Coroutines / Coroutines (для suspend DAO)
    implementation(libs.coroutines.core)

    // Hilt — dependency injection (DatabaseModule provides DAOs) /
    // Hilt — внедрение зависимостей (DatabaseModule предоставляет DAO)
    implementation(libs.hilt.android)

    // Room (local DB) / Room (локальная БД)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)

    // ksp (alphabetical) / ksp (по алфавиту)

    // Hilt compiler — generates Hilt glue code for DatabaseModule /
    // Компилятор Hilt — генерирует код-связку Hilt для DatabaseModule
    ksp(libs.hilt.compiler)

    // Room compiler — generates Room implementation from DAO/Entity /
    // Компилятор Room — генерирует реализацию Room из DAO/Entity
    ksp(libs.room.compiler)
}
