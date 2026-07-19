plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Hilt plugin — processing @Module, @Binds, @Inject annotations in feature-anatomy:data /
    // Плагин Hilt — обработка аннотаций @Module, @Binds, @Inject в feature-anatomy:data
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.vasev.trainingapp.feature.anatomy.data"
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
    // Coroutines — Flow mapping in repository implementations /
    // Coroutines — маппинг Flow в реализациях репозиториев
    implementation(libs.coroutines.core)

    // Hilt — dependency injection (binds repositories to implementations) /
    // Hilt — внедрение зависимостей (биндит репозитории к реализациям)
    implementation(libs.hilt.android)

    // Internal modules (alphabetical) / Внутренние модули (по алфавиту)

    // core:database — provides DAOs via Hilt / core:database — предоставляет DAO через Hilt
    implementation(project(":core:database"))

    // feature-anatomy:domain — repository interfaces and domain models /
    // feature-anatomy:domain — интерфейсы репозиториев и domain-модели
    implementation(project(":features:feature-anatomy:domain"))

    // ksp (alphabetical) / ksp (по алфавиту)

    // Hilt compiler — generates Hilt glue code for @Binds / Компилятор Hilt — генерирует
    // код-связку Hilt для @Binds
    ksp(libs.hilt.compiler)
}
