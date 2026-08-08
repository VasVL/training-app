plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // Hilt plugin — processing @Module, @Binds, @Inject annotations in feature-auth:data /
    // Плагин Hilt — обработка аннотаций @Module, @Binds, @Inject в feature-auth:data
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.vasev.trainingapp.feature.auth.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        // Core library desugaring — Java 8 time API on minSdk < 26 /
        // Core library desugaring — Java 8 time API на minSdk < 26
        isCoreLibraryDesugaringEnabled = true
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

    // Timber — diagnostic logging / Timber — диагностическое логирование
    implementation(libs.timber)

    // Core library desugaring — Java 8 time API on minSdk < 26 /
    // Core library desugaring — Java 8 time API на minSdk < 26
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Internal modules (alphabetical) / Внутренние модули (по алфавиту)

    // core:database — provides UserDao, UserMaxDao via Hilt / core:database — предоставляет
    // UserDao, UserMaxDao через Hilt
    implementation(project(":core:database"))

    // feature-auth:contract — active-user public contract /
    // feature-auth:contract — публичный контракт активного пользователя
    implementation(project(":features:feature-auth:contract"))

    // feature-auth:domain — repository interfaces and domain models /
    // feature-auth:domain — интерфейсы репозиториев и domain-модели
    implementation(project(":features:feature-auth:domain"))

    // ksp (alphabetical) / ksp (по алфавиту)

    // Hilt compiler — generates Hilt glue code for @Binds / Компилятор Hilt — генерирует
    // код-связку Hilt для @Binds
    ksp(libs.hilt.compiler)
}
