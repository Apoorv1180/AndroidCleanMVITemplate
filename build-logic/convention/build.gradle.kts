// Convention Module - Build Configuration
// This file configures the convention module that contains all our custom plugins
// It sets up the Kotlin DSL and includes all necessary build-time dependencies

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    // Enable Kotlin DSL for writing Gradle plugins
    `kotlin-dsl`
}

// Set the group for our convention plugins
// This will be used as the prefix for all our plugin IDs
group = "com.androidcleanmvitemplate.buildlogic"

// Configure dependencies for build-time only
// These dependencies are only needed during compilation, not at runtime
dependencies {
    // Android Gradle Plugin - needed to configure Android modules
    compileOnly(libs.android.gradlePlugin)
    
    // Kotlin Gradle Plugin - needed to configure Kotlin modules
    compileOnly(libs.kotlin.gradlePlugin)
    
    // Android Tools Common - utility functions for Android builds
    compileOnly(libs.android.tools.common)
    
    // KSP Plugin - needed for Room and other annotation processors
    compileOnly(libs.ksp.gradlePlugin)
    
    // Room Plugin - needed to configure Room database modules
    compileOnly(libs.room.gradlePlugin)
}

// Configure the gradlePlugin block to register our convention plugins
gradlePlugin {
    plugins {
        // Register Android Application Convention Plugin
        register("androidApplication") {
            id = "com.androidcleanmvitemplate.buildlogic.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        
        // Register Android Library Convention Plugin
        register("androidLibrary") {
            id = "com.androidcleanmvitemplate.buildlogic.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
