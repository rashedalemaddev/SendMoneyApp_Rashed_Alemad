# SendMoneyApp

A simple money transfer Android app built with Kotlin.  
This project demonstrates clean architecture, MVVM, and practical Android development skills.

---

## Screenshots

<img src="screenshots/Screenshot_20250824_141052.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141114.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141141.jpg" alt="Login Screen" width="300"/>

<img src="screenshots/Screenshot_20250824_141145.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141153.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141159.jpg" alt="Login Screen" width="300"/>

<img src="screenshots/Screenshot_20250824_141206.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141211.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141233.jpg" alt="Login Screen" width="300"/>

<img src="screenshots/Screenshot_20250824_141239.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141246.jpg" alt="Login Screen" width="300"/>
<img src="screenshots/Screenshot_20250824_141304.jpg" alt="Login Screen" width="300"/>
*Click images to see full-size.*

---

## Demo Video

[<img src="screenshots/video_thumbnail.png" alt="Watch Demo" width="300"/>](videos/demo.mp4)

*Click the thumbnail to watch the demo video.*

---

## Architecture & Description

**Architecture:** MVVM (Model-View-ViewModel) with Clean Architecture principles.  

**Layers:**

1. **Domain Layer:**  
   - Contains use cases and business logic.  
   - Examples: `GetProvidersImpl`, `SendMoneyUseCaseImpl`, `RequestHistoryUseCaseImpl`.

2. **Data Layer:**  
   - Repository implementations, local & remote data sources.  
   - Examples: `LoginRepositoryImpl`, `RequestHistoryRepositoryImpl`.

3. **Presentation Layer:**  
   - UI screens, ViewModels, and state management.  
   - Examples: `LoginScreen`, `SendMoneyScreen`, `RequestsHistoryScreen`.  
   - Uses Kotlin + Jetpack Compose for UI.  

**Features:**

- User login and authentication  
- Send money to providers  
- View request history  
- Form validation and error handling  
- Responsive UI with Compose  

---

## Installation

1. Clone the repo:

```bash
git clone https://github.com/rashedalemaddev/SendMoneyApp_Rashed_Alemad.git



