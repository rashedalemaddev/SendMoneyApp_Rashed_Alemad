# 💸 SendMoneyApp

A simple **money transfer Android app** built with **Kotlin & Jetpack Compose**.  
This project demonstrates **Clean Architecture, MVVM**, and practical Android development skills.

---

## 📸 Screenshots

p align="center">
  <img src="screenshots/Screenshot_20250824_141052.jpg" alt="Login Screen" width="250" style="margin: 10px;"/>
  <img src="screenshots/Screenshot_20250824_141114.jpg" alt="Login Screen" width="250" style="margin: 10px;"/>
  <img src="screenshots/Screenshot_20250824_141141.jpg" alt="Login Screen" width="250" style="margin: 10px;"/>
</p>
<br/>

<p align="center">
  <img src="screenshots/Screenshot_20250824_141158.jpg" alt="Dashboard" width="250" style="margin: 10px;"/>
  <img src="screenshots/Screenshot_20250824_141218.jpg" alt="Profile" width="250" style="margin: 10px;"/>
  <img src="screenshots/Screenshot_20250824_141231.jpg" alt="Settings" width="250" style="margin: 10px;"/>
</p>
<br/>

<p align="center">
  <img src="screenshots/Screenshot_20250824_141243.jpg" alt="Feature A" width="250" style="margin: 10px;"/>
  <img src="screenshots/Screenshot_20250824_141259.jpg" alt="Feature B" width="250" style="margin: 10px;"/>
  <img src="screenshots/Screenshot_20250824_141312.jpg" alt="Feature C" width="250" style="margin: 10px;"/>
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
