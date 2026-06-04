

https://github.com/user-attachments/assets/847352dc-8b8f-4499-8b41-c39165ee2f77



# LAB17_MaitriseBroadcastReceiver

> **Course: Mobile Programming — Android with Java**  
> **Lab 17: Mastering BroadcastReceivers**

---

## Project Description

**ReceiverDemo** is a demo Android application that illustrates the complete workings of `BroadcastReceiver` in Java. It covers both receiver types (static and dynamic), system broadcasts, and custom intra-app broadcasts.

---

## Features

| Feature | Receiver Type | Trigger |
|---|---|---|
| Airplane Mode detection | Dynamic | `ACTION_AIRPLANE_MODE_CHANGED` |
| Phone boot detection | Static | `ACTION_BOOT_COMPLETED` |
| Custom broadcast | Dynamic / Manifest | Custom action `CUSTOM_EVENT` |

---

## Project Structure

```
ReceiverDemo/
├── app/
│   ├── manifests/
│   │   └── AndroidManifest.xml          ← Permissions + receiver declarations
│   └── java/com/example/receiverdemo/
│       ├── MainActivity.java            ← Main Activity + dynamic receiver
│       ├── AirplaneModeReceiver.java    ← Dynamic receiver (Airplane Mode)
│       ├── BootReceiver.java            ← Static receiver (Boot)
│       └── CustomEventReceiver.java    ← Receiver for custom broadcasts
└── res/
    └── layout/
        └── activity_main.xml            ← User interface
```

---

## Requirements

- **Android Studio** Hedgehog (2023.1) or newer
- **JDK 17**
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34+ (Android 14)
- Emulator or physical Android device

---

## Installation & Launch

```bash
# 1. Clone or download the project
# 2. Open in Android Studio: File → Open → ReceiverDemo folder
# 3. Wait for Gradle Sync to complete
# 4. Run with Run ▶ or Shift+F10
```

---

## How to Test

### Test 1 — Dynamic Receiver (Airplane Mode)
1. Launch the application
2. Tap **"Enable Airplane Receiver"**
3. Go to **System Settings** → toggle Airplane Mode on/off
4. ✅ A Toast appears showing the airplane mode state

### Test 2 — Custom Broadcast
1. Tap **"Send Custom Broadcast"**
2. ✅ Two Toasts appear: one for sending, one for receiving

### Test 3 — Static Receiver (Boot)
```bash
# Via ADB in the Android Studio terminal
adb reboot
```
1. Wait for the emulator/device to restart
2. ✅ A "Phone started" Toast appears on launch

---

## Key Concepts

### Dynamic Receiver
- Registered via `registerReceiver()` inside the Activity
- Unregistered via `unregisterReceiver()` in `onDestroy()`
- Only active **while the Activity is alive**
- ✅ Recommended for most use cases (battery-friendly)

### Static Receiver
- Declared in `AndroidManifest.xml`
- Active **even if the app is not running**
- Reserved for critical system events (`BOOT_COMPLETED`, etc.)
- ⚠️ Restricted since Android 8+ (background limits)

### Android 12+ Security Rules
- `android:exported="false"` is mandatory on all receivers that don't need to be accessible from other apps
- Always clean up receivers in `onDestroy()` to avoid memory leaks

---

## Required Permissions

```xml
<!-- In AndroidManifest.xml -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```

---

## Best Practices

- **Never do heavy work inside `onReceive()`** — it runs on the main thread
- **Always call `unregisterReceiver()`** to avoid memory leaks
- **Use `LocalBroadcastManager`** for strictly intra-app communication (more secure)
- **Prefer dynamic receivers** over static ones whenever possible
- **Always use `exported="false"`** unless absolutely necessary

---

## Author

Project completed as part of the **Mobile Programming: Android with Java** course — Lab 17.
