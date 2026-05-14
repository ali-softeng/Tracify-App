#  Tracify

Tracify is a real-time rider tracking system that enables live location monitoring using Firebase Firestore and Google Maps SDK. It provides continuous location updates with low latency and smooth map visualization, making real-time tracking simple and reliable.

---

##  Features

-  **Real-Time Location Tracking**  
  Live rider location updates using Firebase Firestore snapshot listeners.

-  **Interactive Map View**  
  Google Maps integration with smooth marker movement and responsive UI.

-  **Live Data Sync**  
  Fetch and update rider information instantly using unique rider IDs.

-  **Safe UI Handling**  
  View Binding ensures clean and null-safe UI interactions.

-  **Data Validation**  
  Proper checks to handle missing or invalid Firestore data safely.

---

##  Tech Stack

- **Language:** Java  
- **Architecture:** MVVM (Model-View-ViewModel)  
- **Backend:** Firebase Firestore (Real-time NoSQL Database)  
- **Authentication:** Firebase Auth  
- **Maps:** Google Maps SDK for Android  
- **UI:** View Binding  

---

##  Core Components

- Google Maps & Location Services – Handles map rendering and coordinate plotting  
- Firebase Firestore – Provides real-time data synchronization  
- View Binding – Simplifies UI handling without `findViewById`  
- SupportMapFragment – Manages map lifecycle within activity  

---

##  Setup Instructions

1. **Clone the Repository**  
   Download or clone the project to your local machine.

2. **Firebase Setup**  
   - Create a Firebase project  
   - Register your Android app (`com.example.tracify`)  
   - Download `google-services.json` and place it inside the `app/` folder  
   - Enable Firestore Database and Firebase Authentication  

3. **Google Maps Setup**  
   - Get API key from Google Cloud Console  
   - Enable Maps SDK for Android  
   - Add API key in `AndroidManifest.xml` or `secrets.properties`

4. **Firestore Setup**  
   - Create a collection named `locations`  
   - Use `riderID` as the document ID  

5. **Run the Project**  
   Sync Gradle and run the app on emulator or physical device.

---

##  Future Improvements

-  Draw rider movement path using Polyline  
-  ETA calculation using Google Directions API  
-  Push notifications for arrival alerts  
-  Trip history tracking and analytics  

---

##  Screenshots

 coming soon

---

##  About This Project

This project demonstrates:
- Real-time cloud data synchronization  
- Clean MVVM architecture  
- Google Maps integration in Android  
- Production-level Android development practices  
