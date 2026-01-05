// Android Library Convention Plugin
// This plugin standardizes the configuration for Android library modules
// It follows the Runique pattern for convention plugins

import com.android.build.api.dsl.LibraryExtension
import com.androidcleanmvitemplate.convention.ExtensionType
import com.androidcleanmvitemplate.convention.configureBuildTypes
import com.androidcleanmvitemplate.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * Convention plugin for Android Library modules
 * 
 * This plugin:
 * - Applies the Android Library plugin
 * - Applies the Kotlin Android plugin
 * - Configures common Android settings using version catalog
 * - Sets up build types (debug/release)
 * - Adds test dependencies
 * 
 * Usage: Apply this plugin to any Android library module (core modules)
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android and Kotlin plugins
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            
            // Configure Android extension using shared functions
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY
                )
                
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }
            }
            
            // Add test dependencies
            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }
}
