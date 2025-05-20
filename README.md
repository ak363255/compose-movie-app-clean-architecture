# 🎬 Movie App

A modern Android movie browsing application built with **Clean Architecture**, **Jetpack Compose**, and powerful tools like **Retrofit**, **Room**, **Kotlin Coroutines**, **Paging 3**, and **StateFlow**. The app allows users to discover movies, search with debounce, and manage a list of favorite movies.

---

## ✨ Features

- 🔍 **Movie Search Screen**  
  - Debounced search with Flow
  - Real-time UI updates using `StateFlow`
  - Minimum query length filter

- 🎞️ **Movie Listing with Pagination**  
  - Seamless infinite scrolling using **Paging 3**
  - Handles network and UI loading states
 

- ❤️ **Favorites Screen**
  - Users can add/remove favorite movies
  - Backed by local **Room database**
  - Custom scroll handler with swipe like effect

- 🧼 **Clean Architecture**
  - Modular structure with clear separation of concerns:
    - `data`: network/database implementation
    - `domain`: use cases & models
    - `presentation`: Compose UI & ViewModels
    - `components`: reusable UI and utilities

- ⚙️ **Koin for Dependency Injection**
  - ViewModel injection
  - Module separation for data, domain, and presentation layers

## 📂 Project Structure


---

## 🔧 Tech Stack

| Tool             | Description                                   |
|------------------|-----------------------------------------------|
| Kotlin           | Modern, concise programming language          |
| Jetpack Compose  | Declarative UI toolkit                        |
| Coroutines       | Asynchronous programming                      |
| StateFlow        | Reactive UI updates                           |
| Paging 3         | Efficient pagination and lazy loading         |
| Retrofit         | HTTP client for API calls                     |
| Room             | Local database for favorite movies            |
| Clean Architecture | Separation of concerns, testability         |

---

## 📱 Screens

- Home: Paginated movie list
- Search: Search with debounce and reactive UI
- Favorites: Swipe-to-delete with Room integration
- Detail Screen - Movie detail screen


