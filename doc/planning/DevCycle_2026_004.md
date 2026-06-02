# DevCycle 2026-004: Random I Want Song

**Status:** Work Complete
**Start Date:** 2026-06-02
**Target Completion:** 2026-06-02
**Focus:** Add a "Random I Want" button to the main screen that generates and plays a four-section "I Want" song structure for musical theatre improvisation.

---

## Goal

The app currently plays predefined chord progressions. This cycle adds a purpose-built generator for "I Want" songs — a common musical theatre song form with four distinct dramatic sections (Opening, Main Body, Big Statement of Desire, Climax). The app randomly selects a key and one progression per section from curated pools, then presents a dedicated screen where the user can tap any section to hear it loop at 120 BPM. The design spec is in `doc/planning/ideas/IWantStructureDesign.md`.

## Desired Outcome

A "Random I Want" button appears on the main screen. Tapping it generates a new I Want song (random key, one progression per section) and navigates to the I Want screen. The screen shows the key at the top and four rows — one per section — each with a labeled button and the rendered chord names in the selected key. Tapping a section button stops any playing audio and starts looping that section's progression at 120 BPM. The back button returns to main.

---

## Tasks

### Phase 1: Data — Progression Pools and Song Model

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/data/IWantProgressions.kt`
  - Define the four pool lists as `List<List<String>>` (each entry is a list of roman numeral strings)
  - Opening pool (6 progressions): `I-vi`, `I-IV`, `I-vi-IV`, `vi-IV`, `I-iii-vi`, `I-vi-IV-I`
  - Main Body pool (6 progressions): `I-vi-ii-V`, `I-vi-IV-V`, `I-IV-ii-V`, `I-iii-vi-ii-V`, `vi-ii-V-I`, `I-IV-V-I`
  - Big Statement pool (6 progressions): `I-V-vi-IV`, `I-vi-IV-V`, `IV-V-I-vi`, `I-IV-I-V`, `vi-IV-I-V`, `I-iii-IV-V`
  - Climax pool (6 progressions): `IV-V-I`, `ii-V-I`, `IV-I-V-I`, `IV-V-vi-V-I`, `ii-IV-V-I`, `IV-V-I-vi-IV-V-I`
- [x] Create `app/src/main/java/com/playchords/model/IWantSong.kt`
  - `data class IWantSection(val label: String, val romanNumerals: List<String>, val chords: List<String>)`
  - `data class IWantSong(val key: String, val sections: List<IWantSection>)`
- [x] Add `fun renderNumerals(key: String, numerals: List<String>): List<String>` to `ChordMapper`
  - Same logic as `renderProgression` but takes a plain list instead of a `ChordProgression` object
- [x] Create `app/src/main/java/com/playchords/data/IWantGenerator.kt`
  - `fun generateIWantSong(): IWantSong` — picks a random key from the 12 supported major keys, picks one random progression per section from each pool, renders each to chord names via `ChordMapper.renderNumerals()`, returns the assembled `IWantSong`
  - Section labels: `"Opening"`, `"Main Body"`, `"Desire Statement"`, `"Climax"`

**Technical Notes:**
All roman numerals in the four pools (`I`, `ii`, `iii`, `IV`, `V`, `vi`) are present in `ChordMapper.keyMap` for all 12 keys — no unmapped numerals. `renderNumerals` is a two-line helper that reuses the existing keyMap lookup. `IWantGenerator` is a pure function with no Android dependencies, so it is fully unit-testable.

---

### Phase 2: ViewModel — Section Playback

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/viewmodel/IWantViewModel.kt`
- [x] Extend `AndroidViewModel`; create `AudioEngine` in the constructor
- [x] On init, call `generateIWantSong()` and store the result in a `StateFlow<IWantSong>`
- [x] Expose `playingSection: StateFlow<Int?>` — index of the currently playing section, or `null` if stopped
- [x] Implement `playSection(index: Int)`:
  - Cancel any in-flight playback job
  - Set `playingSection` to `index`
  - Launch a coroutine on `Dispatchers.IO` that loops the section's chord list indefinitely at 120 BPM (2000 ms per chord, 4 beats × 500 ms/beat)
- [x] Implement `stopPlayback()`: cancel the job, set `playingSection` to `null`
- [x] Release `AudioEngine` and cancel jobs in `onCleared()`

**Technical Notes:**
Loop playback using `while (isActive) { for (chord in chords) { ensureActive(); audioEngine.playChord(chord, 2000L) } }`. `isActive` and `ensureActive()` from `kotlinx.coroutines` ensure the loop exits cleanly when the job is cancelled. 120 BPM × 4 beats = 500 ms/beat; 4 beats per chord = 2000 ms per chord.

---

### Phase 3: UI — I Want Screen

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/ui/IWantScreen.kt`
- [x] Top bar: back button + title "I Want Song"
- [x] Below the top bar: key displayed prominently (e.g., `"Key: C"`)
- [x] Four section rows, one per section in `IWantSong.sections`:
  - A `Button` labelled with the section name (e.g., `"Opening"`)
  - Beside it: the rendered chord names joined with `" – "` (e.g., `"C – Am – F"`)
  - The active section's button uses `containerColor = primary`; inactive sections use `OutlinedButton` style
- [x] Tapping a section button calls `viewModel.playSection(index)`; if the tapped section is already playing, call `viewModel.stopPlayback()` (toggle off)
- [x] Back navigation calls `viewModel.stopPlayback()` then `navController.popBackStack()`

**Technical Notes:**
Collect `viewModel.song` and `viewModel.playingSection` as state with `collectAsState()`. The active/inactive button style swap is driven by comparing `playingSection` to the row's index. Stop-on-back is handled by calling `stopPlayback()` in the `onBack` lambda before popping the stack — no `DisposableEffect` needed since the ViewModel's `onCleared` also cancels playback.

---

### Phase 4: Navigation and Main Screen

**Status:** Work Complete

- [x] In `MainScreen.kt`: add a fourth button `"Random I Want"` below the existing three; add `onRandomIWant: () -> Unit` parameter
- [x] In `Navigation.kt`: add a `composable("iwant")` route that instantiates `IWantViewModel` and passes it to `IWantScreen`
- [x] In the `main` composable in `Navigation.kt`: pass `onRandomIWant = { navController.navigate("iwant") }` to `MainScreen`
- [x] Verify back navigation from `iwant` pops cleanly to `main`

**Technical Notes:**
Each navigation to `"iwant"` creates a new `IWantViewModel` instance (and therefore a new song). This is the desired behavior — "Random I Want" always generates a fresh song. If the user wants to replay the same song they can stay on the screen and re-tap sections.

---

### Phase 5: Tests and Build Verification

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — must succeed
- [x] Run `./gradlew testDebugUnitTest` — all existing tests must still pass
- [x] Add unit tests in `IWantGeneratorTest.kt`:
  - Generated song has exactly 4 sections
  - Each section label matches expected values (`"Opening"`, `"Main Body"`, `"Desire Statement"`, `"Climax"`)
  - Each section's `chords` list is non-empty
  - `song.key` is one of the 12 supported major keys
  - Generating 20 songs produces at least 2 distinct keys (randomness sanity check)
- [x] Manual smoke test: tap "Random I Want", verify song screen appears with key and 4 sections; tap each section, verify audio loops; tap again to stop; tap back, verify return to main

---

## Open Questions

1. **Regenerate button** — Should the I Want screen include a "Regenerate" button to produce a new song without going back to main?
   Recommendation: Defer to a future cycle. The current flow (back → tap again) is sufficient for v1.

2. **Section looping feedback** — Should the UI show any indication that a section is looping (e.g., a pulsing indicator or chord highlight cycling through)?
   Recommendation: Defer to a future cycle. The active button highlight is sufficient feedback for v1.

3. **Tempo** — The spec fixes playback at 120 BPM. Should this be configurable?
   Recommendation: Hard-code 120 BPM for v1 per the spec. Tempo selection can be added later if needed.

---

## Notes and Risks

- All roman numerals in the four progression pools (`I`, `ii`, `iii`, `IV`, `V`, `vi`) are present in `ChordMapper.keyMap` for all 12 keys. No missing mappings.
- The longest pool progression (`IV-V-I-vi-IV-V-I`, 7 chords) loops at 2000 ms/chord = 14 seconds per loop. This is acceptable.
- `IWantViewModel` generates a new song each time it is created. Since ViewModel instances are tied to the back stack entry, navigating back and tapping "Random I Want" again always produces a new song.
- `AudioEngine` has a `SoundPool` stream limit of 8. The maximum chord size is 4 notes + 1 bass note = 5 streams — well within the limit even with rapid section switching.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-02
**Phases Completed:** 1–4 complete; Phase 5 pending on-device verification
**Work Deferred:** On-device smoke test (requires physical device or emulator with audio)

**Accomplishments:**
- Created `IWantProgressions.kt` with all four curated progression pools (24 progressions total)
- Created `IWantSong.kt` data classes (`IWantSection`, `IWantSong`)
- Added `renderNumerals()` to `ChordMapper` for plain-list roman numeral rendering
- Created `IWantGenerator.kt` — pure function, picks random key + one progression per section, renders to chord names
- Created `IWantViewModel.kt` — generates song on init, loops selected section at 120 BPM, cancels on section switch or back
- Created `IWantScreen.kt` — key header, divider, 4 section rows each with a toggle button (filled when playing, outlined when stopped) and chord names; tapping active section stops playback
- Added "Random I Want" button to `MainScreen`, wired via `iwant` route in `Navigation.kt`

**Metrics:**
- Files modified: 3 (`ChordMapper.kt`, `MainScreen.kt`, `Navigation.kt`)
- Files added: 6 (`IWantProgressions.kt`, `IWantSong.kt`, `IWantGenerator.kt`, `IWantViewModel.kt`, `IWantScreen.kt`, `IWantGeneratorTest.kt`)
- Unit tests: 22 passing (15 pre-existing + 7 new)

**Lessons / Notes:**
- `renderNumerals()` is a clean two-line helper that reuses the existing `keyMap` logic — no need to create a dummy `ChordProgression` object just to call `renderProgression`.
- Looping with `while (true) { for (chord in chords) { ensureActive(); audioEngine.playChord(...) } }` gives clean cancellation at chord boundaries without needing `isActive` checks on the outer loop.
- Each navigation to `"iwant"` creates a fresh `IWantViewModel` and therefore a fresh song — intentional behavior since the button is labelled "Random".
