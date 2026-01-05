// Android Application Convention Plugin
// This plugin standardizes the configuration for Android application modules
// It follows the Runique pattern for convention plugins

import com.android.build.api.dsl.ApplicationExtension
import com.androidcleanmvitemplate.convention.ExtensionType
import com.androidcleanmvitemplate.convention.configureBuildTypes
import com.androidcleanmvitemplate.convention.configureKotlinAndroid
import com.androidcleanmvitemplate.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Convention plugin for Android Application modules
 * 
 * This plugin:
 * - Applies the Android Application plugin
 * - Applies the Kotlin Android plugin
 * - Configures common Android settings using version catalog
 * - Sets up build types (debug/release)
 * 
 * Usage: Apply this plugin to any Android application module
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android and Kotlin plugins
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            
            // Configure Android extension using shared functions
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }
                
                configureKotlinAndroid(this)
                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }
}

