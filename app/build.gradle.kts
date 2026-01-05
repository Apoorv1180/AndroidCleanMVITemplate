plugins {
    // Apply our custom Android Application convention plugin
    // This replaces manual android {} configuration with standardized settings
    alias(libs.plugins.androidcleanmvitemplate.android.application)
    
    // Keep Compose plugin as it's specific to this module
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.androidcleanmvitemplate"
    
    // Application-specific configuration
    defaultConfig {
        applicationId = "com.androidcleanmvitemplate"
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    // Compose-specific configuration
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Core modules
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.presentation.ui)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}