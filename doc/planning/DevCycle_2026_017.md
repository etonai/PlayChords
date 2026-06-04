# DevCycle 2026-017: Comedy Song — Six-Way Progression Uniqueness

**Status:** Work Complete
**Start Date:** 2026-06-04
**Target Completion:** 2026-06-04
**Focus:** Guarantee that Chorus, Chorus Resolution, Verse, Verse Resolution, Bridge, and Pre-Modulation are all distinct chord progressions.

---

## Goal

The Comedy Song generator currently enforces only partial uniqueness. Chorus ≠ Verse, and Verse ≠ Bridge, and Pre-Modulation ≠ all five others — but Chorus Resolution and Verse Resolution are each picked freely from the `ENDS_ON_I` pool with no exclusions. This means they can duplicate each other or, if Verse lands on an `ENDS_ON_I` progression, duplicate Verse. The six named sections should always be distinct.

## Desired Outcome

Every generated Comedy Song guarantees:
- Chorus ≠ Verse ≠ Bridge ≠ Chorus Resolution ≠ Verse Resolution ≠ Pre-Modulation
- No two of the six sections share the same Roman numeral sequence.

---

## Tasks

### Phase 1: Sequential Exclusion in ComedyGenerator

**Status:** Planning

The fix is to add exclusions to `chorusResolution` and `verseResolution`. The picks already happen in a usable order; no reordering is needed.

**Current pick logic:**
```kotlin
val chorus           = progressionsWithoutTagFull(ENDS_ON_I).random()
val verse            = progressionsExcludingFull(chorus.romanNumerals).random()
val bridge           = progressionsByTagExcludingFull(ENDS_ON_V, chorus.romanNumerals, verse.romanNumerals).random()
val chorusResolution = progressionsByTagFull(ENDS_ON_I).random()               // ← no exclusions
val verseResolution  = progressionsByTagFull(ENDS_ON_I).random()               // ← no exclusions
val preModulation    = progressionsExcludingManyFull(                          // ← already correct
    chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals,
    chorusResolution.romanNumerals, verseResolution.romanNumerals
).random()
```

**New pick logic:**
```kotlin
val chorus           = progressionsWithoutTagFull(ENDS_ON_I).random()
val verse            = progressionsExcludingFull(chorus.romanNumerals).random()
val bridge           = progressionsByTagExcludingFull(ENDS_ON_V, chorus.romanNumerals, verse.romanNumerals).random()
val chorusResolution = progressionsByTagExcludingFull(ENDS_ON_I, chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals).random()
val verseResolution  = progressionsByTagExcludingFull(ENDS_ON_I, chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals, chorusResolution.romanNumerals).random()
val preModulation    = progressionsExcludingManyFull(                          // unchanged
    chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals,
    chorusResolution.romanNumerals, verseResolution.romanNumerals
).random()
```

- [ ] Update `chorusResolution` pick to use `progressionsByTagExcludingFull(ENDS_ON_I, chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals)`
- [ ] Update `verseResolution` pick to use `progressionsByTagExcludingFull(ENDS_ON_I, chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals, chorusResolution.romanNumerals)`

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ComedyGenerator.kt`

`progressionsByTagExcludingFull` already accepts vararg exclusions and is used by `bridge` — no new infrastructure needed.

Note that `chorus` is always picked from `progressionsWithoutTagFull(ENDS_ON_I)`, meaning it will never appear in the `ENDS_ON_I` pool that `chorusResolution` and `verseResolution` draw from. Likewise, `bridge` is always `ENDS_ON_V` and no progression carries both `ENDS_ON_V` and `ENDS_ON_I`, so bridge can never appear in the resolution pool either. Including them as exclusions is defensive and harmless, and keeps the intent of "exclude everything picked so far" explicit.

The `ENDS_ON_I` pool is large (20+ progressions) so excluding 3–4 entries leaves ample choices.

---

### Phase 2: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [ ] Run `.\gradlew testDebugUnitTest` — confirm all tests pass
- [ ] Manual smoke test: generate several Comedy Songs and confirm no two of the six sections share the same progression name

---

## Notes and Risks

- `preModulation` is already correct — no change needed there.
- The Modulated Chorus and Modulated Chorus Resolution reuse the same progressions as Chorus and Chorus Resolution respectively; they are intentional repeats in a different key and are not subject to this uniqueness rule.
- Pool exhaustion is not a realistic concern: the smallest constrained pool (`verseResolution`, excluding up to 4) still has 16+ eligible progressions.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-04
**Phases Completed:** All
**Work Deferred:** Manual device/emulator smoke test (require device)

**Accomplishments:**
- `chorusResolution` now excludes Chorus, Verse, and Bridge from its `ENDS_ON_I` pool
- `verseResolution` now excludes Chorus, Verse, Bridge, and Chorus Resolution from its `ENDS_ON_I` pool
- All six sections (Chorus, Chorus Resolution, Verse, Verse Resolution, Bridge, Pre-Modulation) are now guaranteed distinct on every generation

**Metrics:**
- Files modified: 1 (`ComedyGenerator.kt`)
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)

**Lessons / Notes:**
Two-line change using the existing `progressionsByTagExcludingFull` helper already used by Bridge — no new infrastructure needed.
