package com.androidcleanmvitemplate.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog

/**
 * Provides access to the version catalog in convention plugins
 * 
 * This extension property allows convention plugins to access the version catalog
 * that is defined in the main project's gradle/libs.versions.toml file.
 * 
 * Usage:
 * ```kotlin
 * val version = libs.findVersion("projectCompileSdkVersion").get().toString()
 * val library = libs.findLibrary("desugar-jdk-libs").get()
 * ```
 */
internal val Project.libs: VersionCatalog
    get() = extensions.getByType<org.gradle.api.artifacts.VersionCatalogsExtension>().named("libs")

