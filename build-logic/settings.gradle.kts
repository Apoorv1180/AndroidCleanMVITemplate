// Build Logic Module - Settings
// This file configures the build-logic module as a separate Gradle project
// It enables type-safe project accessors and references the main project's version catalog

// Set the root project name for the build-logic module
rootProject.name = "build-logic"

// Enable type-safe project accessors for better IDE support
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

// Configure dependency resolution to access the main project's version catalog
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    
    versionCatalogs {
        // Reference the main project's version catalog
        // This allows us to use the same dependency versions in our convention plugins
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

// Include the convention module
include(":convention")
