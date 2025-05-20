# 🎬 Movie App

A modern Android movie browsing application built with **Clean Architecture**, **Jetpack Compose**, and powerful tools like **Retrofit**, **Room**, **Kotlin Coroutines**, **Paging 3**, and **StateFlow**. The app allows users to discover movies, search with debounce, and manage a list of favorite movies.

---## Screenshots

<img src="https://github.com/user-attachments/assets/6305445c-5811-4af4-8516-8ce7b24372fd" alt="project-screenshot" width="250" height="500/">   
<img src="https://github.com/user-attachments/assets/e2243825-d243-4ae4-a53c-f76e048d653c" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/590974ee-eb49-4ca6-9d5c-e233b12220f7" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/2ba1819d-6842-4a02-bbb7-36adf5eb87e6" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/54200bd3-b2c2-4a7f-baa3-a3f442ac44a8" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/493dcb2c-7f12-4123-a371-caea2d16fbb5" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/03314da7-a0b8-4575-b1e3-f49650545b7e" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/ad9bef0c-7475-4a14-b8d7-67b3bc09c719" alt="project-screenshot" width="250" height="500/">
<img src="https://github.com/user-attachments/assets/d07c9f4f-6730-4caf-a31c-c9ef2659c146" alt="project-screenshot" width="250" height="500/">


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


