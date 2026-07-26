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
include(":core:common:domain")
include(":core:common:logging")
include(":core:common:ui")
include(":core:navigation")
include(":core:database")

// Feature: auth / Пользователи / Feature: auth / Пользователи
include(":features:feature-auth:contract")
include(":features:feature-auth:data")
include(":features:feature-auth:domain")
include(":features:feature-auth:ui")

// Feature: programs / Программы / Feature: programs / Программы
include(":features:feature-programs:contract")
include(":features:feature-programs:data")
include(":features:feature-programs:domain")
include(":features:feature-programs:ui")

// Feature: workout / Текущая тренировка / Feature: workout / Текущая тренировка
include(":features:feature-workout:contract")
include(":features:feature-workout:data")
include(":features:feature-workout:domain")
include(":features:feature-workout:ui")

// Feature: calendar / Календарь / Feature: calendar / Календарь
include(":features:feature-calendar:contract")
include(":features:feature-calendar:domain")
include(":features:feature-calendar:ui")

// Feature: weight / Отслеживание веса / Feature: weight / Отслеживание веса
include(":features:feature-weight:contract")
include(":features:feature-weight:data")
include(":features:feature-weight:domain")
include(":features:feature-weight:ui")

// Feature: settings / Настройки / Feature: settings / Настройки
include(":features:feature-settings:contract")
include(":features:feature-settings:domain")
include(":features:feature-settings:ui")

// Feature: exercises / Упражнения / Feature: exercises / Упражнения
include(":features:feature-exercises:contract")
include(":features:feature-exercises:data")
include(":features:feature-exercises:domain")
include(":features:feature-exercises:ui")

// Feature: anatomy / Анатомический атлас / Feature: anatomy / Анатомический атлас
include(":features:feature-anatomy:contract")
include(":features:feature-anatomy:data")
include(":features:feature-anatomy:domain")
include(":features:feature-anatomy:ui")

// Feature: nutrition / Питание / Feature: nutrition / Питание
include(":features:feature-nutrition:contract")
include(":features:feature-nutrition:data")
include(":features:feature-nutrition:domain")
include(":features:feature-nutrition:ui")

// Feature: help / Справка (СРЦ, о приложении) / Feature: help / Справка (СРЦ, о приложении)
include(":features:feature-help:contract")
include(":features:feature-help:domain")
include(":features:feature-help:ui")
