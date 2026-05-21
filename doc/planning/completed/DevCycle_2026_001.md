# DevCycle 2026-001: Initial App Build

**Status:** Work Complete
**Start Date:** 2026-05-20
**Target Completion:** TBD
**Focus:** Create the PlayChords Android app from scratch — project setup through a working chord progression player.

---

## Goal

Build the first working version of PlayChords, an Android app that plays predefined chord progressions. The user can either select a progression manually (choosing progression, key, and tempo) or let the app pick one randomly. The app then plays the selected progression twice. This cycle establishes the entire foundational codebase: project structure, data models, UI screens, and audio playback.

## Desired Outcome

A working Android app that:
- Presents a main screen with "Select Progression" and "Random Progression" options
- Walks the user through progression, key, and tempo selection
- Maps roman numeral progressions to actual chord names for the chosen key
- Plays the chord progression twice at the selected tempo
- Handles random progression selection transparently (auto-picks, then plays)

---

## Tasks

### Phase 1: Project Setup

**Status:** Work Complete

- [x] Create a new Android project in `C:\dev\android\PlayChords` (Kotlin, Jetpack Compose, min SDK 26)
- [x] Add required dependencies: Jetpack Compose, Navigation, ViewModel, Coroutines
- [x] Establish package structure: `model`, `data`, `ui`, `audio`
- [x] Confirm the project builds and runs a blank Compose screen

**Technical Notes:**
Use a single-Activity architecture with Compose Navigation. Target API 35 (Android 15). Keep the dependency footprint minimal — no external audio libraries unless the built-in `SoundPool` or `MediaPlayer` proves insufficient.

---

### Phase 2: Core Data Models

**Status:** Work Complete

- [x] Define `ChordProgression` data class (name, category, romanNumerals, tags/moods)
- [x] Define mood/character enum or sealed class (CADENTIAL, LIFT, LOOP, OPEN, PIVOT, COLOR)
- [x] Implement `ChordProgressions.kt` with the full list of 25 progressions from the initial ideas doc
- [x] Implement `ChordMapper` object with the full 12-key map and `renderProgression()` function
- [x] Implement `keyOneWholeStepHigher()` and `renderProgressionOneWholeStepHigher()` helpers
- [x] Write unit tests for `ChordMapper.renderProgression()` across several keys and progressions

**Technical Notes:**
`ChordMapper` is a pure object with no Android dependencies — keep it that way so it is trivially testable. The 25 progressions and 12-key chord map are defined verbatim from `doc/planning/ideas/00_initial_ideas.md`.

---

### Phase 3: Main Screen

**Status:** Work Complete

- [x] Implement `MainScreen` composable with "Select Progression" and "Random Progression" buttons
- [x] Wire navigation from `MainScreen` to the selection flow (or directly to playback for Random)
- [x] Apply a basic but clean visual theme (dark background suits a music app)

**Technical Notes:**
Keep `MainScreen` stateless — pass callbacks for button actions. Navigation decisions live in the NavHost or a top-level ViewModel.

---

### Phase 4: Selection Flow (Select Progression path)

**Status:** Work Complete

- [x] Implement `SelectProgressionScreen` — scrollable list of progressions grouped by category
- [x] Implement `SelectKeyScreen` — grid or list of the 12 major keys (C through B)
- [x] Implement `SelectTempoScreen` — picker for: 50, 60, 70, 80, 90, 100, 110, 120 bpm
- [x] Wire all three screens into a sequential navigation flow
- [x] Pass selected values forward to the playback screen

**Technical Notes:**
Group progressions by category on `SelectProgressionScreen` (Classic / Standard, Musical Theatre / Jazz, Expressive / Color). Show the progression name and tags so the user has context when choosing. Use a `SelectionViewModel` or Compose state hoisting to carry selections through the flow.

---

### Phase 5: Playback Screen and Audio Engine

**Status:** Work Complete

- [x] Implement `PlaybackScreen` composable — shows current chord, progression name, key, tempo, and playback progress
- [x] Implement `AudioEngine` (or `PlaybackEngine`) that steps through chords at the selected BPM, playing each chord twice through the full progression
- [x] Use synthesized tones (sine wave via `AudioTrack`) to produce an audible chord sound per step
- [x] Handle playback lifecycle (coroutine cancellation on ViewModel clear releases AudioTrack in finally block)
- [x] Show "Play Again" and "Back to Main" actions when the two-pass sequence completes

**Technical Notes:**
Each chord occupies one measure (4 beats) — store this as a named constant. "Play the progression twice" means two full loops of the chord list. Use synthesized tones (sine or sawtooth) per root note rather than sampled audio. For slash-notation chords (e.g., `V/7`, `ii/I`), play the bass note as a separate synthesized tone alongside the chord.

---

### Phase 6: Random Progression

**Status:** Work Complete

- [x] From `MainScreen`, tapping "Random Progression" picks a random progression from the full list
- [x] Randomly selects a key from the 12 major keys
- [x] Randomly selects a tempo from the 8 BPM options
- [x] Navigates directly to `PlaybackScreen` with the chosen values (skipping the selection flow)
- [x] Show the chosen progression name, key, and tempo on the playback screen so the user knows what was picked

**Technical Notes:**
Random selection logic belongs in a ViewModel or use-case, not in the composable. Keep it deterministic under test by injecting a `Random` instance.

---

## Decisions

1. **Audio fidelity: synthesized tones**
   Use simple synthesized tones (sine or sawtooth per root note) to avoid asset management complexity. Upgrade to sampled piano chords in a later cycle if the sound is too thin.

2. **Chord duration: one measure**
   Each chord occupies one measure (4 beats). At 80 bpm that gives 3 seconds per chord — long enough to hear but not tedious. Define this as a named constant so it can be adjusted easily.

3. **Chord voicing / inversion support: play the bass note separately**
   For slash notation (e.g., `V/7`, `ii/I`), the audio engine plays the bass note as a separate tone. The chord name display shows the full slash notation.

---

## Notes and Risks

- The project directory already exists at `C:\dev\android\PlayChords` but contains no Android project files yet — Phase 1 creates the project from scratch.
- Audio on Android requires careful resource management; test on a real device or emulator with audio output enabled.
- `SoundPool` has a limit on simultaneous streams and loaded samples — stay within 8–12 samples for this cycle.
- Some chord names contain Unicode characters (e.g., `♭`, `#`, `maj7`, `ø`) — verify these render correctly in Compose `Text` on target API levels.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-05-20
**Phases Completed:** All (1–6)
**Work Deferred:** None

**Accomplishments:**
- Created the full Android project from scratch (Kotlin, Jetpack Compose, single-Activity)
- Implemented all 25 chord progressions and the 12-key `ChordMapper`
- Built the complete 5-screen navigation flow: Main → Select Progression → Select Key → Select Tempo → Playback
- Implemented synthesized sine-wave audio via `AudioTrack` with envelope shaping and slash-chord bass support
- Random Progression path picks a random progression, key, and tempo and goes directly to playback
- 9 unit tests for `ChordMapper` — all passing
- Debug APK builds successfully: `app/build/outputs/apk/debug/app-debug.apk` (12 MB)

**Metrics:**
- Files created: 30
- Unit tests: 9 (all passing)
- `BUILD SUCCESSFUL` on both `:app:testDebugUnitTest` and `:app:assembleDebug`

**Lessons / Notes:**
- AGP 8.11.1 + Gradle 8.13 requires JDK 21 — JDK 24 causes a `Type T not present` error at configuration time. Fixed by pinning `org.gradle.java.home` in `gradle.properties`.
- `local.properties` must be created manually when not using Android Studio's project wizard.
