# DevCycle 2026-019: Add I → III → IV → iv Progression

**Status:** Verified
**Start Date:** 2026-06-04
**Target Completion:** 2026-06-04
**Focus:** Add a single new chord progression — I → III → IV → iv — to the progression library.

---

## Goal

Add `I → III → IV → iv` to `ChordProgressions.kt`. No new Roman numeral mappings are required — all four numerals are already present in `ChordMapper.keyMap`.

## Desired Outcome

- The progression appears in the library and is available in generated songs according to its tags.

---

## Tasks

### Phase 1: Add Progression

**Status:** Work Complete

- [x] Add `I → III → IV → iv` to `ChordProgressions.kt` in the Expressive / Color section

Proposed entry:
```kotlin
prog("Creep",  "Expressive / Color", listOf("I", "III", "IV", "iv"),  OPEN, COLOR),
```

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

All numerals (`I`, `III`, `IV`, `iv`) are already in `ChordMapper.keyMap` — no mapper changes needed. In the key of C: `C – E – F – Fm`.

`III` is the raised mediant (major chord on the third scale degree, borrowed from the parallel minor or functioning as V/vi). `iv` is the minor subdominant (borrowed from the parallel minor). Together they give the progression a bittersweet, cinematic color. The progression does not end on I or V, so it carries no `ENDS_ON_I` or `ENDS_ON_V` tag. OPEN (starts on I), COLOR (borrowed III and iv).

Progression name and any song-section tag assignments to be confirmed before implementation.

---

### Phase 2: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [x] Run `.\gradlew testDebugUnitTest` — confirm all tests pass

---

### Phase 3: Add I → V → IV Progression

**Status:** Work Complete

- [x] Add `I → V → IV` to `ChordProgressions.kt` in the Classic / Standard section

Proposed entry:
```kotlin
prog("Rock Backslide",           "Classic / Standard",     listOf("I", "V", "IV"),            OPEN, LIFT),
```

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

All numerals (`I`, `V`, `IV`) are already in `ChordMapper.keyMap` — no mapper changes needed. In the key of C: `C – G – F`. A classic 3-chord rock/blues move; starts on I (OPEN), rises to V (LIFT), then settles back on IV without resolving to I, so no `ENDS_ON_I` or `ENDS_ON_V`.

---

### Phase 4: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [x] Run `.\gradlew testDebugUnitTest` — confirm all tests pass

---

## Open Questions

1. **Progression name**
   What should this progression be called?

2. **Song-section tags**
   Should it be assigned to any song-section pools?

---

## Notes and Risks

- No `ChordMapper` changes needed.
- Single-file change (`ChordProgressions.kt`).

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-04
**Phases Completed:** Phase 1, Phase 2, Phase 3, Phase 4
**Work Deferred:** None

**Accomplishments:**
- Added `I → III → IV → iv` ("Creep") to `ChordProgressions.kt` in the Expressive / Color section with tags OPEN, COLOR
- Added `I → V → IV` ("Rock Backslide") to `ChordProgressions.kt` in the Classic / Standard section with tags OPEN, LIFT

**Metrics:**
- Files modified: 1 (ChordProgressions.kt)
- Tests passing: all

**Lessons / Notes:**
