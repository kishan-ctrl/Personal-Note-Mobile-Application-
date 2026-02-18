# Personal Task & Notes App

A simple, secure, and efficient Android application to manage your daily tasks and notes. This project follows modern Android development practices, utilizing the MVVM architecture and local persistent storage.

## 🚀 Features

- **Task Management**: Create, edit, and delete tasks with ease.
- **Completion Tracking**: Mark tasks as completed or pending directly from the list.
- **Persistent Storage**: Uses Room Database to keep your data saved locally on your device.
- **Input Validation**: Ensures tasks have meaningful titles and descriptions before saving.
- **MVVM Architecture**: Clean separation of concerns for better maintainability and testability.
- **State Preservation**: Handles configuration changes (like screen rotation) seamlessly using ViewModels and LiveData.

## 🛠 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- **UI**: XML Layouts with [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- **Material Design**: [Material 3](https://m3.material.io/) components
- **Concurrency**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **Dependency Management**: Gradle Kotlin DSL with [Version Catalogs](https://developer.android.com/build/migrate-to-catalogs)

## 🏗 Project Structure

- **`data/`**: Contains the Room database configuration, Entity models, and the Repository.
- **`ui/`**: 
    - **`list/`**: Task list screen, ViewModel, and RecyclerView Adapter.
    - **`edit/`**: Screen and ViewModel for adding or editing tasks.
- **`util/`**: Utility classes for constants and input validation.

## 🛡 Security & Best Practices

- **Local Only**: All data is stored locally on the device. No external APIs or cloud storage are used, ensuring user privacy.
- **Input Validation**: Strict validation in `Validators.kt` prevents empty or malformed data from entering the database.
- **Context Awareness**: Efficient use of `viewModelScope` to manage asynchronous operations and avoid memory leaks.

## ⚙️ How to Run

1. Clone this repository.
2. Open the project in **Android Studio (Ladybug or newer recommended)**.
3. Sync the project with Gradle files.
4. Run the app on an emulator (e.g., Pixel 6) or a physical device running Android 8.0 (API 26) or higher.

---
*Developed as a personal productivity tool.*
