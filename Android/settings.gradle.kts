pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TrainingApp"

include(":app")

// Core modules / Основные модули
include(":core:common")
include(":core:navigation")
include(":core:database")
include(":core:reference-data")

// Feature: auth / Пользователи / Feature: auth / Пользователи
include(":features:feature-auth:domain")
include(":features:feature-auth:ui")

// Feature: programs / Программы / Feature: programs / Программы
include(":features:feature-programs:contract")
include(":features:feature-programs:domain")
include(":features:feature-programs:ui")

// Feature: workout / Текущая тренировка / Feature: workout / Текущая тренировка
include(":features:feature-workout:contract")
include(":features:feature-workout:domain")
include(":features:feature-workout:ui")

// Feature: calendar / Календарь / Feature: calendar / Календарь
include(":features:feature-calendar:domain")
include(":features:feature-calendar:ui")

// Feature: weight / Отслеживание веса / Feature: weight / Отслеживание веса
include(":features:feature-weight:domain")
include(":features:feature-weight:ui")

// Feature: settings / Настройки / Feature: settings / Настройки
include(":features:feature-settings:domain")
include(":features:feature-settings:ui")

// Feature: exercises / Упражнения / Feature: exercises / Упражнения
include(":features:feature-exercises:domain")
include(":features:feature-exercises:ui")

// Feature: anatomy / Анатомический атлас / Feature: anatomy / Анатомический атлас
include(":features:feature-anatomy:domain")
include(":features:feature-anatomy:ui")

// Feature: info / Информационные экраны (СРЦ, о приложении) / Feature: info / Информационные экраны (СРЦ, о приложении)
include(":features:feature-info:domain")
include(":features:feature-info:ui")
