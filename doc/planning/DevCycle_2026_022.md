# DevCycle 2026-022: Remove OPEN Tag

**Status:** Work Complete
**Start Date:** 2026-06-05
**Target Completion:** 2026-06-05
**Focus:** Remove the OPEN tag from all chord progressions in ChordProgressions.kt.

---

## Goal

The `OPEN` tag is carried by 27 progressions but is not used as a filter or selector anywhere in the generator or helper code. Removing it cleans up the tag surface area without changing any runtime behaviour.

## Desired Outcome

- `OPEN` tag removed from all progressions in `ChordProgressions.kt`.
- No functional change to song generation or progression selection.

---

## Tasks

### Phase 1: Remove OPEN Tag from All Progressions

**Status:** Work Complete

- [x] Remove `OPEN` from every `prog(...)` entry in `ChordProgressions.kt`

Affected progressions (27):

| Progression | Current tags containing OPEN |
|---|---|
| Minor Pop Loop | OPEN, LIFT, ENDS_ON_V |
| Descending Bass Line | OPEN, COLOR, ENDS_ON_V |
| Stepwise Lift | LIFT, OPEN, ENDS_ON_V |
| Ascending Walk | LIFT, OPEN, ENDS_ON_V |
| More Than a Feeling | LIFT, OPEN, ENDS_ON_V |
| Coldplay Progression | OPEN, LIFT, ENDS_ON_V |
| Swiftie 5th | OPEN, LIFT |
| Swiftie 4th | OPEN, LIFT, ENDS_ON_V |
| Yellow | OPEN, LIFT |
| Odo shinko | OPEN, LIFT |
| Pachelbel Canon | LONG_CHORD, OPEN, LIFT, ENDS_ON_V |
| Circle Turnaround | OPEN, CADENTIAL, ENDS_ON_V |
| Extended Turnaround | OPEN, PIVOT, ENDS_ON_V |
| Easy | OPEN, LIFT, PIVOT, ENDS_ON_V |
| Love is an Open Door | OPEN, ENDS_ON_V |
| Don't Cry Out Loud | OPEN, COLOR, PIVOT, ENDS_ON_V |
| Don't Cry Build | OPEN, COLOR, PIVOT, ENDS_ON_V |
| Line Cliche Major | OPEN, COLOR, PIVOT |
| Who Knew | OPEN, PIVOT, COLOR, ENDS_ON_V |
| Thirds Descent | OPEN, COLOR, PIVOT, ENDS_ON_V |
| Life Would Suck | OPEN, COLOR, ENDS_ON_V |
| Creep | OPEN, COLOR |
| You'll Be Back | OPEN, COLOR |
| She Use To Be Mine Bridge | OPEN, COLOR |
| Sam's Progression | OPEN, COLOR |
| Swiftie 2nd | OPEN, PIVOT |
| Sal Tlay | LIFT, OPEN, COLOR, ENDS_ON_V |

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

`OPEN` is defined in `ProgressionTag` but is not referenced in any filter predicate, generator, or helper function — it is unused metadata. Removing it from all 27 entries has no effect on runtime behaviour. The `OPEN` value can remain in the `ProgressionTag` enum for now.

---

### Phase 2: Add OPEN Tag to All 3-Chord Progressions

**Status:** Work Complete

- [x] Add `OPEN` tag to every 3-chord progression in `ChordProgressions.kt`

Affected progressions (18):

| Progression | Current tags |
|---|---|
| Coldplay Progression | LIFT, ENDS_ON_V |
| Yellow | LIFT |
| Two Five One | CADENTIAL, ENDS_ON_I, IWANT_CLIMAX, ILOVE_CLIMAX |
| Backdoor Resolution | CADENTIAL, COLOR, ENDS_ON_I |
| Minor Two Five One | CADENTIAL, COLOR |
| IWant Opening 3 | IWANT_OPENING |
| IWant Opening 5 | IWANT_OPENING |
| IWant Climax 1 | IWANT_CLIMAX, ILOVE_CLIMAX, ENDS_ON_I |
| IWant Climax 3 | IWANT_CLIMAX, SECTION_RESOLUTION |
| ILove Opening 2 | ILOVE_OPENING |
| ILove Opening 3 | ILOVE_OPENING |
| ILove Opening 4 | ILOVE_OPENING |
| ILove Opening 5 | ILOVE_OPENING |
| ILove Main 3 | ILOVE_MAIN_LOVE_THEME |
| ILove Variant 1 | ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX |
| Resolution 3 | SECTION_RESOLUTION |
| Resolution 6 | SECTION_RESOLUTION |
| Resolution 8 | SECTION_RESOLUTION |

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

`OPEN` will be added as the first tag on each affected entry. Since `OPEN` is not used in any filter predicate after Phase 1 cleanup in DC 021, this is purely metadata.

---

### Phase 3: Expand Comedy Resolution Pool to Include OPEN Progressions

**Status:** Work Complete

- [x] In `ComedyGenerator.generate()`, change `chorusResolution` and `verseResolution` to draw from progressions tagged `ENDS_ON_I` **or** `OPEN`, excluding already-used progressions

Current:
```kotlin
val chorusResolution = progressionsByTagExcludingFull(ENDS_ON_I, chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals).random()
val verseResolution = progressionsByTagExcludingFull(ENDS_ON_I, chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals, chorusResolution.romanNumerals).random()
```

Proposed:
```kotlin
val chorusResolution = (progressionsByTagFull(ENDS_ON_I) + progressionsByTagFull(OPEN))
    .distinctBy { it.romanNumerals }
    .filter { it.romanNumerals !in listOf(chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals) }
    .random()
val verseResolution = (progressionsByTagFull(ENDS_ON_I) + progressionsByTagFull(OPEN))
    .distinctBy { it.romanNumerals }
    .filter { it.romanNumerals !in listOf(chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals, chorusResolution.romanNumerals) }
    .random()
```

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ComedyGenerator.kt`

Combining both tag pools with `distinctBy` prevents duplicates when a progression carries both `ENDS_ON_I` and `OPEN`. The existing exclusion logic is preserved — each resolution is still unique from all other selected sections.

---

## Notes and Risks

- No risk to runtime behaviour — `OPEN` is not used in any filter or selection logic.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-05
**Phases Completed:** All
**Work Deferred:** None

**Accomplishments:**
- Removed `OPEN` tag from all 27 progressions that carried it
- Added `OPEN` tag to all 18 three-chord progressions
- Expanded Comedy Song resolution pool to include `OPEN` progressions alongside `ENDS_ON_I`

**Metrics:**
- Files modified: 2 (ChordProgressions.kt, ComedyGenerator.kt)
- Tests passing: all

**Lessons / Notes:**
