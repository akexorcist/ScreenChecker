# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

Android-ScreenChecker is a two-module Android project distributed both as a standalone Play Store app and as a reusable library:

- `screenChecker/` — the library module (`com.akexorcist.screenchecker`), published for other apps to embed a "screen info" debug screen. Contains all real logic.
- `app/` — a thin demo/distribution app (`app.akexorcist.checkscreen`) whose `MainActivity` just launches `ScreenCheckerActivity` from the library module. This is what ships to the Play Store.

## Common commands

Build:
```
./gradlew assembleDebug        # debug build, both modules
./gradlew :app:bundleRelease   # release AAB for Play Store (used by the publish workflow)
```

Test:
```
./gradlew test                                   # all unit tests
./gradlew :screenChecker:test                    # unit tests for the library module only
./gradlew connectedAndroidTest                    # instrumented tests (needs a device/emulator)
./gradlew test --tests "com.akexorcist.screenchecker.SomeTestClass"   # single test class
```

Release signing requires `keystore_path`, `keystore_password`, `keystore_key_alias`, `keystore_key_password` in `local.properties` (see `app/build.gradle.kts`); CI (`.github/workflows/*.yml`) injects these from secrets or dummy values.

## Architecture

All the actual screen-inspection logic lives in `screenChecker/src/main/java/com/akexorcist/screenchecker/` and follows one flow:

1. **`ScreenCheckerActivity`** — the entry point (a `ComponentActivity`, view-binding based, edge-to-edge). On create/start it registers a `DisplayManager` listener and a `ViewTreeObserver` global layout listener, then calls `updateScreenInfo()` / `updateAppResolution()` to populate the UI whenever the display or app window changes. It branches on `Build.VERSION.SDK_INT >= M` to switch between a single-resolution layout and a multi-resolution (device vs. current vs. app) layout, since per-window resolution APIs only exist from API 23+.
2. **`ScreenUtility`** — a stateless `object` that is the single source of truth for querying screen/display facts from the OS (resolution in px/dp, density, rotation, HDR/color mode, multitouch capability, supported display modes, current `Display`). Almost every function has an SDK-version branch (`Build.VERSION.SDK_INT >= ...`) because the underlying Android APIs changed across API levels 21–34; when adding a new metric, follow this same "check the SDK version, fall back gracefully" pattern.
3. **Data model classes** (`Resolution`, `DisplayInfo`, `DisplayMode`, `ColorMode`, `HdrType`, `UiMode`, `Multitouch`) — small Parcelable/plain data holders returned by `ScreenUtility`, consumed by `ScreenInfoTextParser`.
4. **`ScreenInfoTextParser`** — pure functions that turn the raw model values from `ScreenUtility` into the human-readable strings shown in the UI (e.g. mapping `Configuration.SCREENLAYOUT_SIZE_*` constants to "Small"/"Normal"/etc.). Keep formatting logic here rather than in the Activity.
5. **`DisplayListenerAdapter`** — adapter/helper for `DisplayManager.DisplayListener` used by the Activity's display-change registration.

The `app` module has no logic of its own beyond `MainActivity` — new features and fixes almost always belong in `screenChecker/`.

Minimum SDK is 21 (14+ historically per README, but `minSdk` is currently 21 in both modules); target/compile SDK is 37. Many code paths must support API 21 through 37, so SDK-version gating is a first-class concern throughout `ScreenUtility` and `ScreenCheckerActivity` — don't add calls to newer APIs without an SDK check and a sensible fallback for older versions. Note `androidx.activity` is pinned to 1.11.0 rather than latest — 1.12.0+ raised its own minSdk to 23, which would break minSdk 21 support.

Uses AGP 9's built-in Kotlin support (no `kotlin("android")` plugin in either module's `plugins {}` block); `kotlin("plugin.parcelize")` in `screenChecker` is unaffected by this and still needs to be applied explicitly. The `screenChecker` module being a library, `targetSdk` was removed from its `defaultConfig` entirely in AGP 9 (only `app`, as the actual application module, declares one) — `testOptions.targetSdk`/`lint.targetSdk` exist as optional replacements for specific sub-purposes but aren't needed here.
