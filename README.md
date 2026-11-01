# DANA SCHOOL

Native Android app for the Dana School student experience.

## Current milestone
- Native Kotlin + Jetpack Compose Android app
- Persian right-to-left interface
- Home, weekly timetable, tasks, and student profile screens
- Light/dark theme switch
- Custom adaptive launcher icon
- GitHub Actions workflow to build and publish a debug APK artifact

## Build
Open the **Actions** tab and run **Android APK**, or push changes to `main`. The APK is uploaded as the `dana-school-debug-apk` artifact.

## Production requirements
This is currently a UI/demo milestone. Real phone OTP, shared multi-student data, teacher/admin accounts, messaging, grades, online exams, assignment uploads, and push notifications require a secured backend, SMS provider credentials, and verified school data. No fake OTP or simulated shared backend is presented as production functionality.
