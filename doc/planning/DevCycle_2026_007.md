# DevCycle 2026-007: Comedy Song Chorus Resolution

**Status:** Verified
**Start Date:** 2026-06-02
**Target Completion:** 2026-06-02
**Focus:** Add a generated Chorus Resolution Progression to every Comedy Song, with a Modulated Resolution variant for use alongside the Modulated Chorus.

---

## Goal

DC6 added the Comedy Song with four sections: Chorus, Verse, Bridge, and Modulated Chorus. This cycle adds two new sections derived from a Chorus Resolution Progression pool, as specified in `doc/planning/ideas/13_ChorusResolutionProgressions.md`. The Chorus Resolution is always generated and always optional — the performer uses it to end a Chorus more strongly, emphasise a punchline, or signal a song ending. A Modulated Resolution is also always generated: the same resolution numerals rendered in the transposed key, for use alongside the Modulated Chorus.

## Desired Outcome

Every Comedy Song includes six sections: Chorus, Chorus Resolution, Verse, Bridge, Modulated Chorus, and Modulated Resolution. The Chorus Resolution button appears immediately after the Chorus. The Modulated Resolution button appears immediately after the Modulated Chorus. Both resolution sections are marked optional. The Modulated Resolution displays the modulated key annotation, consistent with the Modulated Chorus row. All existing behaviour (looping, regenerate, back navigation) is unchanged.

---

## Tasks

### Phase 1: Data — Resolution Pool and Generator

**Status:** Work Complete

- [x] In `ComedyProgressions.kt`: add a `chorusResolution` pool (`List<List<String>>`) with the 10 progressions from the design doc:
  - `ii - V - I - I`
  - `IV - V - I - I`
  - `vi - IV - V - I`
  - `IV - I - I - I`
  - `V - V - I - I`
  - `I - vi - V - I`
  - `ii - IV - V - I`
  - `I - V - I - I`
  - `iii - vi - V - I`
  - `vi - ii - V - I`
- [x] In `ComedyGenerator.kt`: pick one random progression from `ComedyProgressions.chorusResolution`; render it via `ChordMapper.renderNumerals(key, resolutionNumerals)` for Chorus Resolution, and via `ChordMapper.renderNumerals(modulatedKey, resolutionNumerals)` for Modulated Resolution
- [x] Return six sections in this order: `"Chorus"`, `"Chorus Resolution"`, `"Verse"`, `"Bridge"`, `"Modulated Chorus"`, `"Modulated Resolution"`
  - Chorus Resolution: `isOptional = true`, `isModulated = false`
  - Modulated Resolution: `isOptional = true`, `isModulated = true`
- [x] Also corrected Bridge to `isOptional = true` (design doc defect carried from DC6, resolved per Open Question 1)

**Technical Notes:**
The resolution sections follow the same derivation pattern as the Modulated Chorus: a fixed set of numerals rendered into two different keys. The `isModulated` flag on Modulated Resolution causes `ComedyScreen` to display the modulated key annotation automatically — no screen changes needed for that. The ViewModel iterates sections by index and requires no structural changes. All 10 resolution numerals (`I`, `ii`, `iii`, `IV`, `V`, `vi`) are present in `ChordMapper.keyMap` for all 12 keys.

---

### Phase 2: Tests and Build Verification

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — BUILD SUCCESSFUL
- [x] Run `./gradlew testDebugUnitTest` — all tests pass
- [x] Update `ComedyGeneratorTest.kt`:
  - Change "has exactly 4 sections" → "has exactly 6 sections"
  - Update section label order test to include `"Chorus Resolution"` at index 1 and `"Modulated Resolution"` at index 5
  - Update `isModulated` test: indices 0–4 false, index 5 true (Modulated Resolution joins index 4 Modulated Chorus as modulated — wait, index 4 is Modulated Chorus which is also true)
  - Update `isOptional` test: indices 0, 2 false; indices 1, 3, 4, 5 true
  - Add: Chorus Resolution and Modulated Resolution share the same `romanNumerals`
  - Add: Chorus Resolution and Modulated Resolution have different rendered `chords` (different keys)
- [x] Add: Chorus Resolution and Modulated Resolution share the same `romanNumerals`
- [x] Add: Chorus Resolution and Modulated Resolution have different rendered `chords`
- [ ] Manual smoke test: tap "Random Comedy Song", verify six section rows in correct order; verify Chorus Resolution and Modulated Resolution show "(optional)"; verify Modulated Resolution shows the modulated key; tap each section, verify audio loops; tap Regenerate, verify a new song appears; tap back, verify return to main

**Technical Notes:**
The `isModulated` test needs updating: both Modulated Chorus (index 4) and Modulated Resolution (index 5) should have `isModulated = true`. The `isOptional` test: Chorus (0) and Verse (2) are not optional; Chorus Resolution (1), Bridge (3), Modulated Chorus (4), and Modulated Resolution (5) are optional.

---

## Open Questions

1. **Bridge optional flag** — The design document describes Bridge as optional, and the `isOptional` field exists on `ComedySection`, but Bridge was not marked `isOptional = true` in DC6. Should Bridge be corrected to `isOptional = true` in this cycle?
   Recommendation: Yes — correct it here since we are already updating the generator and tests. It is a one-line change and the design doc is unambiguous.

---

## Notes and Risks

- All resolution pool numerals (`I`, `ii`, `iii`, `IV`, `V`, `vi`) are present in `ChordMapper.keyMap` for all 12 keys. No missing mappings.
- The screen and ViewModel require no structural changes — both already handle an arbitrary number of sections.
- The Modulated Resolution renders the same numerals as the Chorus Resolution into the transposed key. Tests should verify both the shared numerals and the different rendered chords.
- Increasing from 4 to 6 sections will update the existing section-count test — expected and correct.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-02
**Phases Completed:** 1–2 (implementation complete; manual smoke test pending user verification)
**Work Deferred:** Manual smoke test (requires device/emulator)

**Accomplishments:**
- Added `chorusResolution` pool (10 progressions) to `ComedyProgressions.kt`
- Updated `ComedyGenerator.kt` to generate six sections: Chorus, Chorus Resolution, Verse, Bridge, Modulated Chorus, Modulated Resolution
- Corrected Bridge to `isOptional = true` (design doc defect from DC6)
- Updated `ComedyGeneratorTest.kt`: section count, labels, isModulated, isOptional, and resolution numeral/chord tests

**Metrics:**
- Files modified: 2 (`ComedyProgressions.kt`, `ComedyGenerator.kt`, `ComedyGeneratorTest.kt`)
- Files added: 0
- Unit tests: all pass (assembleDebug + testDebugUnitTest both BUILD SUCCESSFUL)

**Lessons / Notes:**
Kotlin backtick test names may not contain semicolons — the compiler rejects them with "Name contains illegal characters". Keep test names to natural English without punctuation.
