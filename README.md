# MS Power & Volume Button 📱⚡

**MS Power & Volume Button** is a lightweight, privacy-focused Android utility application designed to help users lock their screen, open the power menu, and manage volume without relying on physical hardware buttons. 

Whether your physical power/volume buttons are broken, worn out, or simply difficult to reach, this app provides a fast, reliable software-based alternative.

---

## ✨ Key Features

- **🔒 Instant Screen Lock:** Turn off and lock your device screen instantly using Android Device Administrator or Accessibility Service.
- **⚡ Quick Power Menu:** Open the system power menu with a single tap or shortcut.
- **🔊 Volume Control & Management:** Easily access and adjust sound levels and display the system volume UI.
- **🚀 App Shortcuts (Quick Actions):** Long-press the app icon on your home screen for quick shortcuts to the Power Menu, Volume Control, and Settings.
- **🔋 Lightweight & Efficient:** Built with clean native Kotlin and minimal battery overhead.

---

## 🛠️ How It Works & Permissions

To perform global system actions, the app utilizes standard Android system APIs:
1. **Accessibility Service:** Used to trigger global actions like showing the power menu and assisting with screen locking across various Android versions.
2. **Device Administrator:** Used as an optional method to securely and instantly lock the device screen.

---

## 📱 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI:** Android Views (XML & AppCompat)
- **APIs:** AccessibilityService, DevicePolicyManager, ShortcutManager, AudioManager

---

## 📥 Getting Started / Installation

1. Clone or download the repository.
2. Open the project in [Android Studio](https://developer.android.com/studio).
3. Build and run the app on your Android device or emulator (minSdk compatible with modern Android versions).

---

## 🤝 Contributing

Contributions, bug reports, and feature requests are welcome! Feel free to open an issue or submit a pull request.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
