# AGENT.md — SHAKE & FORGET

## 1. Project Overview

You are working on **SHAKE & FORGET**, a colorful, playful Android application designed to help users make random everyday decisions.

Tagline: "Stop overthinking. Let it shake."

The application allows users to create decision lists, organize options into categories, and randomly select an option using interactive smartphone motion sensors.

This is a functional Android application, not a static UI prototype.

Your responsibility is to build a maintainable, functional, and polished Android application while following the existing project design.

---

## 2. Mandatory Design Reference

**IMPORTANT: Read `DESIGN.md` before implementing or modifying any UI components.**

`DESIGN.md` is the primary source of truth for the application's visual identity.

Follow its specifications for:

- Color palette.
- Typography.
- Component styling.
- Spacing.
- Screen layouts.
- Navigation.
- Icons and illustrations.
- Visual hierarchy.
- Interaction patterns.

Rules:

1. Do not arbitrarily redesign existing screens.
2. Do not introduce unrelated colors or typography.
3. Maintain visual consistency across all screens.
4. Reuse existing design components whenever possible.
5. If a design specification is unclear, make the smallest reasonable decision while preserving the established visual identity.
6. Do not replace the supplied design with generic Material Design templates.

The implementation must use native Jetpack Compose components.

Do not use HTML, CSS, or WebView to reproduce the application UI.

---

## 3. Technology Stack

Use the following technologies:

- Programming Language: Kotlin.
- UI Framework: Jetpack Compose.
- Design System: Material 3 where appropriate.
- Architecture: MVVM.
- Database: SQLite through Room.
- Asynchronous Operations: Kotlin Coroutines.
- Reactive State Management: StateFlow.
- Navigation: Navigation Compose.
- Motion Detection: Android SensorManager.
- Minimum Sensor Requirements:
    - Accelerometer.
    - Gyroscope.

Use stable, mutually compatible dependency versions.

Inspect the existing Gradle configuration before adding or updating dependencies.

Do not unnecessarily upgrade the Android Gradle Plugin, Kotlin version, or Gradle wrapper.

---

## 4. Architecture

Implement a clean and maintainable MVVM architecture.

Recommended architecture:

Presentation:
- Jetpack Compose screens.
- Reusable UI components.
- ViewModels.
- UI state classes.

Domain:
- Business logic.
- Decision selection logic.
- Relevant use cases.

Data:
- Room database.
- DAO interfaces.
- Entity models.
- Repository implementations.

Sensors:
- Accelerometer handler.
- Gyroscope handler.
- Sensor state management.

Recommended data flow:

Compose UI
↓
ViewModel
↓
Use Cases / Repository
↓
Room Database
↓
SQLite

Sensor data flow:

Android SensorManager
↓
Sensor Handler
↓
ViewModel
↓
Compose UI

Rules:

- Composables must not directly access the database.
- Composables must not contain business logic.
- ViewModels must not depend on Android Activity instances.
- Use repositories to abstract data sources.
- Expose observable UI state through StateFlow.
- Use immutable UI state where practical.
- Keep sensor lifecycle management outside Composable business logic.

---

## 5. Application Features

Implement the following features.

### 5.1 Home Screen

The main dashboard.

Requirements:
- Application branding.
- Friendly greeting.
- Quick decision entry point.
- Decision categories.
- Recently generated decisions.
- Navigation to other application sections.

### 5.2 Quick Decision

Allow users to quickly generate a random decision.

Requirements:
- Select a category.
- Retrieve available options.
- Randomly select one option.
- Display the selected result.
- Allow users to repeat the selection.

Random selection must use actual application data.

### 5.3 Custom Decision Lists

Allow users to create and manage their own decision lists.

Requirements:
- Create a decision list.
- Edit its name.
- Delete a decision list.
- Add options.
- Edit options.
- Delete options.
- Assign a category.
- Prevent invalid empty lists from being used.

All changes must persist in the database.

### 5.4 Shake to Decide

This is the application's signature feature.

Use the Android Accelerometer through SensorManager.

Requirements:
- Detect intentional shaking movements.
- Implement configurable sensitivity.
- Prevent repeated triggers through a cooldown mechanism.
- Trigger random selection when shaking is detected.
- Provide visual feedback.
- Support manual activation as an alternative.

Do not trigger decisions from normal minor device movements.

Use an appropriate acceleration magnitude calculation and threshold.

### 5.5 Gyroscope Interaction

Use the Android Gyroscope through SensorManager.

Requirements:
- Detect rotational movement.
- Use rotation information for interactive option navigation or highlighting.
- Apply smoothing and appropriate sensitivity thresholds.
- Avoid excessive UI updates.
- Provide a manual interaction alternative.

The gyroscope must have a meaningful functional purpose.

Do not pretend that gyroscope data is being used if the feature is not actually implemented.

### 5.6 Decision Result

Requirements:
- Display the selected option prominently.
- Provide celebratory visual feedback.
- Support selecting again.
- Allow users to confirm their decision.
- Save completed decisions to history.

### 5.7 Decision History

Requirements:
- Display previous decisions.
- Show the selected option.
- Show the associated decision list.
- Show date and time.
- Support deleting individual history entries.
- Support clearing history.

### 5.8 Settings

Requirements:
- Shake sensitivity.
- Shake detection toggle.
- Sound effects toggle.
- Haptic feedback toggle.
- Application information.

Persist appropriate user preferences.

---

## 6. Database Requirements

Use Room Database backed by SQLite.

Recommended entities:

### CategoryEntity

Fields:
- id
- name
- color
- icon

### DecisionSetEntity

Fields:
- id
- title
- categoryId
- createdAt
- updatedAt

### OptionEntity

Fields:
- id
- decisionSetId
- title

### DecisionHistoryEntity

Fields:
- id
- decisionSetId
- selectedOption
- createdAt

Requirements:

- Define appropriate primary keys.
- Implement foreign key relationships.
- Use appropriate indexes.
- Implement DAO interfaces.
- Implement CRUD operations.
- Use Flow for observable database queries.
- Use suspend functions for database writes.
- Handle empty and missing data safely.

Database operations must persist after application restarts.

Do not use hardcoded sample data as a replacement for actual database functionality.

Initial sample categories may be provided through database seeding.

---

## 7. UI Development Guidelines

- Use Kotlin Jetpack Compose exclusively.
- Build reusable composable components.
- Follow the supplied DESIGN.md.
- Use appropriate Compose state management.
- Keep screens modular.
- Avoid excessively large composable functions.
- Implement loading, empty, and error states where appropriate.
- Provide appropriate accessibility descriptions.
- Ensure buttons and interactive elements have meaningful actions.
- Use animations thoughtfully.
- Avoid unnecessary recompositions.

The application must support common Android smartphone screen sizes.

---

## 8. Code Quality

Follow these standards:

- Write readable Kotlin code.
- Use meaningful variable and function names.
- Avoid unnecessary code duplication.
- Avoid giant classes and composables.
- Separate UI, data, and business logic.
- Handle exceptions appropriately.
- Avoid blocking the main thread.
- Avoid unnecessary dependencies.
- Do not leave critical functionality as TODO comments.
- Do not implement fake functionality.

If a feature is incomplete, clearly identify what remains unfinished.

---

## 9. Development Workflow

Work incrementally.

Before modifying the project:

1. Inspect the existing project structure.
2. Read DESIGN.md.
3. Inspect existing Gradle configuration.
4. Identify implemented features.
5. Avoid overwriting existing working code.

Recommended development order:

Phase 1:
- Project setup.
- Dependency configuration.
- Theme implementation.
- Navigation structure.

Phase 2:
- Room database.
- Entities.
- DAOs.
- Repositories.

Phase 3:
- ViewModels.
- UI state management.
- Business logic.

Phase 4:
- Home screen.
- Custom decision lists.
- Categories.
- Decision result.
- History.

Phase 5:
- Accelerometer implementation.
- Gyroscope implementation.
- Sensor lifecycle management.

Phase 6:
- Settings.
- Animations.
- UI refinements.

Phase 7:
- Testing.
- Bug fixing.
- Build verification.

Do not implement everything in one enormous operation.

Complete and verify each phase before proceeding.

---

## 10. Testing and Verification

After implementing changes:

- Verify Kotlin compilation.
- Run Gradle build checks.
- Fix compilation errors.
- Check navigation between screens.
- Verify database CRUD operations.
- Verify persistence after application restart.
- Test sensor availability.
- Test shake detection on a physical Android device.
- Test gyroscope interaction on a physical Android device.
- Verify application behavior when a sensor is unavailable.

Do not claim a feature works unless it has been implemented and appropriately tested.

---

## 11. Git and Project Management

- Preserve existing project files.
- Do not delete user-created assets.
- Do not overwrite DESIGN.md.
- Do not modify unrelated project configurations.
- Keep code organized and GitHub-ready.
- Never commit API keys, passwords, or private credentials.
- Provide clear summaries of implemented changes.

---

## 12. Agent Behavior

Act as a senior Android developer.

Prioritize:
1. Functional correctness.
2. Design consistency.
3. Maintainable architecture.
4. Reliable database operations.
5. Correct sensor implementation.
6. User experience.

When working:

- Explain the implementation briefly.
- Identify files being created or modified.
- Avoid unnecessary complexity.
- Ask for clarification only when a genuinely important requirement is ambiguous.
- Do not silently remove existing functionality.
- Do not assume that generating UI code means the feature is complete.

**Final objective:**

Deliver a fully functional Android application called SHAKE & FORGET using Kotlin, Jetpack Compose, MVVM, SQLite, Accelerometer, and Gyroscope.

The final application must faithfully follow DESIGN.md and provide working features rather than merely attractive screens.