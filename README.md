# 🍒 Cherry Music Player (Comprehensive Spotify Architecture)

<div align="center">

<!-- Non-clickable Badges using HTML -->
<img src="https://img.shields.io/badge/Java-11%2B-ED8B00?style=for-the-badge&logo=java&logoColor=white" alt="Java">
<img src="https://img.shields.io/badge/JavaFX-17-blue?style=for-the-badge&logo=openjdk&logoColor=white" alt="JavaFX">
<img src="https://img.shields.io/badge/Architecture-MVC-green?style=for-the-badge" alt="Architecture">
<img src="https://img.shields.io/badge/Design%20Pattern-Singleton%20%7C%20Observer-orange?style=for-the-badge" alt="Design Pattern">
<img src="https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge" alt="License">

<p align="center">
    <strong>A Production-Grade, Multi-Threaded Audio Streaming & Platform Management System</strong><br>
    An enterprise-level simulation of Spotify built with Java and JavaFX, featuring rigorous OOP hierarchy, complex data structures, multi-criteria custom sorting, custom domain exception handling, and a sleek analytical dashboard.
</p>


<img src="<img width="2560" height="1600" alt="Screenshot 2026-06-26 at 12 08 19 AM" src="https://github.com/user-attachments/assets/270d8285-cd42-4b80-b336-50641da36990" />
" alt="Cherry Music Player Dashboard" width="100%" style="border-radius: 10px;">

</div>

---

## Domain Model & Class Hierarchy

The core system architecture is engineered with a strict, deeply encapsulated Object-Oriented inheritance tree ensuring strong polymorphism and adherence to clean code standards.

### User Account Hierarchy
- `UserAccount` *(Abstract)*
  - `Admin` *(Thread-Safe Singleton)* — Oversees platform reports, statistics, and global system metadata.
  - `Artist` *(Base Class for Creators)* — Manages earnings, dynamic biographical profiles, and subscribers.
    - `Singer` — Publishes full-scale studio `Album` tracks.
    - `Podcaster` — Publishes long-form episodic `Podcast` content.
  - `Listener` *(Base Class for Consumers)* — Manages wallet balances and customized content delivery.
    - `FreeListener` — Enforces functional limits (Max 3 playlists, max 10 tracks/playlist).
    - `PremiumListener` — Unrestricted platform consumption with automated subscription token expiration.

### Audio Component Hierarchy
- `Audio` *(Abstract)* — Tracks structural analytical telemetry (likes, global plays, unique IDs).
  - `Music` — Contains complex lyrics and localized metadata.
  - `Podcast` — Integrates custom thematic captions.

---

## Key Architectural Pillars

### 1. Unified Global State (Singleton Database)
All runtime entities (Users, Audios, System Reports) are dynamically tracked and aggregated inside a centralized memory fabric utilizing the **Singleton Design Pattern** (`Database.java`). Direct mutation of entities is completely encapsulated.

### 2. Custom Reactive Exception Lifecycle
To ensure zero fatal application crashes, a customized, hierarchical domain-specific exception system was developed to gracefully catch operational errors and map them directly into JavaFX UI alerts:
- `InvalidFormatException`: Triggers during runtime user registration using high-performance **Regex filters** for email and localized phone numbers.
- `FailedLoginException`: Parent abstract framework managing:
  - `UserNotFoundException`: Instantiated when query parameters fail database lookup.
  - `WrongPasswordException`: Fired upon credential authentication mismatch.
- `FreeAccountLimitException`: Guard clauses preventing polymorphic boundary violations on restricted tiers.
- `InsufficientCreditException`: Ensures financial transactions and subscription renewals abort securely if wallet metrics are dry.

### 3. Advanced Interface Implementations
- **Polymorphic Traversal (`Iterable<Audio>`)**: Custom iteration engine bound to the `Playlist` collections allowing high-performance, predictable `foreach` streaming routines without breaking structural encapsulation.
- **Multi-Criteria Sorting (`Comparable<Audio>`)**: Overrode standard comparison protocols to achieve native multi-tier array sorting based on a precise algorithmic order:
  $$\text{Audio Title (Alphabetical)} \rightarrow \text{Like Count} \rightarrow \text{Type Specification (Music Prioritized)} \rightarrow \text{Play Metric}$$

### 4. Component-Based Asynchronous UI Architecture
Transitioned from a rich Command-Line Interface (CLI Command Router) into a full-scale decoupled graphical experience driven by **JavaFX, FXML, and Advanced CSS Skins**:
- **Persistent Media Environment**: Unified top layout wrapper utilizing an independent asynchronous background scheduler (`javafx.concurrent.Task`) preventing thread blocking (UI freezing) during continuous music playback, dynamic seeking, and track buffering.
- **Dynamic 3x3 Genre Fabric**: Interactive matrix constraining customer metadata to a maximum of 4 persistent selected genres during onboarding profiles.
- **Analytical Dashboards**: Custom statistical tracking visualizations utilizing JavaFX charts to plot real-time revenue distributions for `Artists` and top platform metrics for `Admins`.

---

## Technical Project Topology
```text
src/
├── org.spotify.player/
│   ├── model/          # Pure immutable domain models, custom enums, and Database layer
│   ├── view/           # FXML dynamic structures and localized CSS presentation layouts
│   ├── controller/     # Bridges UI actions, dispatches asynchronous playback events
│   ├── exceptions/     # Custom domain-specific Throwable exception classes
│   ├── interfaces/     # Custom operational contracts (e.g., GeneralOperations)
│   └── Main.java       # Application entry point and JavaFX lifecycle management
