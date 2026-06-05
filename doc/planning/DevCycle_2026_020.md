# DevCycle 2026-020: Comedy Song Verse Constraint

**Status:** Verified
**Start Date:** 2026-06-04
**Target Completion:** 2026-06-04
**Focus:** Tighten the Comedy Song verse selection so it cannot land on a resolving progression.

---

## Goal

Currently the Verse pool in `ComedyGenerator` only requires that the verse differ from the chorus. This allows a verse ending on I, which undercuts comedic tension by resolving too early. Adding a `NOT ENDS_ON_I` filter gives the verse the same open-ended quality as the chorus.

## Desired Outcome

- The verse is always selected from progressions that do not have `ENDS_ON_I`.
- The change is a one-line filter addition — no new tags, no new helper functions needed.

---

## Tasks

### Phase 1: Add ENDS_ON_I Exclusion to Verse Selection

**Status:** Work Complete

- [x] In `ComedyGenerator.generate()`, change the verse selection to also exclude `ENDS_ON_I` progressions

Current:
```kotlin
val verse = progressionsExcludingFull(chorus.romanNumerals).random()
```

Proposed:
```kotlin
val verse = progressionsWithoutTagFull(ENDS_ON_I)
    .filter { it.romanNumerals != chorus.romanNumerals }
    .random()
```

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ComedyGenerator.kt`

`progressionsWithoutTagFull(ENDS_ON_I)` returns all progressions (excluding `LONG_CHORD`) that do not have `ENDS_ON_I`. The subsequent `.filter` enforces the existing uniqueness constraint against the chorus. No changes to helper functions or tags are needed.

---

### Phase 2: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [x] Run `.\gradlew testDebugUnitTest` — confirm all tests pass

---

### Phase 3: Add Chord Progressions

**Status:** Work Complete

- [x] Add `I/bVII` to `ChordMapper.keyMap` for all 12 keys
- [x] Add `III7/vi` to `ChordMapper.keyMap` for all 12 keys
- [x] Add `I → I/bVII → IV → ii7` to `ChordProgressions.kt`
- [x] Add `I → III7/vi → vi → IV` to `ChordProgressions.kt`

Proposed ChordMapper entries for `I/bVII` (I chord with flat-seventh bass):
```
C:  "I/bVII" to "C/Bb"    Db: "I/bVII" to "Db/B"   D:  "I/bVII" to "D/C"
Eb: "I/bVII" to "Eb/Db"   E:  "I/bVII" to "E/D"    F:  "I/bVII" to "F/Eb"
Gb: "I/bVII" to "Gb/E"    G:  "I/bVII" to "G/F"    Ab: "I/bVII" to "Ab/Gb"
A:  "I/bVII" to "A/G"     Bb: "I/bVII" to "Bb/Ab"  B:  "I/bVII" to "B/A"
```

Proposed ChordMapper entries for `III7/vi` (III dominant-7 with vi in bass — secondary dominant V7/vi):
```
C:  "III7/vi" to "E7/A"     Db: "III7/vi" to "F7/Bb"   D:  "III7/vi" to "F#7/B"
Eb: "III7/vi" to "G7/C"     E:  "III7/vi" to "G#7/C#"  F:  "III7/vi" to "A7/D"
Gb: "III7/vi" to "Bb7/Eb"   G:  "III7/vi" to "B7/E"    Ab: "III7/vi" to "C7/F"
A:  "III7/vi" to "C#7/F#"   Bb: "III7/vi" to "D7/G"    B:  "III7/vi" to "D#7/G#"
```

Proposed ChordProgressions entries:
```kotlin
prog("Bass Line Descent",        "Expressive / Color",     listOf("I", "I/bVII", "IV", "ii7"),        OPEN, COLOR),
prog("Secondary Drop",           "Expressive / Color",     listOf("I", "III7/vi", "vi", "IV"),        OPEN, COLOR),
```

**Technical Notes:**

Files: `ChordMapper.kt`, `ChordProgressions.kt`

`I/bVII`: I chord with the flat seventh in the bass — descending chromatic bass move borrowed from the mixolydian mode. In C: `C → C/Bb → F → Dm7`. Ends on `ii7`.

`III7/vi`: the secondary dominant V7/vi with vi in the bass, creating a smooth voice-leading drop into vi. In C: `C → E7/A → Am → F`. Ends on IV.

---

### Phase 4: Version Update

**Status:** Work Complete

**Note:** Version updated manually by user to align with DC 20. `versionName = "1.0.20.0"` in `app/build.gradle.kts`.

---

### Phase 5: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [x] Run `.\gradlew testDebugUnitTest` — confirm all tests pass

---

## Notes and Risks

- Narrowing the verse pool could leave too few candidates in edge cases. Worth checking the pool size is healthy after the change.
- No tag or data changes needed — single-file edit in `ComedyGenerator.kt`.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:**
**Phases Completed:**
**Work Deferred:**

**Accomplishments:**
-

**Metrics:**
- Files modified:
- Tests passing:

**Lessons / Notes:**
