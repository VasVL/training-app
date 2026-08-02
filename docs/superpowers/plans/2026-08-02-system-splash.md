# System Splash Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use `executing-plans` to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Show the system splash screen until the initial user route is known, then open either the first-profile form or the main screen without a visible intermediate screen.

**Architecture:** `MainActivity` owns the one-time startup check because it owns the root `NavController`. AndroidX SplashScreen is installed before `super.onCreate()` and held by a lightweight boolean until `UserRepository.getDefault()` completes. The existing profile and main fragments continue to render their own states independently.

**Tech Stack:** AndroidX Core SplashScreen 1.0.1, Kotlin, Hilt, Navigation Component, XML resources.

## Global Constraints

- Support minSdk 24 and targetSdk 34.
- Use `androidx.core:core-splashscreen:1.0.1`.
- Do not add a `SplashFragment` or a new navigation destination.
- Do not use TDD; tests are added only after the implementation files are agreed and completed.
- Keep the first-profile route outside the back stack of the temporary main destination.

---

### Task 1: Add and configure the system splash screen

**Files:**

- Modify: `Android/gradle/libs.versions.toml`
- Modify: `Android/app/build.gradle.kts`
- Modify: `Android/app/src/main/res/values/themes.xml`
- Modify: `Android/app/src/main/AndroidManifest.xml`
- Modify: `Android/app/src/main/java/com/vasev/trainingapp/MainActivity.kt`

**Consumes:** `UserRepository.getDefault(): suspend () -> User?`, existing `NavGraphDirections.actionGlobalUserEditFragment(UserEditRequest)`.

**Produces:** A visible system splash screen while the active profile is checked; initial routing to `CreateFirstUser` or `MainScreen.Main` without a main-screen flash.

- [ ] Add `splashscreen = "1.0.1"` and a `core-splashscreen` library alias to the version catalog; add `implementation(libs.core.splashscreen)` to the app module.

- [ ] Create `Theme.TrainingApp.Starting` with parent `Theme.SplashScreen`, graphite background, the app icon as animated splash icon, and `postSplashScreenTheme` set to `Theme.TrainingApp`.

- [ ] Apply `Theme.TrainingApp.Starting` only to `MainActivity` in the manifest.

- [ ] In `MainActivity.onCreate`, call `installSplashScreen()` before `super.onCreate()`. Hold it with `setKeepOnScreenCondition` while the initial repository query is active, then release it on both success and error.

- [ ] Keep the existing first-profile routing with `popUpTo(mainFragment, inclusive = true)`; log the database-read failure and release the splash so the existing main destination remains available rather than leaving the application blocked.

- [ ] Run:

```bash
./gradlew --no-daemon --console=plain :app:assembleDebug
```

Expected: `BUILD SUCCESSFUL`.

- [ ] After all implementation files are agreed, add focused tests for startup routing if the current test setup can construct `MainActivity` with a fake `UserRepository`; otherwise record manual verification in the feature documentation and defer tests until the Activity dependencies are testable without production Hilt bindings.

- [ ] Ask for permission, then commit all files from this task with message:

```bash
git commit -m "Add system splash screen"
```
