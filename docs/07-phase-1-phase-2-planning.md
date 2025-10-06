# 📋 **Phase 2: Module Strategy & Structure - Planning Document**

## 📋 **Overview**

This document outlines the detailed planning for Phase 2: Module Strategy & Structure, building upon the foundation established in Phase 1.

## 🎯 **Phase 2 Objectives**

### **Primary Goals**
1. **Create Core Module Structure**: Establish the foundational module architecture
2. **Implement Basic Dependencies**: Set up module relationships and dependencies
3. **Create Simple Examples**: Implement basic use cases to demonstrate concepts
4. **Establish Module Boundaries**: Define clear separation of concerns

### **Learning Outcomes**
- **Beginner**: Understand basic module structure and dependencies
- **Intermediate**: Implement Clean Architecture layers
- **Advanced**: Optimize module organization and dependencies

## 📋 **Phase 2 Deliverables**
- [ ] Core module structure created → [09-phase-2-modules.md]
- [ ] Basic module configurations → [09-phase-2-modules.md#module-configuration]
- [ ] Package structure established → [09-phase-2-modules.md#package-structure]
- [ ] Module dependency graph implemented → [09-phase-2-modules.md#module-dependencies]
- [ ] Basic Android configurations → [09-phase-2-modules.md#android-configuration]

## 🏗️ **Module Implementation Plan**

### **Step 1: Create Core Module Structure**
**Duration**: 2-3 days
**Deliverables**:
- [ ] `core:domain` module created (Kotlin Library)
- [ ] `core:data` module created (Android Library)
- [ ] `core:presentation` module created (Android Library)
- [ ] `core:presentation:designsystem` module created (Android Library)
- [ ] `core:presentation:ui` module created (Android Library)
- [ ] `core:database` module created (Android Library)
 

**Implementation Details**:
- **Use Android Studio's "New Module" wizard** - not manual directory creation , Switch to Project view and click on root module.
- Hit New -> Module
- **Choose correct module types**:
  - **Kotlin Library** for `core:domain` (pure Kotlin, no Android dependencies)
  - **Android Library** for all other modules
- **Set proper package names** for each module:
  - `com.androidcleanmvitemplate.core.domain`
  - `com.androidcleanmvitemplate.core.data`
  - `com.androidcleanmvitemplate.core.presentation`
  - `com.androidcleanmvitemplate.core.presentation.designsystem`
  - `com.androidcleanmvitemplate.core.presentation.ui`
  - `com.androidcleanmvitemplate.core.database`
 
- **Let Gradle sync** after each module creation

### **Step 2: Configure Module Dependencies**
**Duration**: 1-2 days
**Deliverables**:
- [ ] Module dependencies configured using `implementation project()`
- [ ] Type-safe project accessors enabled
- [ ] Clean Architecture dependency rules followed

**Dependency Rules**:
- **Domain modules**: Can't depend on other modules
- **Data modules**: Can depend on domain and other data modules
- **Presentation modules**: Can depend on domain and data modules
- **App module**: Can depend on all modules (glues everything together)

**Implementation Details**:
- Use `implementation project(":core:domain")` for module references
- Enable type-safe project accessors in `settings.gradle.kts`
- Follow Clean Architecture dependency flow: UI → Domain ← Data

**Networking Strategy (Default Approach):**
- Keep networking (API interfaces, DTOs, Retrofit services, remote data sources) inside each feature's `:data` module (e.g., `auth:data`).
- This is the recommended default structure for most projects.

**Evolution Path (When Needed):**
- If multiple features share identical client plumbing (interceptors, auth refresh, error handling), consider extracting a shared client-only module later (e.g., `core:networking`) for Retrofit/OkHttp setup only.
- **Never extract feature-specific APIs/DTOs into core** - keep them in feature modules.
- **Example evolution**: Start with `auth:data` containing auth APIs, then extract shared client to `core:networking` if auth and user features need identical networking setup.

### **Step 3: Implement Basic Examples**
**Duration**: 3-4 days
**Deliverables**:
- [ ] Simple domain entities (User, Sample)
- [ ] Basic use cases (GetUserUseCase, GetAllSamplesUseCase)
- [ ] Repository interfaces
- [ ] Simple ViewModels
- [ ] Basic UI components

**Example Implementation**:
- **Domain**: `User`, `Sample`, `UserRepository`, `SampleRepository`, `GetUserUseCase`, `GetAllSamplesUseCase`
- **Data**: `UserRepositoryImpl`, `SampleRepositoryImpl`, `UserDataSource`, `SampleDataSource`
- **Presentation**: `UserViewModel`, `SampleViewModel`, `UserScreen`, `SampleScreen`
- **Database**: `UserEntity`, `SampleEntity`, `UserDao`, `SampleDao`
- **Networking**: `UserApi`, `SampleApi`, `UserDto`, `SampleDto`

### **Step 4: Establish Module Boundaries**
**Duration**: 1-2 days
**Deliverables**:
- [ ] Clear package structure
- [ ] Dependency rules enforced
- [ ] Module documentation
- [ ] Examples of proper usage

**Package Structure**:
```
core/domain/src/main/kotlin/com/androidcleanmvitemplate/core/domain/
├── entities/
├── repositories/
├── usecases/
└── exceptions/

core/data/src/main/kotlin/com/androidcleanmvitemplate/core/data/
├── repositories/
├── datasources/
└── mappers/

core/presentation/src/main/kotlin/com/androidcleanmvitemplate/core/presentation/
├── viewmodels/
├── ui/
└── events/

core/database/src/main/kotlin/com/androidcleanmvitemplate/core/database/
├── entities/
├── daos/
└── database/
```

### **Module Responsibilities and Rationale**
- **core:domain**: Pure Kotlin business rules shared across the app. No external or Android dependencies. Reason: enforce clean boundaries and reuse business logic everywhere.
- **core:data**: Shared data utilities (only if truly shared), such as common mappers or data policies. Reason: avoid duplication across features; keep feature data orchestration in feature modules.
- **core:presentation** (+ `designsystem`, `ui`): Shared UI toolkit and design system. Reason: provide consistent UI components and utilities across features.
- **core:database**: Central Room setup (DB, DAOs, entities, migrations) and DB utilities reused by multiple features. Reason: persistence is cross-cutting and benefits from a single source of truth.
- **feature:domain** (e.g., `auth:domain`): Feature-specific business contracts (use cases, models, repositories). Reason: isolate feature business rules and enable independent evolution/testing.
- **feature:data** (e.g., `auth:data`) — includes networking:
  - Orchestrates local (database) and remote (network) sources.
  - Contains repositories, data sources, DTOs, API services, and mappers to domain.
  - Reason: keep feature data logic cohesive and scoped to the feature; avoids premature cross-feature coupling.
- **feature:presentation** (e.g., `auth:presentation`): Compose UI, ViewModels, navigation. Reason: keep UI concerns separate and depend only on `feature:domain` (and sometimes `core:presentation`).

Difference between data module and database module:
- **Database module**: Owns the persistence mechanism (Room DB, DAOs, entities, migrations) and shared DB utilities. It does not orchestrate feature logic.
- **Data module**: Owns feature data orchestration (repositories, local/remote data sources, mappers). It consumes the database module and networking, translating to/from domain.

## 📊 **Detailed Implementation Steps**

### **Day 1: Module Creation**
1. **Use Android Studio's "New Module" wizard**
   - Right-click on root project → New Module
   - Choose appropriate module type (Kotlin Library for domain, Android Library for others)
   - Set proper package names
   - Let Gradle sync after each creation

2. **Create modules in order**:
   - `core:domain` (Kotlin Library)
   - `core:data` (Android Library)
   - `core:presentation` (Android Library)
   - `core:presentation:designsystem` (Android Library)
   - `core:presentation:ui` (Android Library)
   - `core:database` (Android Library)

### **Day 2: Domain Module**
1. **Create Domain Entities**
   - Simple data classes (`User`, `Sample`)
   - Basic business models
   - Domain-specific exceptions

2. **Implement Use Cases**
   - Basic use case interfaces (`GetUserUseCase`, `GetAllSamplesUseCase`)
   - Simple business logic
   - Error handling

3. **Define Repository Interfaces**
   - Data access contracts (`UserRepository`, `SampleRepository`)
   - Business logic interfaces
   - Dependency inversion

### **Day 3: Data Module**
1. **Implement Repository**
   - Repository implementation (`UserRepositoryImpl`, `SampleRepositoryImpl`)
   - Data source interfaces (`UserDataSource`, `SampleDataSource`)
   - Data mapping

2. **Create Data Sources**
   - Local data source (Room database)
   - Remote data source (Retrofit API)
   - Data synchronization

3. **Add Dependencies**
   - Room database
   - Retrofit networking
   - Hilt dependency injection

### **Day 4: Presentation Module**
1. **Create ViewModels**
   - Basic ViewModel structure (`UserViewModel`, `SampleViewModel`)
   - State management (MVI pattern)
   - Action handling

2. **Implement UI Components**
   - Simple Compose screens (`UserScreen`, `SampleScreen`)
   - State observation
   - User interaction handling

3. **Add Navigation**
   - Basic navigation setup
   - Screen routing
   - Parameter passing

### **Day 5: Database & Networking**
1. **Database Module**
   - Room entities (`UserEntity`, `SampleEntity`)
   - DAO interfaces (`UserDao`, `SampleDao`)
   - Database configuration

2. **Networking in Feature Data**
   - Implement API service interfaces inside the feature's `:data` module (e.g., `auth:data`)
   - Define DTOs/models and remote data sources in `:data`
   - Configure Retrofit/OkHttp within `:data` (or a shared client later if needed)

3. **Integration**
   - Connect all modules
   - Test basic functionality
   - Fix any issues

## 🎯 **Success Criteria**

### **Technical Success**
- [ ] All modules compile successfully
- [ ] Dependencies are properly configured using `implementation project()`
- [ ] Basic examples work end-to-end
- [ ] No circular dependencies
- [ ] Clean Architecture principles followed
- [ ] Type-safe project accessors enabled

### **Educational Success**
- [ ] Clear module structure
- [ ] Understandable examples
- [ ] Comprehensive documentation
- [ ] Learning progression maintained
- [ ] Best practices demonstrated

### **Quality Success**
- [ ] Code follows established standards
- [ ] Proper error handling
- [ ] Basic testing implemented
- [ ] Documentation complete
- [ ] Performance considerations

## 📋 **Risk Mitigation**

### **Potential Risks**
1. **Complexity Overload**: Too many modules at once
2. **Dependency Issues**: Circular dependencies
3. **Build Problems**: Configuration errors
4. **Learning Curve**: Too steep for beginners

### **Mitigation Strategies**
1. **Incremental Approach**: Build modules one at a time using Android Studio wizard
2. **Clear Documentation**: Explain each step
3. **Simple Examples**: Start with basic functionality
4. **Regular Testing**: Test after each step
5. **Community Feedback**: Get input from users
6. **Use Android Studio Tools**: Leverage built-in module creation wizard

## 🚀 **Next Phase Preparation**

### **Phase 3 Readiness**
- [ ] Module structure established
- [ ] Basic functionality working
- [ ] Dependencies configured
- [ ] Examples implemented
- [ ] Documentation complete

### **Phase 3 Dependencies**
- [ ] Version catalog setup (`libs.versions.toml`)
- [ ] Dependency management
- [ ] Module dependency configuration
- [ ] Type-safe project accessors
- [ ] Build optimization

## 📚 **Learning Resources**

### **Documentation**
- [Module Strategy](../03-phase-1-module-strategy.md)
- [Architecture Decisions](../02-phase-1-architecture-decisions.md)
- [Development Standards](../04-phase-1-development-standards.md)
- [Master Checklist](../05-phase-1-checklist.md)

### **External Resources**
- [Android Documentation](https://developer.android.com/guide)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [MVI Pattern](https://developer.android.com/jetpack/guide/ui-layer/stateholders)
- [Gradle Documentation](https://docs.gradle.org/current/userguide/userguide.html)
- [Philipp Lackner's Module Creation Tutorial](https://www.youtube.com/@PhilippLackner)

## 📋 **Phase 2 Checklist**

### **Module Creation**
- [ ] Use Android Studio's "New Module" wizard
- [ ] Create `core:domain` module (Kotlin Library)
- [ ] Create `core:data` module (Android Library)
- [ ] Create `core:presentation` module (Android Library)
- [ ] Create `core:presentation:designsystem` module (Android Library)
- [ ] Create `core:presentation:ui` module (Android Library)
- [ ] Create `core:database` module (Android Library)
- [ ] Set proper package names for each module
- [ ] Let Gradle sync after each creation

### **Domain Module**
- [ ] Create domain entities (`User`, `Sample`)
- [ ] Implement use cases (`GetUserUseCase`, `GetAllSamplesUseCase`)
- [ ] Define repository interfaces (`UserRepository`, `SampleRepository`)
- [ ] Add domain-specific exceptions

### **Data Module**
- [ ] Implement repository (`UserRepositoryImpl`, `SampleRepositoryImpl`)
- [ ] Create data sources (`UserDataSource`, `SampleDataSource`)
- [ ] Add data mapping
- [ ] Configure dependencies

### **Presentation Module**
- [ ] Create ViewModels (`UserViewModel`, `SampleViewModel`)
- [ ] Implement UI components (`UserScreen`, `SampleScreen`)
- [ ] Add navigation
- [ ] Handle user interactions

### **Database & Networking**
- [ ] Create database entities (`UserEntity`, `SampleEntity`)
 - [ ] Implement API services inside feature `:data` (e.g., `auth:data`)
 - [ ] Configure Retrofit/OkHttp in feature `:data`
- [ ] Test integration

### **Quality Assurance**
- [ ] Test all modules
- [ ] Verify dependencies
- [ ] Check architecture compliance
- [ ] Update documentation

---

## 🚀 **Continue Reading**

**Next Document**: [09-phase-2-modules.md](09-phase-2-modules.md) - Begin Phase 2 implementation with detailed module creation steps.

**Reading Flow**: Vision → Architecture Decisions → Module Strategy → Development Standards → Foundation → Phase 2 Planning → Implementation

## 🎯 **Ready to Begin Phase 2**

**Phase 2 is now fully planned and ready for implementation. The detailed steps, success criteria, and risk mitigation strategies are in place to ensure a successful module structure implementation.**

**Next Step**: Use Android Studio's "New Module" wizard to create modules properly, following the step-by-step approach outlined in this document.
