package com.androidcleanmvitemplate.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configures common Android settings for both application and library modules
 * This follows the Runique pattern of using CommonExtension
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        // Use version catalog for SDK versions
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
        
        defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()
        
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
    }
    
    configureKotlin()
    
    dependencies {
        "coreLibraryDesugaring"(libs.findLibrary("desugar-jdk-libs").get())
    }
}

/**
 * Configures Kotlin compiler options
 * This ensures consistent Kotlin compilation across all modules
 */
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

