# Currency Converter Android App
This Android application allows users to convert currencies easily. Users can input an amount in one currency and select another currency to see the equivalent amount.
## Features
- Convert currencies with real-time exchange rates.
- Supports conversion between 160+ currencies.
- Simple and user-friendly interface.
## Exchange Rate API Integration
It utilizes https://www.exchangerate-api.com/ service to fetch real-time exchange rates.
### Implementation details
The integration with the mentioned website is implemented in the `ExchangeRateApiClient` class. This class handles the HTTP request to the API endpoint and parses the response to retrieve the exchange rates and the last update date.
### API key
To use their service, you need to obtain an API key. You can sign up for an API key on the https://www.exchangerate-api.com/ website and replace the apiKey variable in the ExchangeRateApiClient class with your own API key.
## Layout Structure
The `activity_main.xml` layout file defines the user interface for our android app. It consists of following key elements:
- EditText
- Spinners
- TextViews
## Logo Configuration
The app uses custom icons to represent the app. The logo configuration is specified in the `AndroidManifest.xml` file:
The currencylogo_min drawable refers to the app's primary logo image file, while ic_launcher_round is the round version of the launcher icon.
To customize the app's logo, replace the currencylogo_min and ic_launcher_round drawables with your desired image resources.
## Installation
To use this application, follow these steps:
1. Clone the repository
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.
