# Heart Rate – Pulse Monitoring App

Heart Rate is an Android app that measures your heart rate using your phone’s camera. Whether you’re tracking your fitness, monitoring your health, or just curious about your pulse, this app provides instant results with a user-friendly interface.

<img src="https://github.com/user-attachments/assets/26fd37bc-3177-482f-80c2-0d114735dc2d" width="240" height="480">
<img src="https://github.com/user-attachments/assets/d7628a8e-aa7b-4dc2-be3f-f8a1ad60f0f7" width="240" height="480">
<img src="https://github.com/user-attachments/assets/ddac978c-82bb-4bc6-90bc-c184b8a5cbed" width="240" height="480">

## DISCLAIMER ⚠️ 

**IMPORTANT:** This application is **NOT A MEDICAL DEVICE** and should not be used for medical purposes or to diagnose health conditions. The heart rate measurement provided by this app is based on the phone's camera and may **NOT BE ACCURATE**. Always consult with a qualified healthcare professional for accurate heart rate measurements and medical advice.

This app is intended for general wellness and fitness purposes only.

## Key Features 🔑

- **Instant Heart Rate Measurement:** Simply place your finger on the camera, and the app will detect your pulse in a matter of seconds.
- **Detailed History:** Keep track of all your past measurements in one place.
- **User-Friendly Design:** Easy-to-navigate interface that guides you through the measurement process and displays results in a clear and concise manner.
- **No Additional Hardware Required:** All you need is your smartphone camera—no extra devices or wearables are necessary.

## How It Works 🧑🏻‍💻

The app utilizes your phone’s camera and flash to detect the blood flow in your finger. When you place your finger on the camera lens, the app analyzes the changes in light absorption and calculates your heart rate.

## Tech Stack 💻

**UI:** Material Design 3, Jetpack Compose, Accompanist, Jetpack Navigation Compose

**Storage:** Room, Room KTX

**Lifecycle & Architecture:** Lifecycle Components, ViewModel Compose

**Dependency Injection:** Hilt, Hilt Navigation Compose

**Camera & Sensors:** CameraX, Camera Lifecycle & View
	
**Architecture:** MVVM (Model-View-ViewModel)

## Installation Instructions 📲

Follow these steps to install the Heart Rate app on your Android device:

### Method 1: Install from GitHub Releases

1. **Download the APK**
   - Go to the [Releases page](https://github.com/6SUPER6SONIC6/HeartRate/releases) of this repository.
   - Find the latest release and download the `HeartRate-release.apk` file. Or [Download directly](https://github.com/6SUPER6SONIC6/HeartRate/releases/download/v1.0.0/HeartRate-release.apk)

2. **Allow Installation from Unknown Sources**
   - Before installing, you may need to allow your device to install apps from unknown sources. To do this:
     - Open your device's **Settings**.
     - Go to **Security** or **Privacy** (this may vary depending on your device).
     - Enable the option to **Install unknown apps** and select the browser or file manager you are using.

3. **Install the APK**
   - Locate the downloaded APK file in your **Downloads** folder or the location where you saved it.
   - Tap on the APK file to begin the installation process.
   - Follow the on-screen instructions to complete the installation.

4. **Launch the App**
   - Once installed, you can find the Heart Rate app icon in your app drawer. Tap on it to launch the app and start measuring your pulse!

### Method 2: Build from Source

If you prefer to build the app from the source code:

1. **Clone the Repository**
   - Open your terminal and run the following command to clone the repository:
     ```bash
     git clone https://github.com/6SUPER6SONIC6/HeartRate.git
     ```

2. **Open the Project in Android Studio**
   - Launch Android Studio and select **Open an Existing Project**.
   - Navigate to the cloned repository and select the project folder.

3. **Sync Gradle and Resolve Dependencies**
   - Once the project is open, Android Studio will automatically start syncing Gradle. Wait for the sync process to complete.
   - Make sure all dependencies are resolved. If not, click on **File** > **Sync Project with Gradle Files**.

4. **Build and Run the App**
   - Connect your Android device or use an emulator.
   - Click on the **Run** button (green triangle) in Android Studio to build and install the app on your device.

5. **Start Measuring!**
   - Once the app is installed, you can launch it from your device and start measuring your heart rate.

### Troubleshooting

- **Installation Blocked?** If your device blocks the installation, check the security settings again to make sure you have allowed installations from unknown sources.
- **Build Errors?** Ensure that you have the latest version of Android Studio and the required SDKs installed. Also, check that your Gradle dependencies are up-to-date.

Enjoy using Heart Rate to monitor your pulse with ease!

## Feedback ✉️

If you have any feedback, please reach out to me at vadym.tantsiura@gmail.com or [Telegram](http://t.me/VTantsiura)

![Logo](https://github.com/user-attachments/assets/7c151081-692f-424a-96c5-9398e484304e)
