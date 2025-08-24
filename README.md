# 💸 SendMoneyApp

A simple **money transfer Android app** built with **Kotlin & Jetpack Compose**.  
This project demonstrates **Clean Architecture, MVVM**, and practical Android development skills.

---

## 📸 Screenshots

<p align="center">
  <img src="screenshots/Screenshot_20250824_141052.jpg" alt="Login Screen" width="250"/>
  <img src="screenshots/Screenshot_20250824_141114.jpg" alt="Login Screen" width="250"/>
  <img src="screenshots/Screenshot_20250824_141141.jpg" alt="Login Screen" width="250"/>
</p>

<p align="center">
  <img src="screenshots/Screenshot_20250824_141145.jpg" alt="Dashboard" width="250"/>
  <img src="screenshots/Screenshot_20250824_141153.jpg" alt="Send Money" width="250"/>
  <img src="screenshots/Screenshot_20250824_141159.jpg" alt="Providers List" width="250"/>
</p>

<p align="center">
  <img src="screenshots/Screenshot_20250824_141206.jpg" alt="Form Validation" width="250"/>
  <img src="screenshots/Screenshot_20250824_141211.jpg" alt="Confirmation" width="250"/>
  <img src="screenshots/Screenshot_20250824_141233.jpg" alt="Request History" width="250"/>
</p>

<p align="center">
  <img src="screenshots/Screenshot_20250824_141239.jpg" alt="History Details" width="250"/>
  <img src="screenshots/Screenshot_20250824_141246.jpg" alt="Settings" width="250"/>
  <img src="screenshots/Screenshot_20250824_141304.jpg" alt="Logout" width="250"/>
</p>

<p align="center"><i>✨ Click images to view full-size</i></p>

---

## 🎥 Demo Video

<p align="center">
  <a href="https://drive.google.com/file/d/1dHluUua4I2djH5uBd52s_nXoIze1IzWt/view">
    <img src="screenshots/Screenshot_20250824_141052.jpg" alt="Watch Demo" width="300"/>
  </a>
</p>

<p align="center"><i>Click the thumbnail to watch the demo video</i></p>

---

## 🏗 Architecture Overview

This app follows **MVVM with Clean Architecture principles**.

### 🔹 Domain Layer
- Contains **use cases** and core business logic.  
- Example: `GetProvidersImpl`, `SendMoneyUseCaseImpl`, `RequestHistoryUseCaseImpl`.

### 🔹 Data Layer
- Implements **repositories**, remote & local data sources.  
- Example: `LoginRepositoryImpl`, `RequestHistoryRepositoryImpl`.

### 🔹 Presentation Layer
- **UI screens, ViewModels, and state management**.  
- Example: `LoginScreen`, `SendMoneyScreen`, `RequestsHistoryScreen`.  
- Built with **Kotlin + Jetpack Compose**.  

---

## ✨ Features

- 🔑 **User Authentication** (Login)  
- 💸 **Send Money** to providers  
- 📜 **View Transaction History**  
- ✅ **Form Validation & Error Handling**  
- 📱 **Modern UI with Jetpack Compose**  
- ⚡ **Responsive and Clean Architecture**  

---

## 🚀 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/rashedalemaddev/SendMoneyApp_Rashed_Alemad.git
