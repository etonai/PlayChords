# DevCycle 2026-008: Section Resolution Progressions

**Status:** Work Complete
**Start Date:** 2026-06-02
**Target Completion:** 2026-06-02
**Focus:** Generalise the Chorus Resolution system into a reusable Section Resolution system, adding a Verse Resolution to Comedy Song and renaming the pool and the Modulated Resolution section to match the updated design.

---

## Goal

DC7 introduced a Chorus Resolution Progression for Comedy Song Type 1. The design document `doc/planning/ideas/14_SectionResolutionProgressions.md` generalises this into a Section Resolution system — a shared pool of resolution progressions reusable across any section type. For Comedy Song Type 1, this means adding a Verse Resolution (independently selected from the same pool), and renaming "Modulated Resolution" to "Modulated Chorus Resolution" to match the design. The pool itself is unchanged; only its name and usage are updated.

## Desired Outcome

Comedy Song Type 1 generates seven sections in this order: Chorus, Chorus Resolution, Verse, Verse Resolution, Bridge, Modulated Chorus, Modulated Chorus Resolution. The Verse Resolution is independently selected from the Section Resolution pool (may coincide with the Chorus Resolution or differ). The Modulated Chorus Resolution continues to be derived from the Chorus Resolution numerals transposed up one whole step. All resolution and optional sections are marked accordingly.

---

## Tasks

### Phase 1: Data and Generator

**Status:** Work Complete

- [x] In `ComedyProgressions.kt`: rename `chorusResolution` → `sectionResolution`
- [x] In `ComedyGenerator.kt`:
  - [x] Pick two independent progressions from `ComedyProgressions.sectionResolution`: `chorusResolutionNumerals` and `verseResolutionNumerals`
  - [x] Insert a `"Verse Resolution"` section (`isOptional = true`) between `"Verse"` and `"Bridge"`
  - [x] Rename `"Modulated Resolution"` → `"Modulated Chorus Resolution"` (label only; flags and derivation unchanged)
  - [x] The Modulated Chorus Resolution continues to use `chorusResolutionNumerals` rendered in `modulatedKey`

**Section order after this change (7 sections):**

| Index | Label                    | isOptional | isModulated |
|-------|--------------------------|------------|-------------|
| 0     | Chorus                   | false      | false       |
| 1     | Chorus Resolution        | true       | false       |
| 2     | Verse                    | false      | false       |
| 3     | Verse Resolution         | true       | false       |
| 4     | Bridge                   | true       | false       |
| 5     | Modulated Chorus         | true       | true        |
| 6     | Modulated Chorus Resolution | true    | true        |

**Technical Notes:**
The Section Resolution pool is identical to the existing `chorusResolution` pool — this is a rename only, not a data change. `chorusResolutionNumerals` and `verseResolutionNumerals` are selected independently; the design doc explicitly permits them to be the same or different. The Modulated Chorus Resolution is derived from `chorusResolutionNumerals` (not `verseResolutionNumerals`), matching the DC7 derivation rule. No model, ViewModel, or screen changes are needed.

---

### Phase 2: Tests and Build Verification

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — BUILD SUCCESSFUL
- [x] Run `./gradlew testDebugUnitTest` — all tests pass
- [x] Update `ComedyGeneratorTest.kt`:
  - Update section count: 6 → 7
  - Update label order test to include `"Verse Resolution"` at index 3 and `"Modulated Chorus Resolution"` at index 6
  - Update `isModulated` test: indices 5 and 6 true, all others false
  - Update `isOptional` test: indices 1, 3, 4, 5, 6 true; indices 0 and 2 false
  - Update existing Chorus/Modulated Chorus numeral and chord tests to use correct indices (4 → 5, 3 → 6... wait: Chorus is still 0; Modulated Chorus moves from index 4 to 5)
  - Add: Verse Resolution (`sections[3]`) has non-empty chords and numerals (covered by existing generic test)
  - Add: Chorus Resolution and Modulated Chorus Resolution share the same `romanNumerals` (indices 1 and 6)
  - Add: Chorus Resolution and Modulated Chorus Resolution have different rendered `chords`
  - [x] Update existing resolution numeral/chord tests (previously indices 1 and 5, now 1 and 6)
- [ ] Manual smoke test: tap "Random Comedy Song", verify seven section rows in correct order; verify Chorus Resolution, Verse Resolution, Bridge, Modulated Chorus, and Modulated Chorus Resolution all show "(optional)"; verify Modulated Chorus and Modulated Chorus Resolution show the modulated key; tap each section, verify audio loops; tap Regenerate, verify new song; tap back, verify return to main

---

## Notes and Risks

- The pool rename from `chorusResolution` to `sectionResolution` in `ComedyProgressions.kt` requires updating the reference in `ComedyGenerator.kt`. No other files reference `chorusResolution` directly.
- Test index references for Modulated Chorus and Modulated Chorus Resolution shift by one (from 4→5 and 5→6) due to the insertion of Verse Resolution at index 3. All affected tests must be updated.
- The Verse Resolution renders in the original key, not the modulated key — `isModulated` is false for Verse Resolution.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-02
**Phases Completed:** 1–2 (implementation complete; manual smoke test pending user verification)
**Work Deferred:** Manual smoke test (requires device/emulator)

**Accomplishments:**
- Renamed `chorusResolution` → `sectionResolution` in `ComedyProgressions.kt`
- Updated `ComedyGenerator.kt` to pick independent `chorusResolutionNumerals` and `verseResolutionNumerals` from the shared pool; generates 7 sections in order: Chorus, Chorus Resolution, Verse, Verse Resolution, Bridge, Modulated Chorus, Modulated Chorus Resolution
- Renamed "Modulated Resolution" → "Modulated Chorus Resolution"
- Updated `ComedyGeneratorTest.kt`: section count (6→7), all label/index/flag tests, resolution derivation tests

**Metrics:**
- Files modified: 3 (`ComedyProgressions.kt`, `ComedyGenerator.kt`, `ComedyGeneratorTest.kt`)
- Files added: 0
- Unit tests: all pass (assembleDebug + testDebugUnitTest both BUILD SUCCESSFUL)
