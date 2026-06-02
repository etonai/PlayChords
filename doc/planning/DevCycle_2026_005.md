# DevCycle 2026-005: Random I Love Song

**Status:** Planning
**Start Date:** TBD
**Target Completion:** TBD
**Focus:** Add a "Random I Love" button to the main screen that generates and plays a four-section "I Love" song structure for musical theatre improvisation.

---

## Goal

DC4 introduced the "I Want" song generator. This cycle adds a companion generator for "I Love" songs — a warm, celebratory song form in which a character expresses love or deep affection. The structure has four sections (Tender Opening, Story, Declaration, Joyful Finale) with harmonic pools designed to feel intimate and triumphant rather than urgent and desiring. The design spec is in `doc/planning/ideas/ILoveSongStructureDesign.md`.

## Desired Outcome

A "Random I Love" button appears on the main screen. Tapping it generates a new I Love song (random key, one progression per section from each pool) and navigates to the I Love screen. The screen shows the key at the top, a Regenerate button, and four section rows — each with a labeled toggle button and the rendered chord names in the selected key. Tapping a section loops it at 120 BPM; tapping again stops it. The back button stops audio and returns to main.

---

## Tasks

### Phase 1: Data — Progression Pools and Song Model

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/data/ILoveProgressions.kt`
  - Define the four pool lists as `List<List<String>>`
  - Tender Opening pool (6 progressions): `I-vi`, `I-IV-I`, `I-Imaj7-IV`, `vi-I-IV`, `I-iii-IV`, `I-IV-vi-I`
  - Story pool (6 progressions): `I-IV-V-I`, `I-IV-ii-V`, `IV-I-ii-V`, `I-Imaj7-IV-V`, `I-ii-V-I`, `I-iii-IV-V`
  - Declaration pool (6 progressions): `I-V-IV-I`, `IV-V-I-IV`, `I-IV-V-I`, `IV-I-V-I`, `I-V-I-IV`, `I-IV-V-vi`
  - Joyful Finale pool (6 progressions): `IV-V-I`, `I-IV-V-I`, `I-V-IV-I`, `IV-I-IV-V-I`, `I-IV-I-V-I`, `ii-V-I-IV-I`
- [ ] Create `app/src/main/java/com/playchords/model/ILoveSong.kt`
  - `data class ILoveSection(val label: String, val romanNumerals: List<String>, val chords: List<String>)`
  - `data class ILoveSong(val key: String, val sections: List<ILoveSection>)`
- [ ] Create `app/src/main/java/com/playchords/data/ILoveGenerator.kt`
  - `fun generate(): ILoveSong` — picks a random key from the 12 supported major keys, picks one random progression per section from each pool, renders each to chord names via `ChordMapper.renderNumerals()`, returns the assembled `ILoveSong`
  - Section labels: `"Opening"`, `"Story"`, `"Declaration"`, `"Finale"`

**Technical Notes:**
All roman numerals used across the four pools (`I`, `ii`, `iii`, `IV`, `V`, `vi`, `Imaj7`) are present in `ChordMapper.keyMap` for all 12 keys — no missing mappings. `ChordMapper.renderNumerals()` was added in DC4 and is reused here unchanged. `ILoveGenerator` is pure Kotlin with no Android dependencies, so it is fully unit-testable.

---

### Phase 2: ViewModel — Section Playback

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/viewmodel/ILoveViewModel.kt`
- [ ] Extend `AndroidViewModel`; create `AudioEngine` in the constructor
- [ ] On init, call `ILoveGenerator.generate()` and store the result in a `StateFlow<ILoveSong>`
- [ ] Expose `playingSection: StateFlow<Int?>` — index of the currently playing section, or `null` if stopped
- [ ] Implement `playSection(index: Int)`: cancel any in-flight job; if `index` is already playing, stop and return; otherwise set `playingSection` to `index` and launch a looping coroutine on `Dispatchers.IO` at 120 BPM (2000 ms per chord)
- [ ] Implement `stopPlayback()`: cancel the job, set `playingSection` to `null`
- [ ] Implement `regenerate()`: call `stopPlayback()`, then assign a new `ILoveGenerator.generate()` result to `_song`
- [ ] Release `AudioEngine` and cancel jobs in `onCleared()`

**Technical Notes:**
This ViewModel is structurally identical to `IWantViewModel` — same loop pattern (`while (true) { for (chord) { ensureActive(); audioEngine.playChord(chord, 2000L) } }`), same toggle-off behavior, same regenerate pattern. The only difference is the song type it holds. See Notes and Risks for a discussion of potential future consolidation.

---

### Phase 3: UI — I Love Screen

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/ui/ILoveScreen.kt`
- [ ] Top bar: back button (calls `stopPlayback()` then `popBackStack()`) + title `"I Love Song"`
- [ ] Content column:
  - Key displayed prominently: `"Key of [key]"` in `headlineMedium` + `primary` color
  - `Regenerate` `OutlinedButton` (full width, calls `viewModel.regenerate()`)
  - `HorizontalDivider`
  - Four section rows, one per section:
    - Filled `Button` when that section is playing; `OutlinedButton` when stopped
    - Button label: section name (`"Opening"`, `"Story"`, `"Declaration"`, `"Finale"`)
    - Chord names beside the button joined with `" – "`, in `primary` color when playing and `MutedText` when stopped
- [ ] Tapping the active section's button calls `stopPlayback()` (toggle off)
- [ ] Style consistent with `IWantScreen` — same dimensions, spacing, and color rules

**Technical Notes:**
Collect `viewModel.song` and `viewModel.playingSection` with `collectAsState()`. The layout and interaction logic are identical to `IWantScreen`; only the title, ViewModel type, and song type differ. If a shared composable is extracted in a future refactor, this screen becomes a thin wrapper.

---

### Phase 4: Navigation and Main Screen

**Status:** Planning

- [ ] In `MainScreen.kt`: add a fifth button `"Random I Love"` below `"Random I Want"`; add `onRandomILove: () -> Unit` parameter
- [ ] In `Navigation.kt`: add a `composable("ilove")` route that instantiates `ILoveViewModel` via `viewModel()` and passes it to `ILoveScreen`
- [ ] In the `main` composable in `Navigation.kt`: pass `onRandomILove = { navController.navigate("ilove") }` to `MainScreen`
- [ ] Verify back navigation from `ilove` pops cleanly to `main`
- [ ] Verify that navigating from `iwant` to `ilove` (via main) does not share ViewModel instances

**Technical Notes:**
Each navigation to `"ilove"` creates a fresh `ILoveViewModel` and a fresh song, for the same reason as `"iwant"`. The two routes are independent — no shared state.

---

### Phase 5: Tests and Build Verification

**Status:** Planning

- [ ] Run `./gradlew assembleDebug` — must succeed
- [ ] Run `./gradlew testDebugUnitTest` — all existing tests must still pass
- [ ] Add unit tests in `ILoveGeneratorTest.kt`:
  - Generated song has exactly 4 sections
  - Section labels are `"Opening"`, `"Story"`, `"Declaration"`, `"Finale"` in order
  - Each section's `chords` list is non-empty
  - Chord count matches roman numeral count per section
  - `song.key` is one of the 12 supported major keys
  - Generating 20 songs produces at least 2 distinct keys
  - Generating 20 songs varies the Opening progressions
- [ ] Manual smoke test: tap "Random I Love", verify screen opens with key and 4 sections; tap each section, verify audio loops; tap Regenerate, verify a new song appears and audio stops; tap back, verify return to main

---

## Open Questions

1. **Shared ViewModel base** — `ILoveViewModel` and `IWantViewModel` are structurally identical. A shared abstract base class or a generic `SongViewModel<T>` could eliminate the duplication.
   Recommendation: Defer to a future refactor cycle. Both ViewModels are small and the duplication is harmless for now. Generalise when a third song type is added.

2. **Shared data model** — `ILoveSection`/`ILoveSong` and `IWantSection`/`IWantSong` have identical fields. A single `SongSection`/`GeneratedSong` pair could serve both.
   Recommendation: Defer for the same reason. Introduce a shared model when adding the third song type.

3. **Shared screen composable** — `ILoveScreen` and `IWantScreen` differ only in title and type. A parameterised `SongScreen(title, song, ...)` composable could serve both.
   Recommendation: Defer to the same future refactor cycle.

ED: I agree with all recommendations. Part of the purpose of this test app is to create at least 3 (but probably 4) song types, and then use that knowledge to refactor.
---

## Notes and Risks

- All roman numerals in the I Love pools (`I`, `ii`, `iii`, `IV`, `V`, `vi`, `Imaj7`) are confirmed present in `ChordMapper.keyMap` for all 12 keys.
- `Imaj7` appears in the Tender Opening pool (`I-Imaj7-IV`) and the Story pool (`I-Imaj7-IV-V`). Verify that `AudioEngine.parseChord()` handles `Imaj7` correctly — it does: `quality.endsWith("maj7")` matches and returns intervals `[0, 4, 7, 11]`.
- The longest Finale progression (`ii-V-I-IV-I`, 5 chords) loops at 2000 ms/chord = 10 seconds per loop. This is well within the `SoundPool` stream limit.
- `ILoveViewModel` generates a fresh song on construction, same as `IWantViewModel`. Pressing back and tapping "Random I Love" again always produces a new song.

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
