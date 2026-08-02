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

    // AndroidX SplashScreen — system-compatible launch screen / AndroidX SplashScreen — системный совместимый экран запуска
    implementation(libs.core.splashscreen)

    // Hilt — dependency injection / Hilt — внедрение зависимостей
    implementation(libs.hilt.android)

    // Material Design 2 (Material Components for Android) / Material Design 2 (Material Components for Android)
    implementation(libs.material)

    // Jetpack Navigation — navigation between screens / Jetpack Navigation — навигация между экранами
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Timber — logging / Timber — логирование
    implementation(libs.timber)

    // Internal modules (alphabetical) / Внутренние модули (по алфавиту)

    // core:common:logging — shared Timber extensions / core:common:logging — общие расширения Timber
    implementation(project(":core:common:logging"))

    // core:database — Room TrainingDatabase + DAOs + Hilt DatabaseModule /
    // core:database — Room TrainingDatabase + DAO + Hilt DatabaseModule
    // app no longer references Room types directly: DatabaseModule lives in core:database and
    // provides DAOs via Hilt. Feature `data` modules consume the DAOs and expose repositories. /
    // app больше не ссылается на типы Room напрямую: DatabaseModule живёт в core:database и
    // предоставляет DAO через Hilt. `data`-модули фичей потребляют DAO и отдают репозитории.
    implementation(project(":core:database"))

    // core:navigation — Navigator port + Screen marker / core:navigation — порт Navigator + маркер Screen
    implementation(project(":core:navigation"))

    // Feature modules (alphabetical) / Модули фичей (по алфавиту)
    // Each feature contributes: `data` (Hilt @Binds for repositories) + `ui` (screens) +
    // `contract` (screen routes, via ui transitively). /
    // Каждая фича даёт: `data` (Hilt @Binds для репозиториев) + `ui` (экраны) +
    // `contract` (маршруты экранов, транзитивно через ui).

    // feature-anatomy — anatomy atlas / feature-anatomy — анатомический атлас
    implementation(project(":features:feature-anatomy:data"))
    implementation(project(":features:feature-anatomy:ui"))

    // feature-auth — users / feature-auth — пользователи
    implementation(project(":features:feature-auth:contract"))
    implementation(project(":features:feature-auth:data"))
    implementation(project(":features:feature-auth:domain"))
    implementation(project(":features:feature-auth:ui"))

    // feature-calendar — calendar / feature-calendar — календарь
    implementation(project(":features:feature-calendar:ui"))

    // feature-exercises — exercises / feature-exercises — упражнения
    implementation(project(":features:feature-exercises:data"))
    implementation(project(":features:feature-exercises:ui"))

    // feature-help — help / feature-help — справка
    implementation(project(":features:feature-help:ui"))

    // feature-nutrition — nutrition / feature-nutrition — питание
    implementation(project(":features:feature-nutrition:data"))
    implementation(project(":features:feature-nutrition:ui"))

    // feature-programs — programs / feature-programs — программы
    implementation(project(":features:feature-programs:data"))
    implementation(project(":features:feature-programs:ui"))

    // feature-settings — settings / feature-settings — настройки
    implementation(project(":features:feature-settings:ui"))

    // feature-weight — weight tracking / feature-weight — отслеживание веса
    implementation(project(":features:feature-weight:data"))
    implementation(project(":features:feature-weight:ui"))

    // feature-workout — current workout / feature-workout — текущая тренировка
    implementation(project(":features:feature-workout:data"))
    implementation(project(":features:feature-workout:ui"))

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
