# CleanCam

**CleanCam** is a mobile-first Fabric client mod for Minecraft: Java Edition **1.20.1**, created by **RC Empire**.

> **Status: early alpha, not production-ready.** The in-game menu, tutorial, keybinds, configuration persistence, and CI setup are the foundation. Real video capture, audio, editing, clean framebuffer capture, and MP4 export are not implemented yet.

## Current features
- In-game menu with Tutorial, About/keybinds, and Storage sections.
- Configurable shortcuts under Options > Controls > Key Binds > CleanCam.
- F7 opens tutorial by default; F8 toggles a prototype flag only.
- First-world-entry tutorial prompt and saved settings.

## Android launcher compatibility
Designed for testing with Fabric Loader + Minecraft Java 1.20.1 on Android Java launchers, including PojavLauncher and Zalith Launcher 2. The goal is broad compatibility, not a false guarantee that every launcher/device/renderer has passed. Test each launcher, Android version, renderer, and Java runtime before release.

## Install a development build
1. Install Fabric Loader for Minecraft 1.20.1 in your launcher.
2. Install Fabric API for Minecraft 1.20.1.
3. Put the CleanCam JAR and Fabric API JAR in that instance's mods folder.
4. Launch the Fabric 1.20.1 profile.
5. Find CleanCam in Options > Controls > Key Binds.

No stable release has been published yet. Do not treat a failed CI artifact as a release.

## Build from source
Requires Git and JDK 17. Clone this repository and run `gradle build` from its root. The remapped JAR should be under build/libs.

## Planned storage
The menu shows intended folders under the game directory: cleancam/recordings/raw and cleancam/recordings/exports. They are informational in this alpha; no video files are written. Android public-media export needs a compatible storage integration.

## Technical limits
- A normal Fabric mod cannot guarantee launcher controls disappear if the launcher draws them outside Minecraft's framebuffer.
- Android hardware encoding may require a supported bridge to Android MediaCodec.
- A true replay editor needs game-state capture; it is not just camera movement.
- Capture/encoding must avoid blocking Minecraft's render thread.

## Roadmap
- [x] Fabric 1.20.1 project foundation
- [x] In-game tutorial and keybind reference
- [x] Persistent configuration prototype
- [ ] Implement real video capture and audio sync
- [ ] Test capture on PojavLauncher, Zalith Launcher 2, and other Android Java launchers
- [ ] Add trim/split/preview/export
- [ ] Add Android storage and encoder integration where supported
- [ ] Run automated and physical-device tests
- [ ] Publish stable release only after tests pass

## Bug reports
Include Minecraft/Fabric versions, launcher/version, device and Android version, renderer, latest.log, and reproduction steps. Remove tokens and private information from logs.

## License
MIT. See LICENSE. Third-party dependencies retain their own licenses.
