# Todo2021
New ToDo app with clean code and coroutines
This is a simple Android app for managing your TODO list. It follows the MVVM architecture pattern and is implemented using Kotlin.

## Features

- Add, edit, and delete tasks.
- Mark tasks as completed.
- View a list of all tasks or filter by completed and active tasks.
- Data persistence using a local database.

## Prerequisites

- Android Studio
- Kotlin

## Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern.

Model: Represents the data and business logic of the app. In this app, it includes the data entities and the repository for managing data operations.

View: Represents the UI of the app. It includes activities, fragments, and layouts.

ViewModel: Acts as a bridge between the Model and View. It contains the UI-related logic and communicates with the repository to get and save data.

## Libraries Used
Android Architecture Components (ViewModel, LiveData, Room)
Kotlin Coroutines
Contributing
If you would like to contribute to the project, feel free to submit a pull request.

## License
This project is licensed under the MIT License - see the LICENSE.md file for details.

## Acknowledgments
Inspired by Android Architecture Blueprints
Thanks to the Kotlin and Android communities for their valuable resources.
