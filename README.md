# AndroidCleanMVITemplate

## Overview
Educational Android template demonstrating Clean Architecture + MVI pattern through a step-by-step development journey.

## Current Status
- **Phase**: 4 - Build Logic & Convention Plugins (IN PROGRESS)
- **Status**: Base convention plugins implemented, remaining plugins in development
- **Completed**: Phases 0-3 ✅ | Phase 4 (Partial) 🔄
- **Next**: Complete Phase 4 (remaining convention plugins) → Phase 5

## Development Journey
1. **Phase 0**: Project Foundation Setup ✅
2. **Phase 1**: Project Foundation & Planning ✅
3. **Phase 2**: Module Strategy & Structure ✅
4. **Phase 3**: Gradle Management & Build System ✅
5. **Phase 4**: Build Logic & Convention Plugins 🔄 (Base plugins complete)
6. **Phase 5**: Core Utilities & Infrastructure (Next)
7. **Phase 6**: Architecture Patterns Implementation
8. **Phase 7**: Feature Development & Examples

## Technology Stack
- **Architecture**: Clean Architecture + MVI (Model-View-Intent)
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Database**: Room
- **Networking**: Retrofit + OkHttp
- **Async**: Kotlin Coroutines + Flow
- **Build System**: Gradle with Convention Plugins
- **Language**: Kotlin 2.0+

## Getting Started
Follow the phase documentation in the `docs/` folder. Each phase builds upon the previous one, creating a comprehensive learning experience.

## Project Structure
```
AndroidCleanMVITemplate/
├── docs/                    # Phase documentation
├── app/                     # Main application module (uses convention plugin)
├── core/                    # Core modules
│   ├── domain/              # Domain layer (JVM library)
│   ├── data/                # Data layer (Android library)
│   ├── database/            # Database layer (Android library)
│   └── presentation/         # Presentation layer
│       ├── designsystem/    # Design system (Android library)
│       └── ui/              # UI components (Android library)
├── build-logic/             # Convention plugins (Phase 4)
│   └── convention/          # Custom Gradle plugins
├── gradle/                  # Gradle configuration
│   └── libs.versions.toml  # Version catalog
└── README.md               # This file
```

## Phase 4 Progress

### ✅ Completed
- Build-logic module structure
- Android Application Convention Plugin
- Android Library Convention Plugin
- Shared configuration functions (Kotlin, BuildTypes)
- Version catalog access helper
- App module migrated to use convention plugin

### 🔄 In Progress
- Android Application Compose Convention Plugin
- Android Library Compose Convention Plugin
- Android Feature UI Convention Plugin
- Android Room Convention Plugin
- JVM Library Convention Plugin
- Module migrations to convention plugins

## Convention Plugins

The project uses custom Gradle convention plugins to standardize build configuration:

- `androidcleanmvitemplate.android.application` - For Android application modules
- `androidcleanmvitemplate.android.library` - For Android library modules
- More plugins coming in Phase 4 completion...

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Follow the phase documentation in the `docs/` folder
4. Each phase builds upon the previous one, creating a comprehensive learning experience

## Documentation

Comprehensive documentation is available in the `docs/` folder:
- Phase-by-phase implementation guides
- Architecture decisions and rationale
- Development standards and best practices
- Module strategy and dependency management

## Contributing

This is an educational template. Follow the phase-based development approach and ensure all changes are properly documented.
