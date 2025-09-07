# Little Lemon Restaurant App

A modern Android app built with **Jetpack Compose** and **Firebase** as part of the **Meta Android Developer** specialization on Coursera.  
The app simulates a food ordering experience at the Little Lemon restaurant, with features like authentication, menu browsing, cart management, orders, and reservations.

## Features

- **User Authentication**
  - Sign up, log in, and log out using **Firebase Authentication**
  - Email verification before login

- **Menu & Dish Details**
  - Browse dishes with descriptions and images
  - Add dishes to the cart, update quantities

- **Cart & Checkout**
  - Cart stored locally using **Room Database**
  - Synced with **Firebase Firestore** for cloud persistence
  - Checkout stores order summary + items in Firestore
  - Cart is cleared locally and remotely after checkout

- **Orders**
  - View your past orders
  - Expand orders to see items and total

- **Reservations**
  - Reserve a table through a simple checkout flow
  - Confirmation screen once reserved

- **Profile**
  - View account info (name, email)
  - Access past orders
  - Logout with one tap

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model–View–ViewModel)
- **Dependency Injection**: Hilt
- **Database**: Room (offline persistence)
- **Backend**: Firebase (Authentication & Firestore)
- **Navigation**: Jetpack Navigation Compose

## Screenshots

<img width="2688" height="1265" alt="Little Lemon App" src="https://github.com/user-attachments/assets/2e711b5d-b43f-4fbf-9daf-43d9fa3f2d2e" />

## License

This project was created as part of the Meta Android Developer Professional Certificate on Coursera.
