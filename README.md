# Android User List App

This Android application fetches a list of users from a public API, displays them in a list, and allows users to view detailed information about each user.

## Features

*   Fetches user data from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/users).
*   Displays a list of users with their ID, name, and email.
*   Allows users to tap on a list item to view a detailed user screen.
*   Implements pull-to-refresh functionality to update the user list.
*   Handles potential API errors.
*   Implements local caching for offline access.
*   Includes minimal Unit tests

## Technologies Used

*   **Kotlin:** Primary programming language.
*   **Jetpack Compose:** Modern toolkit for building native Android UI.
*   **Ktor:**  Asynchronous HTTP client for making network requests.
*   **Coroutines & Flow:** For asynchronous operations and managing data streams.
*   **MVVM (Model-View-ViewModel):** Architectural pattern for separating UI logic from data.
*   **Hilt:** Dependency Injection library for managing dependencies.
*   **Room:** Persistence library for local data caching.
*   **JUnit:** Testing framework
*   **Mockk:** Mocking framework

## Architecture

The app follows the MVVM architecture:

*   **View (Jetpack Compose UI):** Displays the data and interacts with the ViewModel.
*   **ViewModel:**  Prepares and manages the data for the UI, handles user input, and interacts with the Repository.
*   **Repository:**  Acts as a data source, fetching data from the network (API) or the local database (Room).

## Setup Instructions

1.  **Clone the repository:**

    ```
    git clone https://github.com/Belarussianin/Test_Android_IDF
    ```

2.  **Open the project in Android Studio.**

3.  **Build the project:** `Build` > `Make Project` or `Build` > `Rebuild Project`
    *   Ensure you have the latest Android SDK and build tools installed.

4.  **Run the application:**

    *   Connect an Android device or start an emulator.
    *   Click the "Run" button in Android Studio.

## Usage

Once the app is running:

*  The main screen displays a list of users fetched from the API, showing their ID, name, and email.
*  You can pull down on the list to refresh it, which will trigger a new fetch from the API.
*  Tapping on any user in the list will open a detailed screen displaying more information about that user.

## API Key

This application uses a public API, so no API key is required.

## Error Handling

The application includes basic error handling to display a message if the API is unavailable or if there are network issues.

## Local Caching

The application uses Room to cache the list of users locally. This allows the app to display data even when the device is offline.

## Unit Tests

Unit tests are included for the ViewModels to ensure the logic is working correctly.
