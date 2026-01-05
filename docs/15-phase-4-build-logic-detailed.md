# Phase 4: Build Logic & Convention Plugins - Comprehensive Guide

## 📚 Table of Contents
1. [Understanding Convention Plugins](#understanding-convention-plugins)
2. [Why We Need Convention Plugins](#why-we-need-convention-plugins)
3. [Implementation Strategy](#implementation-strategy)
4. [Incremental Implementation Approach](#incremental-implementation-approach)
5. [Detailed Plugin Explanations](#detailed-plugin-explanations)
6. [Developer Guide: Creating Custom Plugins](#developer-guide-creating-custom-plugins)
7. [Impact & Benefits](#impact--benefits)

---

## 🎯 Understanding Convention Plugins

### **What Are Convention Plugins?**

Convention plugins are **custom Gradle plugins** that encapsulate common build configuration patterns. Instead of repeating the same configuration across multiple modules, we create reusable plugins that apply consistent settings.

### **Key Concepts:**

1. **Included Build**: A separate Gradle project (`build-logic`) that contains only build configuration
2. **Convention Plugin**: A plugin that applies standard configuration patterns
3. **Shared Configuration**: Common settings extracted into reusable functions
4. **Type-Safe Access**: Using `projects.moduleName` instead of hardcoded strings

---

## 🔍 Why We Need Convention Plugins

### **Problem: Configuration Duplication**

**Before Convention Plugins:**
```kotlin
// app/build.gradle.kts
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 26
        targetSdk = 36
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
    buildFeatures {
        compose = true
    }
}

// core/data/build.gradle.kts
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
}

// core/presentation/designsystem/build.gradle.kts
android {
    compileSdk = 36
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlin {
        jvmToolchain(21)
    }
    buildFeatures {
        compose = true
    }
}
```

**Problems:**
- ❌ **Repetitive Code**: Same configuration repeated across modules
- ❌ **Maintenance Nightmare**: Changing SDK version requires updating multiple files
- ❌ **Error-Prone**: Easy to forget updating one module
- ❌ **Inconsistency**: Different modules might have slightly different configurations

### **Solution: Convention Plugins**

**After Convention Plugins:**
```kotlin
// app/build.gradle.kts
plugins {
    alias(libs.plugins.androidcleanmvitemplate.android.application.compose)
}

android {
    namespace = "com.androidcleanmvitemplate"
    defaultConfig {
        applicationId = "com.androidcleanmvitemplate"
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

// core/data/build.gradle.kts
plugins {
    alias(libs.plugins.androidcleanmvitemplate.android.library)
}

// core/presentation/designsystem/build.gradle.kts
plugins {
    alias(libs.plugins.androidcleanmvitemplate.android.library.compose)
}
```

**Benefits:**
- ✅ **DRY Principle**: Configuration defined once, used everywhere
- ✅ **Easy Maintenance**: Change SDK version in one place
- ✅ **Consistency**: All modules use identical configuration
- ✅ **Clean Code**: Module build files focus on module-specific settings

---

## 📖 Implementation Strategy

### **Build Logic Setup**

An included build is a separate Gradle project that contains only build configuration. From Gradle's perspective, our whole app is one project, but the build-logic module is its own isolated place for our own Gradle logic.

**Key Insights:**
1. **Separate Gradle Project**: `build-logic` is its own Gradle project
2. **Build-Specific Dependencies**: Only compile-time dependencies needed
3. **Version Catalog Access**: Must reference main project's version catalog
4. **Plugin Registration**: Each plugin must be registered in `gradlePlugin` block

### **Convention Plugin Implementation**

We can have our very own gradle logic we specify in this conventions module. We will go inside of the source folder and create our very own gradle plugins we can then take and include in our main build, in our main project gradle configuration.

**Implementation Strategy:**
1. **Incremental Approach**: Create one plugin, apply to one module, test
2. **Shared Functions**: Extract common configuration into reusable functions
3. **Plugin Composition**: Build complex plugins by combining simpler ones
4. **Type-Safe Access**: Use `projects.moduleName` for module references

---

## 🔄 Incremental Implementation Approach

### **Why Incremental?**

**Benefits of Incremental Approach:**
1. **Early Error Detection**: Catch issues immediately
2. **Easier Debugging**: Isolate problems to specific plugins
3. **Confidence Building**: Each step validates the previous
4. **Rollback Safety**: Can revert individual changes
5. **Learning Progression**: Understand each plugin before moving on

### **Implementation Order:**

1. **Step 1**: Create build-logic structure → Test build-logic
2. **Step 2**: Create Android Application plugin → Apply to app → Test
3. **Step 3**: Create Android Library plugin → Apply to core:data → Test
4. **Step 4**: Create Android Library Compose plugin → Apply to core:presentation:designsystem → Test
5. **Step 5**: Create JVM Library plugin → Apply to core:domain → Test
6. **Step 6**: Create Android Room plugin → Apply to core:database → Test
7. **Step 7**: Create Android Feature UI plugin → Apply to core:presentation:ui → Test
8. **Step 8**: Create JVM Ktor plugin → Apply to core:data → Test
9. **Step 9**: Create Android Application Compose plugin → Apply to app → Test
10. **Step 10**: Final integration test

---

## 🛠️ Detailed Plugin Explanations

### **1. Android Application Convention Plugin**

**Purpose**: Standardizes Android application module configuration

**What it does:**
- Sets compile SDK to 36
- Configures Java 21 compatibility
- Sets up Kotlin JVM toolchain
- Configures build types (debug/release)

**Why we need it:**
- Every Android app module needs these basic settings
- Ensures consistency across application modules
- Reduces boilerplate in app build files

**How it works:**
```kotlin
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android and Kotlin plugins
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }
            
            // Apply shared configuration
            configureKotlinAndroid()
            configureBuildTypes()
        }
    }
}
```

### **2. Android Library Convention Plugin**

**Purpose**: Standardizes Android library module configuration

**What it does:**
- Sets compile SDK to 36
- Configures Java 21 compatibility
- Sets up Kotlin JVM toolchain
- Configures build types (debug/release)

**Why we need it:**
- All Android library modules need these settings
- Ensures consistency across library modules
- Reduces boilerplate in library build files

**How it works:**
```kotlin
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android and Kotlin plugins
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            
            // Apply shared configuration
            configureKotlinAndroid()
            configureBuildTypes()
        }
    }
}
```

### **3. Android Library Compose Convention Plugin**

**Purpose**: Adds Compose support to Android library modules

**What it does:**
- Inherits from Android Library plugin
- Enables Compose build features
- Adds Compose dependencies
- Configures Compose compiler

**Why we need it:**
- Compose modules need additional configuration
- Ensures consistent Compose setup
- Reduces Compose-specific boilerplate

**How it works:**
```kotlin
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android library plugin
            with(pluginManager) {
                apply("com.androidcleanmvitemplate.buildlogic.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            
            // Add Compose-specific configuration
            configureAndroidCompose()
            addUILayerDependencies()
        }
    }
}
```


### **4. JVM Library Convention Plugin**

**Purpose**: Standardizes pure Kotlin library module configuration

**What it does:**
- Sets Java 21 compatibility
- Configures Kotlin JVM toolchain
- Sets up Java library plugin

**Why we need it:**
- Domain modules are pure Kotlin (no Android dependencies)
- Ensures consistency across JVM modules
- Reduces boilerplate in domain build files

**How it works:**
```kotlin
class JVMLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Java and Kotlin plugins
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }
            
            // Apply shared configuration
            configureKotlin()
        }
    }
}
```


### **5. Android Room Convention Plugin**

**Purpose**: Standardizes Room database module configuration

**What it does:**
- Inherits from Android Library plugin
- Applies Room plugin
- Applies KSP plugin
- Configures Room-specific settings

**Why we need it:**
- Room modules need specific plugin configuration
- Ensures consistent Room setup
- Reduces Room-specific boilerplate

**How it works:**
```kotlin
class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android library plugin
            with(pluginManager) {
                apply("com.androidcleanmvitemplate.buildlogic.android.library")
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }
            
            // Configure Room-specific settings
            configureRoom()
        }
    }
}
```

### **6. Android Feature UI Convention Plugin**

**Purpose**: Standardizes feature presentation module configuration

**What it does:**
- Inherits from Android Library Compose plugin
- Adds feature-specific dependencies
- Configures feature-specific settings

**Why we need it:**
- Feature UI modules have specific requirements
- Ensures consistent feature setup
- Reduces feature-specific boilerplate

**How it works:**
```kotlin
class AndroidFeatureUIConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android library compose plugin
            with(pluginManager) {
                apply("com.androidcleanmvitemplate.buildlogic.android.library.compose")
            }
            
            // Add feature-specific dependencies
            addFeatureUIDependencies()
        }
    }
}
```

### **7. JVM Ktor Convention Plugin**

**Purpose**: Adds Ktor support to JVM library modules

**What it does:**
- Applies Kotlin Serialization plugin
- Adds Ktor dependencies
- Configures Ktor-specific settings

**Why we need it:**
- Networking modules need Ktor configuration
- Ensures consistent Ktor setup
- Reduces Ktor-specific boilerplate

**How it works:**
```kotlin
class JVMKtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply Kotlin Serialization plugin
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }
            
            // Add Ktor dependencies
            addKtorDependencies()
        }
    }
}
```

### **8. Android Application Compose Convention Plugin**

**Purpose**: Adds Compose support to Android application modules

**What it does:**
- Inherits from Android Application plugin
- Enables Compose build features
- Adds Compose dependencies
- Configures Compose compiler

**Why we need it:**
- App modules with Compose need additional configuration
- Ensures consistent Compose setup
- Reduces Compose-specific boilerplate

**How it works:**
```kotlin
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base Android application plugin
            with(pluginManager) {
                apply("com.androidcleanmvitemplate.buildlogic.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            
            // Add Compose-specific configuration
            configureAndroidCompose()
            addUILayerDependencies()
        }
    }
}
```

---

## 👨‍💻 Developer Guide: Creating Custom Plugins

### **When to Create a New Plugin**

**Create a new plugin when:**
1. **Configuration Repetition**: Same configuration appears in 3+ modules
2. **Complex Setup**: Configuration involves multiple plugins and settings
3. **Domain-Specific**: Configuration is specific to a particular domain (e.g., Room, Compose)
4. **Team Standards**: Team wants to enforce specific configuration patterns

### **How to Create a Custom Plugin**

#### **Step 1: Identify the Pattern**

**Example**: You notice that all your feature modules need:
- Android Library + Compose
- Specific dependencies
- Custom build configuration

#### **Step 2: Create the Plugin**

```kotlin
// build-logic/convention/src/main/kotlin/AndroidFeatureConventionPlugin.kt
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply base plugins
            with(pluginManager) {
                apply("com.androidcleanmvitemplate.buildlogic.android.library.compose")
            }
            
            // Add feature-specific configuration
            configureFeatureModule()
        }
    }
}

// Add configuration function
fun Project.configureFeatureModule() {
    extensions.configure<LibraryExtension> {
        // Feature-specific configuration
        buildTypes {
            debug {
                isMinifyEnabled = false
            }
            release {
                isMinifyEnabled = true
            }
        }
    }
    
    dependencies {
        add("implementation", libs.bundles.feature.dependencies)
    }
}
```

#### **Step 3: Register the Plugin**

```kotlin
// build-logic/convention/build.gradle.kts
gradlePlugin {
    plugins {
        register("android.feature") {
            id = "com.androidcleanmvitemplate.buildlogic.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
    }
}
```

#### **Step 4: Add to Version Catalog**

```toml
# gradle/libs.versions.toml
[plugins]
androidcleanmvitemplate-android-feature = { id = "com.androidcleanmvitemplate.buildlogic.android.feature" }
```

#### **Step 5: Apply to Modules**

```kotlin
// feature/auth/presentation/build.gradle.kts
plugins {
    alias(libs.plugins.androidcleanmvitemplate.android.feature)
}
```

### **Best Practices for Plugin Development**

1. **Single Responsibility**: Each plugin should have one clear purpose
2. **Composition**: Build complex plugins by combining simpler ones
3. **Shared Functions**: Extract common configuration into reusable functions
4. **Documentation**: Document what each plugin does and why
5. **Testing**: Test each plugin individually before integration

### **Common Plugin Patterns**

#### **Pattern 1: Base Plugin + Extension**
```kotlin
// Base plugin
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // Apply base configuration
    }
}

// Extension plugin
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // Apply base plugin
        pluginManager.apply("com.androidcleanmvitemplate.buildlogic.android.library")
        // Add Compose-specific configuration
    }
}
```

#### **Pattern 2: Configuration Function**
```kotlin
// Shared configuration function
fun Project.configureKotlinAndroid() {
    extensions.configure<LibraryExtension> {
        compileSdk = 36
        // ... other configuration
    }
}

// Plugin using the function
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            configureKotlinAndroid()
        }
    }
}
```

---

## 🚀 Impact & Benefits

### **Immediate Benefits**

1. **Reduced Boilerplate**: 80% reduction in build file content
2. **Consistency**: All modules use identical configuration
3. **Maintainability**: Change SDK version in one place
4. **Developer Experience**: New modules require minimal configuration

### **Long-term Benefits**

1. **Scalability**: Easy to add new modules
2. **Team Productivity**: Developers focus on business logic, not build configuration
3. **Quality Assurance**: Consistent configuration reduces build-related bugs
4. **Knowledge Transfer**: New team members understand build system quickly

### **Real-World Impact**

**Before Convention Plugins:**
- Adding new module: 50+ lines of configuration
- Updating SDK version: 10+ file changes
- Build file maintenance: High complexity
- Developer onboarding: Steep learning curve

**After Convention Plugins:**
- Adding new module: 5-10 lines of configuration
- Updating SDK version: 1 file change
- Build file maintenance: Low complexity
- Developer onboarding: Smooth learning curve

---

## 📚 Additional Resources

### **Further Reading**
- [Gradle Plugin Development Guide](https://docs.gradle.org/current/userguide/custom_plugins.html)
- [Android Gradle Plugin Documentation](https://developer.android.com/build)
- [Kotlin DSL Documentation](https://docs.gradle.org/current/userguide/kotlin_dsl.html)

### **Learning Path**
1. Understand Gradle plugin system
2. Learn Kotlin DSL syntax
3. Practice with simple plugins
4. Build complex convention plugins
5. Apply to real projects

---

**Next Steps**: Ready to start implementing the build-logic module structure? 🚀
