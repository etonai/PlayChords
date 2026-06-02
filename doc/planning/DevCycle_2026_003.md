# DevCycle 2026-003: Play Chords Screen

**Status:** Planning
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

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/data/PlayableChords.kt`
- [ ] Define a data object that exposes a grouped or flat list of chord name strings the screen will display
- [ ] The list should include every unique chord name that appears as a value in `ChordMapper.keyMap` (across all 12 keys), deduplicated and sorted by root then quality
- [ ] Verify the list covers all quality types the `AudioEngine` supports: major, minor, maj7, dominant 7, dim, sus4, half-diminished (7b5), and slash chords

**Technical Notes:**
`ChordMapper.keyMap` maps each of 12 root keys to a map of roman-numeral → chord-name. The unique chord name values across all 12 entries give us the complete set. Grouping by root (C, Db, D … B) keeps the grid navigable. The data object should be pure Kotlin with no Android dependencies so it can be tested without a device.

---

### Phase 2: ViewModel — Single-Chord Playback

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/viewmodel/ChordPlayerViewModel.kt`
- [ ] Extend `AndroidViewModel` (same pattern as `PlaybackViewModel`) to get `Context` for `AudioEngine`
- [ ] Expose a `playChord(chordName: String)` method that plays the chord once with a short fixed duration (e.g., 2000 ms) on a coroutine scoped to the ViewModel
- [ ] Cancel any in-flight playback when a new chord is tapped, so tapping quickly doesn't stack sounds
- [ ] Expose a `release()` call and clean up `AudioEngine` in `onCleared()`

**Technical Notes:**
`PlaybackViewModel` uses a `Job` to cancel sequential progression playback. A similar pattern works here: keep a reference to the current playback `Job` and cancel it before starting a new one. Duration of 2000 ms gives a clean piano decay without cutting off abruptly. No BPM logic is needed — this screen is always single-chord, single-play.

---

### Phase 3: UI — Play Chords Screen

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/ui/PlayChordsScreen.kt`
- [ ] Screen layout: a top bar with a back button and a "Play Chords" title, then a scrollable `LazyVerticalGrid` of chord buttons
- [ ] Each chord button shows the chord name; tapping it calls `viewModel.playChord(chordName)`
- [ ] Group chords by root note with a section header row for each root (C, Db, D … B) — use `LazyVerticalGrid` items with `span` to make headers full-width
- [ ] Style consistent with the existing app theme (Material 3, same `primary` / `outline` button colors)
- [ ] `onBack` lambda wires to `navController.popBackStack()`

**Technical Notes:**
`LazyVerticalGrid` with `GridCells.Fixed(3)` (3 columns) gives a compact layout for short chord names. Section headers use `item(span = { GridItemSpan(maxLineSpan) })`. Chord names with slash (`G/B`) may be slightly longer — ensure text is sized to fit or use `autoSize` / wrapping with a minimum font size.

---

### Phase 4: Navigation and Main Screen

**Status:** Planning

- [ ] In `Navigation.kt`: add a `composable("play/chords")` route wired to `PlayChordsScreen`
- [ ] In `MainScreen.kt`: add a third `OutlinedButton` labelled "Play Chords" below the existing two buttons; add an `onPlayChords: () -> Unit` parameter
- [ ] In `Navigation.kt` `main` composable: pass `onPlayChords = { navController.navigate("play/chords") }` to `MainScreen`
- [ ] Confirm the back stack from `play/chords` pops cleanly to `main` (no extra entries)

**Technical Notes:**
`MainScreen` currently takes two lambdas. Adding a third follows the same pattern. No shared state is needed between `PlayChordsScreen` and `SelectionViewModel` — `ChordPlayerViewModel` is self-contained.

---

### Phase 5: Build and Verification

**Status:** Planning

- [ ] Run `./gradlew assembleDebug` — must succeed with no new warnings
- [ ] Run `./gradlew testDebugUnitTest` — all existing tests must still pass
- [ ] Add at least one unit test for `PlayableChords`: verify the list is non-empty and contains known chord names (e.g., "C", "Am", "Gmaj7")
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

**Completion Date:** [YYYY-MM-DD]
**Phases Completed:** [List or "All"]
**Work Deferred:** [What was not done and why, or "None"]

**Accomplishments:**
- [What was built or changed]

**Metrics:**
- Files modified: [N]
- Files added: [N]
- Unit tests: [N passing]

**Lessons / Notes:**
[Anything worth remembering for future cycles.]
