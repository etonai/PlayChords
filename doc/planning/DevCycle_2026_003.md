# DevCycle 2026-003: Play Chords Screen

**Status:** Work Complete
**Start Date:** 2026-06-02
**Target Completion:** TBD
**Focus:** Add a "Play Chords" entry point on the main screen that lets the user tap any chord and hear it immediately.

---

## Goal

The app currently supports playing chord progressions but has no way to hear individual chords in isolation. This cycle adds a chord-browser screen reachable from a new third button on the main screen. The user can scroll through every chord the `AudioEngine` supports, tap one, and hear it play immediately. A back button returns to the main screen.

## Desired Outcome

A "Play Chords" button appears on the main screen alongside the existing two buttons. Tapping it opens a dedicated screen with a scrollable grid of chord buttons — one per chord name available in `ChordMapper` — organized by root note. Tapping a chord button plays it once through the `AudioEngine`. A back button at the top of the screen returns the user to main. The existing progression-playback flow is unchanged.

---

## Tasks

### Phase 1: Data — Define Playable Chord List

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/data/PlayableChords.kt`
- [x] Define a data object that exposes a grouped or flat list of chord name strings the screen will display
- [x] The list should include every unique chord name that appears as a value in `ChordMapper.keyMap` (across all 12 keys), deduplicated and sorted by root then quality
- [x] Verify the list covers all quality types the `AudioEngine` supports: major, minor, maj7, dominant 7, dim, sus4, half-diminished (7b5), and slash chords

**Technical Notes:**
`ChordMapper.keyMap` maps each of 12 root keys to a map of roman-numeral → chord-name. The unique chord name values across all 12 entries give us the complete set. Grouping by root (C, Db, D … B) keeps the grid navigable. The data object should be pure Kotlin with no Android dependencies so it can be tested without a device.

---

### Phase 2: ViewModel — Single-Chord Playback

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/viewmodel/ChordPlayerViewModel.kt`
- [x] Extend `AndroidViewModel` (same pattern as `PlaybackViewModel`) to get `Context` for `AudioEngine`
- [x] Expose a `playChord(chordName: String)` method that plays the chord once with a short fixed duration (e.g., 2000 ms) on a coroutine scoped to the ViewModel
- [x] Cancel any in-flight playback when a new chord is tapped, so tapping quickly doesn't stack sounds
- [x] Expose a `release()` call and clean up `AudioEngine` in `onCleared()`

**Technical Notes:**
`PlaybackViewModel` uses a `Job` to cancel sequential progression playback. A similar pattern works here: keep a reference to the current playback `Job` and cancel it before starting a new one. Duration of 2000 ms gives a clean piano decay without cutting off abruptly. No BPM logic is needed — this screen is always single-chord, single-play.

---

### Phase 3: UI — Play Chords Screen

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/ui/PlayChordsScreen.kt`
- [x] Screen layout: a top bar with a back button and a "Play Chords" title, then a scrollable `LazyVerticalGrid` of chord buttons
- [x] Each chord button shows the chord name; tapping it calls `viewModel.playChord(chordName)`
- [x] Group chords by root note with a section header row for each root (C, Db, D … B) — use `LazyVerticalGrid` items with `span` to make headers full-width
- [x] Style consistent with the existing app theme (Material 3, same `primary` / `outline` button colors)
- [x] `onBack` lambda wires to `navController.popBackStack()`

**Technical Notes:**
`LazyVerticalGrid` with `GridCells.Fixed(3)` (3 columns) gives a compact layout for short chord names. Section headers use `item(span = { GridItemSpan(maxLineSpan) })`. Chord names with slash (`G/B`) may be slightly longer — ensure text is sized to fit or use `autoSize` / wrapping with a minimum font size.

---

### Phase 4: Navigation and Main Screen

**Status:** Work Complete

- [x] In `Navigation.kt`: add a `composable("play/chords")` route wired to `PlayChordsScreen`
- [x] In `MainScreen.kt`: add a third `OutlinedButton` labelled "Play Chords" below the existing two buttons; add an `onPlayChords: () -> Unit` parameter
- [x] In `Navigation.kt` `main` composable: pass `onPlayChords = { navController.navigate("play/chords") }` to `MainScreen`
- [x] Confirm the back stack from `play/chords` pops cleanly to `main` (no extra entries)

**Technical Notes:**
`MainScreen` currently takes two lambdas. Adding a third follows the same pattern. No shared state is needed between `PlayChordsScreen` and `SelectionViewModel` — `ChordPlayerViewModel` is self-contained.

---

### Phase 5: Build and Verification

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — BUILD SUCCESSFUL
- [x] Run `./gradlew testDebugUnitTest` — all 15 tests passing (9 existing + 6 new)
- [x] Add unit tests for `PlayableChords`: 6 tests covering non-empty, known chord names, all 12 roots present, no duplicates
- [ ] Manual or emulator smoke test: tap "Play Chords" from main, verify screen opens; tap a chord, verify audio; tap back, verify return to main

---

## Open Questions

1. **Slash chords in the grid** — Slash chords like `G/B` or `Dm/C` are valid but less common as standalone chord auditions. They are in `ChordMapper` and the `AudioEngine` supports them.
   Recommendation: Include them in the list. They play correctly and excluding them would mean the grid is incomplete relative to what the app knows. If the grid feels cluttered, they can be filtered in a future cycle.

2. **Grid columns** — 3 columns works for names up to ~6 characters; some slash chords or names like `C#m7b5` may be longer.
   Recommendation: Use 3 columns with `maxLines = 1` and `overflow = Ellipsis` on button text, or drop to 2 columns if names overflow frequently in testing.

---

## Notes and Risks

- `ChordPlayerViewModel` must release `AudioEngine` in `onCleared()`. If the user navigates back mid-play, the ViewModel will be cleared and audio should stop.
- The `PlayChordsScreen` route should not share a `PlaybackViewModel` instance with the progression playback route — use separate ViewModel instances to avoid audio state conflicts.
- `SoundPool` has a stream limit of 8 (set in `AudioEngine`). Tapping chords in rapid succession plays up to 8 simultaneous streams; cancelling the coroutine stops the `delay` but does not cut off a sound already submitted to `SoundPool`. This is acceptable behavior for a v1 chord browser.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-02
**Phases Completed:** 1–4 complete; Phase 5 pending on-device verification
**Work Deferred:** On-device smoke test (requires physical device or emulator with audio)

**Accomplishments:**
- Added `allChordNames()` to `ChordMapper` to expose all unique chord names as a public API
- Created `PlayableChords` data object grouping all chord names by root (17 possible root slots covering C through B including sharps and flats)
- Created `ChordPlayerViewModel` — cancels in-flight playback on each new tap, 2-second sustain, releases `AudioEngine` on `onCleared()`
- Created `PlayChordsScreen` — `Scaffold` + `TopAppBar` with back arrow, `LazyVerticalGrid` (3 columns) with per-root section headers, `OutlinedButton` per chord
- Added "Play Chords" `OutlinedButton` to `MainScreen`, wired via new `play/chords` route in `Navigation.kt`

**Metrics:**
- Files modified: 3 (`ChordMapper.kt`, `MainScreen.kt`, `Navigation.kt`)
- Files added: 4 (`PlayableChords.kt`, `ChordPlayerViewModel.kt`, `PlayChordsScreen.kt`, `PlayableChordsTest.kt`)
- Unit tests: 15 passing (9 pre-existing + 6 new)

**Lessons / Notes:**
- `ChordMapper.keyMap` is private; adding a thin `allChordNames()` accessor keeps the data source single and avoids duplicating the chord list.
- `PlayableChords.groups` uses `lazy` delegation so the derivation from `ChordMapper` runs once at first access — no startup cost.
- The automirrored back icon (`Icons.AutoMirrored.Filled.ArrowBack`) is already available via the Material3 BOM; no extra dependency needed.
