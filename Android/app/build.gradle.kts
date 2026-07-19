plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Hilt plugin — processing @HiltAndroidApp, @Inject, @Module annotations / Плагин Hilt — обработка аннотаций DI
    alias(libs.plugins.hilt)
    // KSP — Kotlin Symbol Processing, fast annotation processor for Hilt (generates glue code) / KSP — Kotlin Symbol Processing, быстрый процессор аннотаций для Hilt (генерирует код-связку)
    alias(libs.plugins.ksp)
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

    // ViewBinding + BuildConfig enabled — generates type-safe accessors for XML views and
    // the BuildConfig class (used for BuildConfig.DEBUG checks) /
    // ViewBinding + BuildConfig включены — генерируют типобезопасные доступы к XML-views и
    // класс BuildConfig (используется для проверок BuildConfig.DEBUG)
    buildFeatures {
        buildConfig = true
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
    // implementation (alphabetical) / implementation (по алфавиту)

    // AndroidX AppCompat / AndroidX AppCompat
    implementation(libs.appcompat)

    // Coil — image loading (Kotlin-first, Coroutines) / Coil — загрузка картинок (Kotlin-first, Coroutines)
    implementation(libs.coil)

    // AndroidX ConstraintLayout / AndroidX ConstraintLayout
    implementation(libs.constraintlayout)

    // AndroidX core-ktx / AndroidX core-ktx
    implementation(libs.core.ktx)

    // Hilt — dependency injection / Hilt — внедрение зависимостей
    implementation(libs.hilt.android)

    // Material Design 2 (Material Components for Android) / Material Design 2 (Material Components for Android)
    implementation(libs.material)

    // Jetpack Navigation — navigation between screens / Jetpack Navigation — навигация между экранами
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Room — needed in app because DatabaseModule references Room.databaseBuilder and
    // RoomDatabase (the supertype of TrainingDatabase). core:database exposes TrainingDatabase
    // via `implementation`, so Room types are not transitively visible here. /
    // Room — нужен в app, потому что DatabaseModule ссылается на Room.databaseBuilder и
    // RoomDatabase (супертип TrainingDatabase). core:database отдаёт TrainingDatabase через
    // `implementation`, поэтому типы Room не видны здесь транзитивно.
    implementation(libs.room.runtime)

    // Timber — logging / Timber — логирование
    implementation(libs.timber)

    // Internal modules (alphabetical) / Внутренние модули (по алфавиту)

    // core:common — Resource<T> and shared utilities / core:common — Resource<T> и общие утилиты
    implementation(project(":core:common"))

    // core:database — Room TrainingDatabase + DAOs (consumed by DatabaseModule) / core:database — Room TrainingDatabase + DAO (используются DatabaseModule)
    implementation(project(":core:database"))

    // core:navigation — Navigator port + Screen marker / core:navigation — порт Navigator + маркер Screen
    implementation(project(":core:navigation"))

    // ksp (alphabetical) / ksp (по алфавиту)

    // Hilt compiler — generates Hilt glue code / Компилятор Hilt — генерирует код-связку Hilt
    ksp(libs.hilt.compiler)

    // coreLibraryDesugaring / coreLibraryDesugaring

    // Core library desugaring — Java 8 time API on minSdk < 26 / Core library desugaring — Java 8 time API на minSdk < 26
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // debugImplementation (alphabetical) / debugImplementation (по алфавиту)

    // LeakCanary — memory leak detection, debug only / LeakCanary — детекция утечек памяти, только debug
    debugImplementation(libs.leakcanary.android)

    // testImplementation (alphabetical) / testImplementation (по алфавиту)

    // Hamcrest — matchers for assertions / Hamcrest — матчеры для проверок
    testImplementation(libs.hamcrest)

    // JUnit — unit testing framework / JUnit — фреймворк юнит-тестирования
    testImplementation(libs.junit)

    // Mockito-Kotlin — mocking framework / Mockito-Kotlin — фреймворк моков
    testImplementation(libs.mockito.kotlin)

    // Robolectric — on-JVM Android testing / Robolectric — тестирование Android на JVM
    testImplementation(libs.robolectric)

    // Turbine — Flow testing utilities / Turbine — утилиты тестирования Flow
    testImplementation(libs.turbine)
}
