# DevCycle 2026-021: New Chord Progressions

**Status:** Work Complete
**Start Date:** 2026-06-04
**Target Completion:** 2026-06-05
**Focus:** Add two new chord progressions and remove LONG_CHORD filtering from song generators.

---

## Goal

Add four progressions to `ChordProgressions.kt`: "Something Plus" (`I Imaj7 I7 IV iv I`), "Odo shinko" (`IV V iii vi`), "Pachelbel Canon" (`I V vi iii IV I IV V`), and "Sam's Progression" (`I iii vi ivadd6`). Also remove the `LONG_CHORD` filter from all helper functions so long progressions can appear in song generators.

## Desired Outcome

- Four new progression entries in `ChordProgressions.kt`.
- `ivadd6` added to `ChordMapper.kt` for all 12 keys.
- `ivadd6` added to `ChordMapper.kt` for all 12 keys; all other chords already mapped.
- `LONG_CHORD` tag retained as metadata but no longer drives filtering.

---

## Tasks

### Phase 1: Add Progression

**Status:** Work Complete

- [x] Add `I → Imaj7 → I7 → IV → iv → I` to `ChordProgressions.kt` in the `Expressive / Color` section
- [x] Add `IV → V → iii → vi` to `ChordProgressions.kt` in the `Classic / Standard` section
- [x] Add `I → V → vi → iii → IV → I → IV → V` to `ChordProgressions.kt` in the `Classic / Standard` section
- [x] Add `ivadd6` to `ChordMapper.kt` for all 12 keys
- [x] Add `I → iii → vi → ivadd6` to `ChordProgressions.kt` in the `Expressive / Color` section

Proposed entry:
```kotlin
prog("Something Plus",   "Expressive / Color",     listOf("I", "Imaj7", "I7", "IV", "iv", "I"),  LONG_CHORD, CADENTIAL, COLOR, ENDS_ON_I),
```

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

The descending chromatic inner-voice line (`I → Imaj7 → I7`) drives toward IV (as a secondary dominant resolution), then borrows the minor subdominant `iv` before settling on `I`. In C: `C → Cmaj7 → C7 → F → Fm → C`.

Tags:
- `LONG_CHORD` — 6-chord progression; label only after Phase 2 removes the filter
- `CADENTIAL` — resolves cleanly to I
- `COLOR` — the borrowed `iv` chord adds modal color
- `ENDS_ON_I` — final chord is I

No new chords or ChordMapper entries needed. Place after "Line Cliche Major" in the `Expressive / Color` block.

`IV → V → iii → vi`: lifts through the dominant then settles on vi. All chords diatonic; no ChordMapper changes needed. In C: `F → G → Em → Am`. Ends on vi — open, no resolution.

```kotlin
prog("Odo shinko",          "Classic / Standard",     listOf("IV", "V", "iii", "vi"),            OPEN, LIFT),
```

Tags:
- `OPEN` — ends on vi, no resolution to I
- `LIFT` — IV→V push gives the phrase forward momentum

Place in the `Classic / Standard` block.

`I → V → vi → iii → IV → I → IV → V`: the Pachelbel Canon progression. All eight chords diatonic; no ChordMapper changes needed. In C: `C → G → Am → Em → F → C → F → G`. Ends on V — open. 8 chords, so tagged `LONG_CHORD`.

```kotlin
prog("Pachelbel Canon",                    "Classic / Standard",     listOf("I", "V", "vi", "iii", "IV", "I", "IV", "V"),  LONG_CHORD, OPEN, LIFT, ENDS_ON_V),
```

Tags:
- `LONG_CHORD` — 8-chord progression; label only after Phase 2 removes the filter
- `OPEN` — ends on V, no final resolution
- `LIFT` — the steady descending bass with rising harmonic energy
- `ENDS_ON_V` — final chord is V

Place in the `Classic / Standard` block.

`I → iii → vi → ivadd6`: descends through the diatonic minor chords then lands on the borrowed minor iv with an added major 6th, giving it an open, bittersweet colour. In C: `C → Em → Am → Fmadd6`. Ends on ivadd6 — open.

`ivadd6` is the minor iv chord with the major 6th added above the root. ChordMapper entries needed for all 12 keys:
```
C:  "ivadd6" to "Fmadd6"    Db: "ivadd6" to "Gbmadd6"  D:  "ivadd6" to "Gmadd6"
Eb: "ivadd6" to "Abmadd6"   E:  "ivadd6" to "Amadd6"   F:  "ivadd6" to "Bbmadd6"
Gb: "ivadd6" to "Bmadd6"    G:  "ivadd6" to "Cmadd6"   Ab: "ivadd6" to "Dbmadd6"
A:  "ivadd6" to "Dmadd6"    Bb: "ivadd6" to "Ebmadd6"  B:  "ivadd6" to "Emadd6"
```

```kotlin
prog("Sam's Progression",    "Expressive / Color",     listOf("I", "iii", "vi", "ivadd6"),        OPEN, COLOR),
```

Tags:
- `OPEN` — ends on ivadd6, no resolution to I
- `COLOR` — borrowed iv with added 6th gives it modal colour

Place in the `Expressive / Color` block.

---

### Phase 2: Remove LONG_CHORD Filtering from Helper Functions

**Status:** Work Complete

- [x] Remove `&& LONG_CHORD !in it.tags` from all nine filter predicates in `ChordProgressions.kt`

Affected functions (all in `ChordProgressions.kt`):
- `progressionsByTag`
- `progressionsWithoutTag`
- `progressionsExcluding`
- `progressionsByTagExcluding`
- `progressionsByTagFull`
- `progressionsByTagExcludingFull`
- `progressionsWithoutTagFull`
- `progressionsExcludingFull`
- `progressionsExcludingManyFull`

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

All nine helper functions share the same `LONG_CHORD !in it.tags` guard. Removing it from each predicate makes long progressions eligible for random selection in Comedy, IWant, and ILove generators without touching any generator code.

`LONG_CHORD` remains in the `ProgressionTag` enum and on the "Something Plus" entry as a metadata label — it just stops driving filtering behavior.

---

## Notes and Risks

- After Phase 2, `LONG_CHORD` becomes a metadata-only label with no filtering effect. Long progressions will be eligible for selection in all song generators.
- User is making manual edits to some existing progressions during this cycle.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-05
**Phases Completed:** All
**Work Deferred:** None

**Accomplishments:**
- Added "Something Plus", "Odo shinko", "Pachelbel Canon", and "Sam's Progression" to `ChordProgressions.kt`
- Added `ivadd6` to `ChordMapper.kt` for all 12 keys
- Removed `LONG_CHORD` filtering from all nine helper functions in `ChordProgressions.kt`

**Metrics:**
- Files modified: 2 (ChordMapper.kt, ChordProgressions.kt)
- Tests passing: all

**Lessons / Notes:**
