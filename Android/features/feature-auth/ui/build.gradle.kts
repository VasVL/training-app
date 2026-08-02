plugins {
    alias(libs.plugins.android.library)
    // Hilt plugin — processing @HiltViewModel, @Inject, @AndroidEntryPoint annotations /
    // Плагин Hilt — обработка аннотаций @HiltViewModel, @Inject, @AndroidEntryPoint
    alias(libs.plugins.hilt)
    // KSP — Kotlin Symbol Processing for generated Hilt code /
    // KSP — Kotlin Symbol Processing для генерируемого кода Hilt
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.vasev.trainingapp.feature.auth.ui"
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

    // ViewBinding — generates type-safe binding classes for XML layouts /
    // ViewBinding — генерирует типобезопасные binding-классы для XML-разметок
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // implementation (alphabetical) / implementation (по алфавиту)

    // AndroidX AppCompat / AndroidX AppCompat
    implementation(libs.appcompat)

    // AndroidX ConstraintLayout / AndroidX ConstraintLayout
    implementation(libs.constraintlayout)

    // AndroidX core-ktx / AndroidX core-ktx
    implementation(libs.core.ktx)

    // Coroutines Android — viewModelScope and Flow collection on Android /
    // Coroutines Android — viewModelScope и сбор Flow на Android
    implementation(libs.coroutines.android)

    // AndroidX Fragment KTX / AndroidX Fragment KTX
    implementation(libs.fragment.ktx)

    // Hilt — dependency injection / Hilt — внедрение зависимостей
    implementation(libs.hilt.android)

    // AndroidX Lifecycle — ViewModel and lifecycle-aware Flow collection /
    // AndroidX Lifecycle — ViewModel и lifecycle-aware сбор Flow
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Material Design 2 / Material Design 2
    implementation(libs.material)

    // AndroidX RecyclerView / AndroidX RecyclerView
    implementation(libs.recyclerview)

    // Timber — diagnostic logging / Timber — диагностическое логирование
    implementation(libs.timber)

    // Internal modules (alphabetical) / Внутренние модули (по алфавиту)

    // core:common:domain — shared Kotlin domain utilities /
    // core:common:domain — общие Kotlin-утилиты domain-слоя
    implementation(project(":core:common:domain"))

    // core:common:ui — shared UI resources /
    // core:common:ui — общие UI-ресурсы
    implementation(project(":core:common:ui"))

    // core:navigation — common navigation contracts /
    // core:navigation — общие контракты навигации
    implementation(project(":core:navigation"))

    // feature-auth:contract — auth screen routes /
    // feature-auth:contract — маршруты экранов auth
    implementation(project(":features:feature-auth:contract"))

    // feature-auth:domain — user models and repository interfaces /
    // feature-auth:domain — модели пользователей и интерфейсы репозиториев
    implementation(project(":features:feature-auth:domain"))

    // ksp / ksp

    // Hilt compiler — generates dependency-injection code /
    // Компилятор Hilt — генерирует код внедрения зависимостей
    ksp(libs.hilt.compiler)
}
