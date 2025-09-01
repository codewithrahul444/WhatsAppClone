# WhatsApp Clone 📱

A modern WhatsApp clone built with **Kotlin**, **Jetpack Compose**, and **Firebase**.

## ✨ Features

- 💬 Real-time messaging
- 👥 Chat list with user management
- 🔔 Push notifications via FCM
- 🎨 Modern Material Design 3 UI
- 📱 Responsive design
- 🔒 Firebase Authentication
- ☁️ Cloud Firestore database

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Repository Pattern
- **Dependency Injection**: Dagger Hilt
- **Backend**: Firebase (Auth, Firestore, FCM)
- **Database**: Room (local caching)
- **Navigation**: Compose Navigation

## 📋 Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.8+
- Android SDK 24+
- Firebase project setup

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/codewithrahul444/WhatsAppClone.git
   cd WhatsAppClone
   ```

2. **Firebase Setup**
   - Follow the instructions in [FIREBASE_SETUP.md](FIREBASE_SETUP.md)
   - Add your `google-services.json` file to the `app/` directory

3. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```

## 📁 Project Structure

```
app/
├── src/main/java/com/example/whatsappclone/
│   ├── data/
│   │   ├── database/     # Room database
│   │   ├── model/        # Data models
│   │   └── repository/   # Repository pattern
│   ├── di/              # Dependency injection
│   ├── service/         # FCM service
│   ├── ui/
│   │   ├── navigation/  # Navigation setup
│   │   ├── screens/     # Compose screens
│   │   ├── theme/       # Material Design theme
│   │   └── viewmodel/   # ViewModels
│   ├── MainActivity.kt
│   └── WhatsAppApplication.kt
```

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Code With Rahul**
- GitHub: [@codewithrahul444](https://github.com/codewithrahul444)
- Email: codewithrahul444@gmail.com

## 🙏 Acknowledgments

- Firebase for backend services
- Jetpack Compose for modern UI
- Material Design for beautiful components
