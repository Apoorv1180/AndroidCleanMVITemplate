# Clean Architecture Dependency Flow Diagram

## Visual Representation of Module Dependencies

```
                    ┌─────────────────────────────────────────┐
                    │                 APP MODULE               │
                    │         (Orchestrator/Glue)             │
                    └─────────────────────────────────────────┘
                                      │
                                      │ depends on ALL modules
                                      ▼
                    ┌─────────────────────────────────────────┐
                    │              ALL MODULES                │
                    │  ┌─────────┐ ┌─────────┐ ┌─────────┐    │
                    │  │ core:   │ │ core:   │ │ core:   │    │
                    │  │domain   │ │ data    │ │database │    │
                    │  └─────────┘ └─────────┘ └─────────┘    │
                    │  ┌─────────┐ ┌─────────┐                │
                    │  │ core:   │ │ core:   │                │
                    │  │present- │ │present- │                │
                    │  │ation:   │ │ation:   │                │
                    │  │design-  │ │ui       │                │
                    │  │system   │ └─────────┘                │
                    │  └─────────┘                            │
                    └─────────────────────────────────────────┘
```

## Detailed Dependency Flow

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                              CLEAN ARCHITECTURE LAYERS                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐       │
│  │   PRESENTATION  │    │      DATA        │    │     DOMAIN      │       │
│  │     LAYER       │    │     LAYER        │    │     LAYER       │       │
│  │                 │    │                 │    │                 │       │
│  │ ┌─────────────┐ │    │ ┌─────────────┐ │    │ ┌─────────────┐ │       │
│  │ │core:present │ │    │ │core:data    │ │    │ │core:domain  │ │       │
│  │ │ation:ui     │ │    │ │             │ │    │ │             │ │       │
│  │ └─────────────┘ │    │ └─────────────┘ │    │ └─────────────┘ │       │
│  │        │        │    │        │        │    │        │        │       │
│  │ ┌─────────────┐ │    │ ┌─────────────┐ │    │        │        │       │
│  │ │core:present │ │    │ │core:database│ │    │        │        │       │
│  │ │ation:design │ │    │ │             │ │    │        │        │       │
│  │ │system       │ │    │ └─────────────┘ │    │        │        │       │
│  │ └─────────────┘ │    └─────────────────┘    │        │        │       │
│  └─────────────────┘                           └─────────────────┘       │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

## Module Dependency Matrix

| Module | Depends On | Dependency Type | Clean Architecture Rule |
|--------|------------|-----------------|------------------------|
| **app** | All modules | Orchestrator | App glues everything together |
| **core:domain** | None | Pure Domain | Domain has no dependencies |
| **core:data** | `core:domain`, `core:database` | Data Layer | Data can access Domain + Database |
| **core:database** | `core:domain` | Database Layer | Database can access Domain |
| **core:presentation:designsystem** | `core:domain` | Presentation Layer | Design system can access Domain |
| **core:presentation:ui** | `core:domain`, `core:presentation:designsystem` | Presentation Layer | UI can access Domain + Design System |

## Dependency Flow Visualization

```
                    ┌─────────────────┐
                    │   core:domain   │
                    │   (Pure Domain) │
                    └─────────────────┘
                           ▲
                           │
                    ┌──────┴──────┐
                    │             │
                    ▼             ▼
            ┌─────────────┐ ┌─────────────┐
            │core:database│ │core:present │
            │             │ │ation:design │
            └─────────────┘ │system       │
                    ▲       └─────────────┘
                    │               ▲
                    │               │
                    ▼               ▼
            ┌─────────────┐ ┌─────────────┐
            │ core:data   │ │core:present │
            │             │ │ation:ui     │
            └─────────────┘ └─────────────┘
                    ▲               ▲
                    │               │
                    └───────┬───────┘
                            │
                            ▼
                    ┌─────────────────┐
                    │      app        │
                    │   (Orchestrator)│
                    └─────────────────┘
```

## Key Principles

### ✅ **Clean Architecture Rules Followed:**
1. **Domain Layer**: No dependencies (pure business logic)
2. **Data Layer**: Can access Domain + Database
3. **Presentation Layer**: Can access Domain + other Presentation modules
4. **App Layer**: Orchestrates all modules

### ✅ **Dependency Direction:**
- **Inward**: Dependencies point toward Domain
- **No Circular**: No module depends on itself
- **Layered**: Clear separation between layers

### ✅ **Type-Safe Project Accessors:**
- `projects.core.domain`
- `projects.core.data`
- `projects.core.database`
- `projects.core.presentation.designsystem`
- `projects.core.presentation.ui`

## Benefits of This Structure

1. **Testability**: Domain layer is pure and easily testable
2. **Maintainability**: Clear separation of concerns
3. **Scalability**: Easy to add new modules following the same pattern
4. **Flexibility**: Modules can be developed independently
5. **Clean Dependencies**: No circular references or tight coupling
