# LoopLab

An interactive Android Activity lifecycle explorer built with **Kotlin** and native **Android Views**.

## Features
- On-screen timeline for seven lifecycle callbacks, with short explanations.
- Latest-callback indicator and a clear-history action.
- Scrollable layout and system-bar insets.
- Logcat output using the LoopLab tag.

## Try it
Run the app, press Home, and reopen it. Rotate the device with auto-rotate enabled to observe Activity recreation. The timeline retains up to 40 events while the process lives; it is not permanent storage. Android does not guarantee onDestroy when terminating a process.

## Build
Open this folder in Android Studio, sync Gradle, and run the app module on Android 7.0 / API 24 or later.

## Technology
Kotlin, Android Views, AndroidX AppCompat, Gradle Kotlin DSL.