# DevCycle 2026-006: Random Comedy Song

**Status:** Work Complete
**Start Date:** 2026-06-02
**Target Completion:** 2026-06-02
**Focus:** Add a "Random Comedy Song" button to the main screen that generates and plays a four-section comedy song structure for improvised musical theatre.

---

## Goal

DC4 added the I Want song and DC5 added the I Love song. This cycle adds the Comedy Song — a cyclic, joke-accumulation song form with a Chorus that acts as the audience's recurring anchor. Unlike the previous two song types, the Comedy Song has two optional sections (Bridge and Modulated Chorus) that are always generated but used at the performer's discretion. The Modulated Chorus is unique: it reuses the Chorus progression transposed up one whole step, providing a built-in big finish. The design spec is in `doc/planning/ideas/12_ComedySongStructureDesign.md`.

## Desired Outcome

A "Random Comedy Song" button appears on the main screen. Tapping it generates a new Comedy Song (random key, one progression each for Chorus, Verse, and Bridge, plus a derived Modulated Chorus) and navigates to the Comedy Song screen. The screen shows the original key and the modulated key, a Regenerate button, and four section rows. Each row has a toggle button and the rendered chord names. The Modulated Chorus row is visually distinguished and shows the modulated key. Tapping a section loops it at 120 BPM; tapping again stops it. The back button stops audio and returns to main.

---

## Tasks

### Phase 1: Data — Progression Pools and Song Model

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/data/ComedyProgressions.kt`
  - Define three pool lists as `List<List<String>>`
  - Chorus pool (6 progressions): `I-V-vi-IV`, `I-vi-IV-V`, `I-IV-V-I`, `IV-V-I-vi`, `I-IV-I-V`, `vi-IV-I-V`
  - Verse pool (6 progressions): `I-IV-V`, `I-IV`, `I-V`, `I-vi-IV-V`, `I-IV-I-V`, `I-V-I-IV`
  - Bridge pool (6 progressions): `vi-IV-I-V`, `I-IV-vi-V`, `I-iii-IV-V`, `iii-vi-ii-V`, `vi-ii-IV-V`, `I-IV-I-ii-V`
  - No Modulated Chorus pool — it is derived from the Chorus progression at generation time
- [x] Create `app/src/main/java/com/playchords/model/ComedySong.kt`
  - `data class ComedySection(val label: String, val romanNumerals: List<String>, val chords: List<String>, val isModulated: Boolean = false)`
  - `data class ComedySong(val key: String, val modulatedKey: String, val sections: List<ComedySection>)`
  - The `modulatedKey` field is the original key transposed up one whole step
  - The `isModulated` flag on `ComedySection` marks the Modulated Chorus row for distinct UI treatment
- [x] Create `app/src/main/java/com/playchords/data/ComedyGenerator.kt`
  - `fun generate(): ComedySong`:
    1. Pick a random key from the 12 supported major keys
    2. Derive `modulatedKey` via `ChordMapper.keyOneWholeStepHigher(key)` (never null for the supported 12 keys)
    3. Pick one random progression from each of the Chorus, Verse, and Bridge pools
    4. Render Chorus, Verse, and Bridge via `ChordMapper.renderNumerals(key, numerals)`
    5. Render Modulated Chorus using the **same Chorus numerals** via `ChordMapper.renderNumerals(modulatedKey, chorusNumerals)`
    6. Return `ComedySong` with sections `["Chorus", "Verse", "Bridge", "Big Finish"]` in that order, with `isModulated = true` on the Big Finish section


**Technical Notes:**
`ChordMapper.keyOneWholeStepHigher()` is already implemented and tested (DC1). It returns null only for unknown keys, which cannot occur here since we draw from the same 12-key list. All roman numerals across the three pools (`I`, `ii`, `iii`, `IV`, `V`, `vi`) are present in `ChordMapper.keyMap` for all 12 keys. The Modulated Chorus shares `romanNumerals` with the Chorus section — both store the same list, but `chords` differs because they are rendered from different keys.

---

### Phase 2: ViewModel — Section Playback

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/viewmodel/ComedyViewModel.kt`
- [x] Extend `AndroidViewModel`; create `AudioEngine` in the constructor
- [x] On init, call `ComedyGenerator.generate()` and store the result in a `StateFlow<ComedySong>`
- [x] Expose `playingSection: StateFlow<Int?>` — index of the currently playing section, or `null` if stopped
- [x] Implement `playSection(index: Int)`: cancel any in-flight job; if `index` is already playing, stop and return; otherwise set `playingSection` to `index` and launch a looping coroutine on `Dispatchers.IO` at 120 BPM (2000 ms per chord)
- [x] Implement `stopPlayback()`: cancel the job, set `playingSection` to `null`
- [x] Implement `regenerate()`: call `stopPlayback()`, then assign a new `ComedyGenerator.generate()` result to `_song`
- [x] Release `AudioEngine` and cancel jobs in `onCleared()`

**Technical Notes:**
The ViewModel is structurally identical to `IWantViewModel` and `ILoveViewModel`. The Modulated Chorus plays its pre-rendered chords (already in the transposed key) through the same loop mechanism — no special handling needed in the ViewModel. This is the third song-type ViewModel with this identical structure; see Notes and Risks for refactor timing.

---

### Phase 3: UI — Comedy Song Screen

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/ui/ComedyScreen.kt`
- [x] Top bar: back button (calls `stopPlayback()` then `popBackStack()`) + title `"Comedy Song"`
- [x] Content column:
  - Key line: `"Key of ${song.key}"` in `headlineMedium` + `primary` color
  - `Regenerate` `OutlinedButton` (full width, calls `viewModel.regenerate()`)
  - `HorizontalDivider`
  - Four section rows, one per section in `song.sections`:
    - Filled `Button` when playing; `OutlinedButton` when stopped
    - Button label: section name (`"Chorus"`, `"Verse"`, `"Bridge"`, `"Big Finish"`)
    - For `isModulated = false` sections: chord names joined with `" – "` in `primary` when playing, `MutedText` when stopped
    - For `isModulated = true` (Modulated Chorus): chord names joined with `" – "`, plus `"(${song.modulatedKey})"` below in `MutedText` style to show the transposed key at a glance
- [x] Tapping the active section's button calls `stopPlayback()` (toggle off)
- [x] Style consistent with `IWantScreen` and `ILoveScreen` — same dimensions, spacing, and color rules

**Technical Notes:**
The only visual difference from `IWantScreen`/`ILoveScreen` is the modulated key annotation on the Big Finish row. Appending `"  (${song.modulatedKey})"` as a secondary `Text` beside the chords text is sufficient for v1. The key header shows the main key only — the modulated key surfaces only on the Big Finish row where it is relevant.

---

### Phase 4: Navigation and Main Screen

**Status:** Work Complete

- [x] In `MainScreen.kt`: add a sixth button `"Random Comedy Song"` below `"Random I Love"`; add `onRandomComedy: () -> Unit` parameter
- [x] In `Navigation.kt`: add a `composable("comedy")` route that instantiates `ComedyViewModel` via `viewModel()` and passes it to `ComedyScreen`
- [x] In the `main` composable in `Navigation.kt`: pass `onRandomComedy = { navController.navigate("comedy") }` to `MainScreen`
- [x] Verify back navigation from `"comedy"` pops cleanly to `"main"`

**Technical Notes:**
Same navigation pattern as `iwant` and `ilove`. Each navigation to `"comedy"` creates a fresh `ComedyViewModel` and a fresh song.

---

### Phase 5: Tests and Build Verification

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — BUILD SUCCESSFUL
- [x] Run `./gradlew testDebugUnitTest` — all tests pass
- [x] Add unit tests in `ComedyGeneratorTest.kt`:
  - Generated song has exactly 4 sections
  - Section labels are `"Chorus"`, `"Verse"`, `"Bridge"`, `"Big Finish"` in order
  - Only the Big Finish section has `isModulated = true`
  - Each section's `chords` list is non-empty
  - Chord count matches roman numeral count per section
  - `song.key` is one of the 12 supported major keys
  - `song.modulatedKey` is one of the 12 supported major keys and differs from `song.key`
  - The Chorus and Big Finish sections share the same `romanNumerals` list
  - The Chorus and Big Finish sections have different `chords` lists (different keys)
  - Generating 20 songs produces at least 2 distinct keys
- [ ] Manual smoke test: tap "Random Comedy Song", verify screen opens with key and 4 sections; verify Big Finish row shows the modulated key; tap each section, verify audio loops; tap Regenerate, verify a new song appears; tap back, verify return to main

---

### Phase 6: Refinements

**Status:** Work Complete

Two design document fidelity issues were identified after initial implementation and corrected here.

**I Love Song — label correction (DC5 defect):**
DC5 implemented the I Love Song using the wrong design document (`ILoveSongStructureDesign.md`) instead of the authoritative spec (`11_ILoveStructureDesign.md`), producing incorrect section labels. Corrected here.

- [x] In `ILoveSong.kt`: add `isOptional: Boolean = false` field to `ILoveSection`
- [x] In `ILoveGenerator.kt`: rename sections to `"Opening"`, `"Main Love Theme"`, `"Variant Love Theme"`, `"Climax"`; set `isOptional = true` on `"Variant Love Theme"`
- [x] In `ILoveScreen.kt`: display `"(optional)"` as small muted sub-label inside the button for optional sections
- [x] In `ILoveGeneratorTest.kt`: update label assertions; add test that only `"Variant Love Theme"` has `isOptional = true`

**Comedy Song — label correction (DC6 planning defect):**
The DC6 planning document introduced "Big Finish" as the label for the Modulated Chorus, diverging from `12_ComedySongStructureDesign.md` which specifies "Modulated Chorus". Corrected here.

- [x] In `ComedySong.kt`: add `isOptional: Boolean = false` field to `ComedySection`
- [x] In `ComedyGenerator.kt`: rename `"Big Finish"` → `"Modulated Chorus"`; set `isOptional = true` on that section
- [x] In `ComedyScreen.kt`: display `"(optional)"` as small muted sub-label inside the button for optional sections
- [x] In `ComedyGeneratorTest.kt`: update label assertions and isModulated test name; add test that only `"Modulated Chorus"` has `isOptional = true`
- [x] Run `./gradlew assembleDebug testDebugUnitTest` — BUILD SUCCESSFUL

**Technical Notes:**
The I Love progression pools were not corrected — `ILoveProgressions.kt` still uses pools from `ILoveSongStructureDesign.md`. The two design documents differ in progressions as well as labels. Aligning the pools to `11_ILoveStructureDesign.md` is a separate decision for a future cycle.

---

## Open Questions

1. **Shared ViewModel / model / screen refactor** — This is now the third song type, and all three ViewModels, data models, and screens are structurally identical (with minor variations). The user has noted the intent to accumulate at least 3–4 song types before refactoring. Deferring remains the right call.
   Recommendation: Continue to defer. Plan the refactor as a dedicated DC once the fourth song type is confirmed or completed.

2. **Bridge and Big Finish "optional" labelling** — The design spec describes Bridge and Modulated Chorus as optional at performance time. The UI currently treats all four sections identically. Should the optional sections be visually distinguished (e.g., greyed label, `(optional)` suffix)?
   Recommendation: Add `(optional)` as small muted text below the Bridge and Big Finish button labels. This is low effort and improves clarity for new performers. Implement in Phase 3 of this cycle.

---

## Notes and Risks

- All roman numerals in the Comedy pools (`I`, `ii`, `iii`, `IV`, `V`, `vi`) are confirmed present in `ChordMapper.keyMap` for all 12 keys.
- `ChordMapper.keyOneWholeStepHigher()` has unit test coverage from DC1 and handles all 12 keys correctly, including the wrap-around case (`Bb` → `C`).
- The Verse pool includes two-chord progressions (`I-IV`, `I-V`). At 2000 ms per chord these loop every 4 seconds — short but musically valid for joke delivery.
- The Modulated Chorus shares `romanNumerals` with the Chorus but has different rendered `chords`. Tests should verify both the shared numerals and the different rendered output.
- This is the third structurally-identical song-type implementation. The accumulating duplication across `IWantViewModel`, `ILoveViewModel`, and `ComedyViewModel` is intentional for now, per the project's stated design intent.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-02
**Phases Completed:** 1–5 (implementation complete; manual smoke test pending user verification)
**Work Deferred:** Manual smoke test (requires device/emulator)

**Accomplishments:**
- Added `ComedyProgressions.kt` with Chorus (6), Verse (6), and Bridge (6) pools
- Added `ComedySong.kt` model with `ComedySection` (including `isModulated` flag) and `ComedySong` (with `modulatedKey`)
- Added `ComedyGenerator.kt` deriving the Big Finish from Chorus numerals transposed one whole step up
- Added `ComedyViewModel.kt` with looping section playback and regenerate support
- Added `ComedyScreen.kt` styled consistently with `IWantScreen`/`ILoveScreen`; Big Finish row shows modulated key as muted sub-label
- Updated `MainScreen.kt` with "Random Comedy Song" button
- Updated `Navigation.kt` with `"comedy"` route
- Added `ComedyGeneratorTest.kt` with 10 unit tests covering structure, labels, modulation, numeral/chord sharing, and key variety

**Metrics:**
- Files modified: 2 (MainScreen.kt, Navigation.kt)
- Files added: 6 (ComedyProgressions.kt, ComedySong.kt, ComedyGenerator.kt, ComedyViewModel.kt, ComedyScreen.kt, ComedyGeneratorTest.kt)
- Unit tests: all pass (assembleDebug + testDebugUnitTest both BUILD SUCCESSFUL)

**Lessons / Notes:**
The three song-type ViewModels (IWant, ILove, Comedy) are now structurally identical. The refactor to a shared base remains deferred pending a fourth song type, per project design intent.
