plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Hilt plugin — processing @HiltAndroidApp, @Inject, @Module annotations / Плагин Hilt — обработка аннотаций DI
    alias(libs.plugins.hilt)
    // Kapt — annotation processor for Hilt (generates glue code) / Kapt — процессор аннотаций для Hilt (генерирует код-связку)
    alias(libs.plugins.kapt)
    // Navigation Safe Args plugin — type-safe navigation directions generation / Плагин Navigation Safe Args — генерация типобезопасных направлений навигации
    alias(libs.plugins.navigation.safeargs)
}

android {
    namespace = "com.vasev.trainingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vasev.trainingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Debuggable enabled for debugging tools (LeakCanary, Timber debug tree) / Debuggable включён для отладочных инструментов (LeakCanary, Timber debug tree)
            isDebuggable = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // ViewBinding enabled — generates type-safe accessors for XML views / ViewBinding включён — генерирует типобезопасные доступы к XML-views
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        // Core library desugaring — enables Java 8 time API (LocalDate, Instant, etc.) on minSdk < 26 / Core library desugaring — включает Java 8 time API (LocalDate, Instant и т.д.) на minSdk < 26
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // AndroidX core / AndroidX core
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)

    // Material Design 2 (Material Components for Android) / Material Design 2 (Material Components for Android)
    implementation(libs.material)

    // Hilt — dependency injection / Hilt — внедрение зависимостей
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Jetpack Navigation — navigation between screens / Jetpack Navigation — навигация между экранами
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Timber — logging / Timber — логирование
    implementation(libs.timber)

    // Core library desugaring — Java 8 time API on minSdk < 26 / Core library desugaring — Java 8 time API на minSdk < 26
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // LeakCanary — memory leak detection, debug only / LeakCanary — детекция утечек памяти, только debug
    debugImplementation(libs.leakcanary.android)

    // Testing / Тестирование
    testImplementation(libs.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.turbine)
    testImplementation(libs.robolectric)
    testImplementation(libs.hamcrest)
}
