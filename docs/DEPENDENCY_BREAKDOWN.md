# Dependency Breakdown - Clear Explanation

## Simple Dependency List

### **1. core:domain** 
**Depends on:** ❌ **NOTHING** (Pure domain layer)
- This is the center of our Clean Architecture
- Contains pure business logic
- No external dependencies
- Can be tested independently

### **2. core:database**
**Depends on:** ✅ **core:domain**
- Database layer needs access to domain models
- Can read/write domain entities
- Cannot access data layer or presentation layer

### **3. core:data** 
**Depends on:** ✅ **core:domain** + ✅ **core:database**
- Data layer needs domain models (for repositories)
- Data layer needs database access (for local storage)
- Cannot access presentation layer

### **4. core:presentation:designsystem**
**Depends on:** ✅ **core:domain**
- Design system needs domain models (for UI state)
- Cannot access data layer or database layer
- Pure UI components

### **5. core:presentation:ui**
**Depends on:** ✅ **core:domain** + ✅ **core:presentation:designsystem**
- UI layer needs domain models (for ViewModels)
- UI layer needs design system (for components)
- Cannot access data layer or database layer

### **6. app**
**Depends on:** ✅ **ALL MODULES**
- App module orchestrates everything
- Glues all layers together
- Contains dependency injection setup
- Contains navigation logic

## Visual Dependency Chain

```
core:domain (no dependencies)
    ↑
    │
    ├── core:database
    ├── core:data ──→ core:database
    ├── core:presentation:designsystem
    └── core:presentation:ui ──→ core:presentation:designsystem
                                    ↑
                                    │
                                app (depends on ALL)
```

## Why This Structure?

### **Clean Architecture Rules:**
1. **Domain** = Business logic (no dependencies)
2. **Data** = Data sources (can access Domain + Database)
3. **Presentation** = UI (can access Domain + other Presentation)
4. **App** = Orchestration (accesses everything)

### **Dependency Direction:**
- Dependencies flow **INWARD** toward Domain
- **NO** dependencies flow **OUTWARD** from Domain
- **NO** circular dependencies

## Common Questions & Answers

### **Q: Why can't core:data access core:presentation:ui?**
**A:** This would violate Clean Architecture. Data layer should not know about UI. UI gets data through Domain layer.

### **Q: Why can't core:presentation:ui access core:database?**
**A:** UI should not directly access database. It should go through Domain layer (repositories) for data.

### **Q: Why does core:data depend on core:database?**
**A:** Data layer needs to implement repositories that can save/load data from database.

### **Q: Why does core:presentation:ui depend on core:presentation:designsystem?**
**A:** UI layer uses design system components to build screens consistently.

### **Q: Why does app depend on everything?**
**A:** App module is the orchestrator. It sets up dependency injection, navigation, and glues all layers together.

## Dependency Matrix (Yes/No)

| Module | core:domain | core:data | core:database | core:presentation:designsystem | core:presentation:ui | app |
|--------|-------------|-----------|---------------|--------------------------------|---------------------|-----|
| **core:domain** | ❌ | ❌ | ❌ | ❌ | ❌ | ❌ |
| **core:data** | ✅ | ❌ | ✅ | ❌ | ❌ | ❌ |
| **core:database** | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| **core:presentation:designsystem** | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| **core:presentation:ui** | ✅ | ❌ | ❌ | ✅ | ❌ | ❌ |
| **app** | ✅ | ✅ | ✅ | ✅ | ✅ | ❌ |

## Real-World Example

### **Scenario: User wants to see a list of items**

1. **UI** (`core:presentation:ui`) calls **Domain** (`core:domain`) repository
2. **Domain** repository is implemented in **Data** (`core:data`) layer
3. **Data** layer calls **Database** (`core:database`) to get items
4. **Database** returns domain models to **Data** layer
5. **Data** layer returns domain models to **Domain** layer
6. **Domain** layer returns domain models to **UI** layer
7. **UI** layer uses **Design System** (`core:presentation:designsystem`) to display items

### **Flow:**
```
UI → Domain → Data → Database
     ↑                ↓
     └── Domain ←─────┘
     ↓
   Design System
```

## Key Takeaways

1. **Domain is the center** - everything depends on it, it depends on nothing
2. **Dependencies flow inward** - toward Domain layer
3. **No circular dependencies** - clean, predictable structure
4. **App orchestrates** - glues everything together
5. **Each layer has clear responsibilities** - separation of concerns

This structure makes the code:
- ✅ **Testable** (Domain is pure)
- ✅ **Maintainable** (clear separation)
- ✅ **Scalable** (easy to add features)
- ✅ **Flexible** (independent modules)
