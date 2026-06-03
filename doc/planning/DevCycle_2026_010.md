# DevCycle 2026-010: Minor Fixes — Modulated Label and Long Chord Tag

**Status:** Work Complete
**Start Date:** 2026-06-02
**Target Completion:** 2026-06-02
**Focus:** Rename the Modulated Chorus Resolution label in Comedy songs, and introduce a LONG_CHORD tag that excludes lengthy progressions from song generation.

---

## Goal

Two small improvements to clean up behaviour introduced in earlier cycles. First, the Comedy song section labelled "Modulated Chorus Resolution" is redundant — its modulated context is already established by the preceding "Modulated Chorus" section, so the label should simply read "Chorus Resolution". Second, one progression in the master list is notably longer than the rest and does not suit song generation; tagging it LONG_CHORD and filtering it out of `progressionsByTag` keeps generation quality consistent without removing the progression from the browse list.

## Desired Outcome

Comedy songs show "Chorus Resolution" as the label for the final modulated section. No progression with more than 5 chords is ever selected during song generation. The LONG_CHORD tag is available for future progressions that may be added later.

---

## Tasks

### Phase 1: Rename Modulated Chorus Resolution Label

**Status:** Work Complete

- [x] In `data/ComedyGenerator.kt`: change the label `"Modulated Chorus Resolution"` to `"Chorus Resolution"`

**Technical Notes:**

Only the `label` string changes. The section's `isOptional`, `isModulated`, and numeral/chord derivation are all unchanged. After this change, two sections in a Comedy song share the label `"Chorus Resolution"`: index 1 (non-modulated) and index 6 (modulated). They are distinguished in the UI by the modulated key displayed alongside index 6.

No model, test index, or flag changes are required.

---

### Phase 2: LONG_CHORD Tag and Generation Filter

**Status:** Work Complete

- [x] In `model/ProgressionTag.kt`: add `LONG_CHORD` enum value
- [x] In `data/ChordProgressions.kt`: tag `"IWant Climax 4"` (numerals `IV V I vi IV V I`, 7 chords) with `LONG_CHORD`
- [x] In `data/ChordProgressions.kt`: update `progressionsByTag` to exclude `LONG_CHORD` progressions

**Technical Notes:**

`LONG_CHORD` definition: a progression whose `romanNumerals` list has more than 5 entries.

Current progressions with more than 5 chords:

| Entry | Numerals | Count |
|---|---|---|
| IWant Climax 4 | `IV V I vi IV V I` | 7 |

Only one progression qualifies today. The tag exists for any future additions.

Updated `progressionsByTag` signature stays the same; only the filter gains an additional condition:

```kotlin
fun progressionsByTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag in it.tags && LONG_CHORD !in it.tags }
        .map { it.romanNumerals }
```

`LONG_CHORD` progressions remain in `allProgressions` and are visible in the progression browser. They are only excluded from song generation (i.e. from `progressionsByTag` results).

Effect on pool sizes after this change:

| Tag | Before | After |
|---|---|---|
| IWANT_CLIMAX | 6 | 5 |
| All others | unchanged | unchanged |

---

### Phase 3: ENDS_ON_I Tag

**Status:** Work Complete

- [x] In `model/ProgressionTag.kt`: add `ENDS_ON_I` enum value
- [x] In `data/ChordProgressions.kt`: add `ENDS_ON_I` to all 31 progressions whose final numeral is `"I"`

**Technical Notes:**

Definition: a progression receives `ENDS_ON_I` when its last `romanNumerals` entry is exactly `"I"` (uppercase, no decoration). Entries ending on `"i"` (minor tonic), `"IV"`, `"V"`, etc. do not qualify.

This tag is informational only. It does not affect `progressionsByTag` filtering or any generator behaviour.

Qualifying progressions by group:

| Group | Entries |
|---|---|
| Classic / Standard | Classic Cadence, Plagal Loop, Axis Variant |
| Musical Theatre / Jazz | Two Five One, Backdoor Resolution, Major Six Turnaround |
| Expressive / Color | Major To Minor Four, Minor To Resolution, Flat Seven Color, Chromatic Mediants, Deceptive Cycle |
| I Want Opening | IWant Opening 6 |
| I Want Climax | IWant Climax 1, IWant Climax 2, IWant Climax 3, IWant Climax 4 |
| I Love Opening | ILove Opening 1, ILove Opening 5 |
| I Love Main Love Theme | ILove Main 3 |
| I Love Variant Love Theme | ILove Variant 1 |
| I Love Climax | ILove Climax 1, ILove Climax 2, ILove Climax 3 |
| Section Resolution | Resolution 1–8 (all eight) |

Total: 31 progressions.

---

### Phase 4: Use ENDS_ON_I for Resolution Selection

**Status:** Work Complete

- [x] In `data/ComedyGenerator.kt`: change both resolution pool calls from `progressionsByTag(SECTION_RESOLUTION)` to `progressionsByTag(ENDS_ON_I)`

**Technical Notes:**

Two call sites change — `chorusResolutionNumerals` and `verseResolutionNumerals` on lines 18–19 of `ComedyGenerator.kt`. No other files require changes.

Effect on pool sizes:

| Section | Old pool | Old size | New pool | New size |
|---|---|---|---|---|
| Chorus Resolution | SECTION_RESOLUTION | 10 | ENDS_ON_I | 30 |
| Verse Resolution | SECTION_RESOLUTION | 10 | ENDS_ON_I | 30 |

The ENDS_ON_I pool size is 30 (31 tagged progressions minus `IWant Climax 4`, which carries `LONG_CHORD` and is filtered out by `progressionsByTag`).

`SECTION_RESOLUTION` remains as a classification tag on its 10 progressions. It is no longer used for any generator pick, but it is retained for future use and as meaningful metadata.

No test changes are expected — no test asserts which pool is used, only that resolution sections have non-empty numerals and that the chorus resolution numerals are shared between sections 1 and 6.

---

### Phase 5: Comedy Chorus Pulls from Non-Resolving Progressions

**Status:** Work Complete

- [x] In `data/ChordProgressions.kt`: add `progressionsWithoutTag` helper function
- [x] In `data/ComedyGenerator.kt`: change chorus selection to `progressionsWithoutTag(ENDS_ON_I).random()`

**Technical Notes:**

The intent is that a Chorus progression should not resolve — any progression that ends on something other than `I` is a valid candidate. The `ENDS_ON_I` tag precisely identifies the complement to exclude.

New helper to add to `ChordProgressions.kt`:

```kotlin
fun progressionsWithoutTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag !in it.tags && LONG_CHORD !in it.tags }
        .map { it.romanNumerals }
```

In `ComedyGenerator.kt`, line 15 changes from:

```kotlin
val chorusNumerals = progressionsByTag(COMEDY_CHORUS).random()
```

to:

```kotlin
val chorusNumerals = progressionsWithoutTag(ENDS_ON_I).random()
```

Effect on pool size:

| Section | Old pool | Old size | New pool | New size |
|---|---|---|---|---|
| Chorus | COMEDY_CHORUS | 6 | not ENDS_ON_I | 37 |

The 37-entry pool is every progression in `allProgressions` that carries neither `ENDS_ON_I` nor `LONG_CHORD`. The `COMEDY_CHORUS` tag is retained on its 6 progressions as classification metadata but is no longer used for generation.

No test changes are expected.

---

### Phase 6: Comedy Verse Excludes the Chosen Chorus

**Status:** Work Complete

- [x] In `data/ChordProgressions.kt`: add `progressionsExcluding` helper function
- [x] In `data/ComedyGenerator.kt`: change verse selection to `progressionsExcluding(chorusNumerals).random()`

**Technical Notes:**

New helper to add to `ChordProgressions.kt`:

```kotlin
fun progressionsExcluding(excluded: List<String>): List<List<String>> =
    allProgressions
        .filter { LONG_CHORD !in it.tags && it.romanNumerals != excluded }
        .map { it.romanNumerals }
```

In `ComedyGenerator.kt`, the verse line changes from:

```kotlin
val verseNumerals = progressionsByTag(COMEDY_VERSE).random()
```

to:

```kotlin
val verseNumerals = progressionsExcluding(chorusNumerals).random()
```

The verse line must come after `chorusNumerals` is assigned, which it already does.

Effect on pool size:

| Section | Old pool | Old size | New pool | New size |
|---|---|---|---|---|
| Verse | COMEDY_VERSE | 6 | all except LONG_CHORD and chosen chorus | 66 |

The 66 is derived from 68 total progressions minus 1 `LONG_CHORD` entry (`IWant Climax 4`) minus the 1 already-chosen chorus progression. The chorus is always drawn from the 37 non-`ENDS_ON_I` entries, which are all non-`LONG_CHORD`, so the subtraction of 1 is always exact.

`COMEDY_VERSE` is retained as classification metadata on its 6 progressions but is no longer used for generation.

No test changes are expected.

---

### Phase 7: ENDS_ON_V Tag

**Status:** Work Complete

- [x] In `model/ProgressionTag.kt`: add `ENDS_ON_V` enum value
- [x] In `data/ChordProgressions.kt`: add `ENDS_ON_V` to all 18 progressions whose final numeral is `"V"`

**Technical Notes:**

Definition: a progression receives `ENDS_ON_V` when its last `romanNumerals` entry is exactly `"V"`. Decorated variants such as `"V/7"`, `"V7"`, `"II7"` do not qualify.

This tag is informational only. It does not affect any generator behaviour.

Qualifying progressions by group:

| Group | Entries |
|---|---|
| Classic / Standard | Minor Pop Loop, 50s Progression, Descending Bass Line |
| Musical Theatre / Jazz | Circle Turnaround, Extended Turnaround, Dominant Lift Setup, Chromatic Walk-Up |
| I Want Main | IWant Main 1, IWant Main 2 |
| I Want Desire | IWant Desire 2, IWant Desire 3 |
| I Love Main Love Theme | ILove Main 1, ILove Main 2 |
| Comedy Verse | Comedy Verse 1, Comedy Verse 3 |
| Comedy Bridge | Comedy Bridge 1, Comedy Bridge 2, Comedy Bridge 3 |

Total: 18 progressions.

---

### Phase 8: Comedy Bridge Uses ENDS_ON_V Excluding Chorus and Verse

**Status:** Work Complete

- [x] In `data/ChordProgressions.kt`: add `progressionsByTagExcluding` helper function
- [x] In `data/ComedyGenerator.kt`: change bridge selection to `progressionsByTagExcluding(ENDS_ON_V, chorusNumerals, verseNumerals).random()`

**Technical Notes:**

New helper to add to `ChordProgressions.kt`:

```kotlin
fun progressionsByTagExcluding(tag: ProgressionTag, vararg excluded: List<String>): List<List<String>> =
    allProgressions
        .filter { tag in it.tags && LONG_CHORD !in it.tags && it.romanNumerals !in excluded }
        .map { it.romanNumerals }
```

In `ComedyGenerator.kt`, the bridge line changes from:

```kotlin
val bridgeNumerals = progressionsByTag(COMEDY_BRIDGE).random()
```

to:

```kotlin
val bridgeNumerals = progressionsByTagExcluding(ENDS_ON_V, chorusNumerals, verseNumerals).random()
```

The bridge line must come after both `chorusNumerals` and `verseNumerals` are assigned, which it already does.

Effect on pool size:

| Section | Old pool | Old size | New pool | New size |
|---|---|---|---|---|
| Bridge | COMEDY_BRIDGE | 6 | ENDS_ON_V minus chorus and verse | 16–18 |

The pool is 18 `ENDS_ON_V` progressions minus however many of the chorus and verse picks happen to carry `ENDS_ON_V`. Chorus is drawn from the 37 non-`ENDS_ON_I` progressions (11 of which also have `ENDS_ON_V`); verse is drawn from all 66 non-chorus progressions (up to 17 of which have `ENDS_ON_V`). In the worst case both chorus and verse are in `ENDS_ON_V`, leaving 16 bridge options.

`COMEDY_BRIDGE` is retained as classification metadata on its 6 progressions but is no longer used for generation.

No test changes are expected.

---

## Notes and Risks

- The `LONG_CHORD` filter in `progressionsByTag` applies universally. If a future progression is tagged both `SECTION_RESOLUTION` and `LONG_CHORD`, it will also be excluded from generation. This is intentional.
- `LONG_CHORD` should not appear as a chip in `SelectProgressionScreen.kt`. The existing `else -> return` guard added in DC 9 already handles this — no further change needed.
- No existing unit tests assert on IWANT_CLIMAX pool size, so no test updates are expected.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-02
**Phases Completed:** All
**Work Deferred:** None

**Accomplishments:**
- Renamed label `"Modulated Chorus Resolution"` → `"Chorus Resolution"` in `ComedyGenerator.kt`
- Added `LONG_CHORD` enum value to `ProgressionTag.kt`
- Tagged `"IWant Climax 4"` (`IV V I vi IV V I`, 7 chords) with `LONG_CHORD` in `ChordProgressions.kt`
- Updated `progressionsByTag` to filter out `LONG_CHORD` progressions from song generation
- Updated `ComedyGeneratorTest.kt` to assert the renamed label at index 6
- Added `ENDS_ON_I` enum value to `ProgressionTag.kt`
- Tagged all 31 progressions whose final numeral is `"I"` with `ENDS_ON_I` in `ChordProgressions.kt`
- Changed Chorus Resolution and Verse Resolution in `ComedyGenerator.kt` to pull from `ENDS_ON_I` (30 progressions) instead of `SECTION_RESOLUTION` (10 progressions)
- Added `progressionsWithoutTag` helper to `ChordProgressions.kt`
- Changed Comedy Chorus in `ComedyGenerator.kt` to pull from `progressionsWithoutTag(ENDS_ON_I)` (37 progressions) instead of `COMEDY_CHORUS` (6 progressions)
- Added `progressionsExcluding` helper to `ChordProgressions.kt`
- Changed Comedy Verse in `ComedyGenerator.kt` to pull from `progressionsExcluding(chorusNumerals)` (66 progressions) instead of `COMEDY_VERSE` (6 progressions)
- Added `ENDS_ON_V` enum value to `ProgressionTag.kt`
- Tagged all 18 progressions whose final numeral is `"V"` with `ENDS_ON_V` in `ChordProgressions.kt`
- Added `progressionsByTagExcluding` helper to `ChordProgressions.kt`
- Changed Comedy Bridge in `ComedyGenerator.kt` to pull from `ENDS_ON_V` excluding chorus and verse (16–18 progressions) instead of `COMEDY_BRIDGE` (6 progressions)

**Metrics:**
- Files modified: 4 (`ComedyGenerator.kt`, `ProgressionTag.kt`, `ChordProgressions.kt`, `ComedyGeneratorTest.kt`)
- Unit tests: all pass (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)
