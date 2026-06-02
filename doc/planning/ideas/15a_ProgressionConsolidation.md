# Chord Progression Consolidation — Implementation Plan

## Overview

This document translates the strategy in `15_ProgressionConsolidation.md` into concrete implementation steps.

The goal is to make `ChordProgressions.kt` the single source of truth for all chord progressions, and to make song generators pull from it by section-based tags rather than from separate progression files.

## Files Involved

### Files to Modify

| File | Change |
|---|---|
| `model/ProgressionTag.kt` | Add 12 new section-based tags |
| `data/ChordProgressions.kt` | Add all song-section progressions; tag existing overlapping entries |
| `data/IWantGenerator.kt` | Filter `allProgressions` by tag instead of using `IWantProgressions` |
| `data/ILoveGenerator.kt` | Filter `allProgressions` by tag instead of using `ILoveProgressions` |
| `data/ComedyGenerator.kt` | Filter `allProgressions` by tag instead of using `ComedyProgressions` |

### Files to Delete

| File | Reason |
|---|---|
| `data/IWantProgressions.kt` | Fully absorbed into `ChordProgressions.kt` |
| `data/ILoveProgressions.kt` | Fully absorbed into `ChordProgressions.kt` |
| `data/ComedyProgressions.kt` | Fully absorbed into `ChordProgressions.kt` |

## Step 1 — Add Section Tags to ProgressionTag.kt

Add 12 new enum values. Keep existing 6 values (`CADENTIAL`, `LIFT`, `LOOP`, `OPEN`, `PIVOT`, `COLOR`) unchanged.

New values to add:

```kotlin
// I Want Song
IWANT_OPENING,
IWANT_MAIN,
IWANT_DESIRE,
IWANT_CLIMAX,

// I Love Song
ILOVE_OPENING,
ILOVE_MAIN_LOVE_THEME,
ILOVE_VARIANT_LOVE_THEME,
ILOVE_CLIMAX,

// Comedy Song
COMEDY_CHORUS,
COMEDY_VERSE,
COMEDY_BRIDGE,

// Shared Resolution
SECTION_RESOLUTION,
```

## Step 2 — Resolve Pre-existing Duplicate in ChordProgressions.kt

`ChordProgressions.kt` currently contains two entries with identical numerals:

- `"Circle Turnaround"` — `["I", "vi", "ii", "V"]`
- `"Rhythm Changes"` — `["I", "vi", "ii", "V"]`

Remove `"Rhythm Changes"` and keep `"Circle Turnaround"`. The name `"Rhythm Changes"` is historically a chord sequence for a specific jazz standard, but its numerals here are identical to `"Circle Turnaround"`. Keeping only one entry satisfies the no-duplicate rule.

## Step 3 — Tag Existing ChordProgressions.kt Entries

Several progressions already in `ChordProgressions.kt` also appear in the song-section files. Add section tags to them during this step. No numerals change.

| Progression Name | Current Tags | Section Tags to Add |
|---|---|---|
| Classic Cadence `I IV V I` | CADENTIAL | IWANT_MAIN, ILOVE_MAIN_LOVE_THEME, ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX, COMEDY_CHORUS |
| Pop Axis `I V vi IV` | LIFT, LOOP | IWANT_DESIRE, COMEDY_CHORUS |
| Minor Pop Loop `vi IV I V` | OPEN, LIFT, LOOP | IWANT_DESIRE, COMEDY_CHORUS, COMEDY_BRIDGE |
| 50s Progression `I vi IV V` | LIFT, LOOP | IWANT_MAIN, IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE |
| Plagal Loop `IV I V I` | CADENTIAL, LIFT | IWANT_CLIMAX, ILOVE_VARIANT_LOVE_THEME |
| Two Five One `ii V I` | CADENTIAL | IWANT_CLIMAX, ILOVE_CLIMAX |
| Circle Turnaround `I vi ii V` | OPEN, CADENTIAL | IWANT_MAIN |
| Extended Turnaround `iii vi ii V` | OPEN, PIVOT | COMEDY_BRIDGE |
| Minor To Resolution `vi ii V I` | CADENTIAL, LIFT | IWANT_MAIN, SECTION_RESOLUTION |

## Step 4 — Add New Progressions to ChordProgressions.kt

All progressions from the three song-section files that are not already in `ChordProgressions.kt` must be added.

Each new entry follows the existing `prog(name, category, numerals, vararg tags)` format.

Use `"Song / Section"` as the category value to distinguish these from the existing musical-style categories. This makes them easy to identify during future classification work.

### New Progressions — I Want Opening

These 6 progressions are unique to `IWantProgressions.opening`. Note that `["I", "vi"]` also appears in `ILoveProgressions.opening` — assign both tags to the single entry.

| Numerals | Tags |
|---|---|
| `I vi` | IWANT_OPENING, ILOVE_OPENING |
| `I IV` | IWANT_OPENING |
| `I vi IV` | IWANT_OPENING |
| `vi IV` | IWANT_OPENING |
| `I iii vi` | IWANT_OPENING |
| `I vi IV I` | IWANT_OPENING |

### New Progressions — I Want Main

These are the `IWantProgressions.mainBody` entries not already in ChordProgressions.kt:

| Numerals | Tags |
|---|---|
| `I IV ii V` | IWANT_MAIN, ILOVE_MAIN_LOVE_THEME |
| `I iii vi ii V` | IWANT_MAIN |

### New Progressions — I Want Desire

These are `IWantProgressions.bigStatement` entries not already covered:

| Numerals | Tags |
|---|---|
| `IV V I vi` | IWANT_DESIRE, COMEDY_CHORUS |
| `I IV I V` | IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE |
| `I iii IV V` | IWANT_DESIRE, ILOVE_MAIN_LOVE_THEME, COMEDY_BRIDGE |

### New Progressions — I Want Climax

| Numerals | Tags |
|---|---|
| `IV V I` | IWANT_CLIMAX, ILOVE_CLIMAX |
| `IV V vi V I` | IWANT_CLIMAX |
| `ii IV V I` | IWANT_CLIMAX, SECTION_RESOLUTION |
| `IV V I vi IV V I` | IWANT_CLIMAX |

### New Progressions — I Love Opening

| Numerals | Tags |
|---|---|
| `I IV I` | ILOVE_OPENING |
| `I Imaj7 IV` | ILOVE_OPENING |
| `vi I IV` | ILOVE_OPENING |
| `I iii IV` | ILOVE_OPENING |
| `I IV vi I` | ILOVE_OPENING |

### New Progressions — I Love Main Love Theme

| Numerals | Tags |
|---|---|
| `IV I ii V` | ILOVE_MAIN_LOVE_THEME |
| `I Imaj7 IV V` | ILOVE_MAIN_LOVE_THEME |
| `I ii V I` | ILOVE_MAIN_LOVE_THEME |

### New Progressions — I Love Variant Love Theme

| Numerals | Tags |
|---|---|
| `I V IV I` | ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX |
| `IV V I IV` | ILOVE_VARIANT_LOVE_THEME |
| `I V I IV` | ILOVE_VARIANT_LOVE_THEME, COMEDY_VERSE |
| `I IV V vi` | ILOVE_VARIANT_LOVE_THEME |

### New Progressions — I Love Climax

| Numerals | Tags |
|---|---|
| `IV I IV V I` | ILOVE_CLIMAX |
| `I IV I V I` | ILOVE_CLIMAX |
| `ii V I IV I` | ILOVE_CLIMAX |

### New Progressions — Comedy Verse

| Numerals | Tags |
|---|---|
| `I IV V` | COMEDY_VERSE |
| `I IV` | COMEDY_VERSE |
| `I V` | COMEDY_VERSE |

### New Progressions — Comedy Bridge

| Numerals | Tags |
|---|---|
| `I IV vi V` | COMEDY_BRIDGE |
| `vi ii IV V` | COMEDY_BRIDGE |
| `I IV I ii V` | COMEDY_BRIDGE |

### New Progressions — Section Resolution

These are `ComedyProgressions.sectionResolution` entries not already covered by the `SECTION_RESOLUTION` tag assigned above:

| Numerals | Tags |
|---|---|
| `ii V I I` | SECTION_RESOLUTION |
| `IV V I I` | SECTION_RESOLUTION |
| `vi IV V I` | SECTION_RESOLUTION |
| `IV I I I` | SECTION_RESOLUTION |
| `V V I I` | SECTION_RESOLUTION |
| `I vi V I` | SECTION_RESOLUTION |
| `I V I I` | SECTION_RESOLUTION |
| `iii vi V I` | SECTION_RESOLUTION |

Note: `["vi", "ii", "V", "I"]` maps to existing `"Minor To Resolution"` and already receives `SECTION_RESOLUTION` in Step 3.
Note: `["ii", "IV", "V", "I"]` maps to new `IWANT_CLIMAX` entry above and already receives `SECTION_RESOLUTION` there.

## Step 5 — Add Helper Filter Function to ChordProgressions.kt

Add a function that generators can use to retrieve progressions by tag:

```kotlin
fun progressionsByTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag in it.tags }
        .map { it.romanNumerals }
```

This mirrors the current shape expected by the generators (`List<List<String>>`), so generator call sites change minimally.

## Step 6 — Update Generators

Replace all references to the separate progression objects with calls to `progressionsByTag`.

### IWantGenerator.kt

Before:
```kotlin
IWantProgressions.opening.random()
IWantProgressions.mainBody.random()
IWantProgressions.bigStatement.random()
IWantProgressions.climax.random()
```

After:
```kotlin
progressionsByTag(IWANT_OPENING).random()
progressionsByTag(IWANT_MAIN).random()
progressionsByTag(IWANT_DESIRE).random()
progressionsByTag(IWANT_CLIMAX).random()
```

### ILoveGenerator.kt

Before:
```kotlin
ILoveProgressions.opening.random()
ILoveProgressions.story.random()
ILoveProgressions.declaration.random()
ILoveProgressions.finale.random()
```

After:
```kotlin
progressionsByTag(ILOVE_OPENING).random()
progressionsByTag(ILOVE_MAIN_LOVE_THEME).random()
progressionsByTag(ILOVE_VARIANT_LOVE_THEME).random()
progressionsByTag(ILOVE_CLIMAX).random()
```

### ComedyGenerator.kt

Before:
```kotlin
ComedyProgressions.chorus.random()
ComedyProgressions.sectionResolution.random()
ComedyProgressions.verse.random()
ComedyProgressions.bridge.random()
```

After:
```kotlin
progressionsByTag(COMEDY_CHORUS).random()
progressionsByTag(SECTION_RESOLUTION).random()
progressionsByTag(COMEDY_VERSE).random()
progressionsByTag(COMEDY_BRIDGE).random()
```

## Step 7 — Delete Separate Progression Files

Delete:
- `data/IWantProgressions.kt`
- `data/ILoveProgressions.kt`
- `data/ComedyProgressions.kt`

Verify the build compiles cleanly with no unresolved references.

## Step 8 — Verify Progression Pool Sizes

After consolidation, verify that each tag yields the same number of progressions as the original list it replaces:

| Tag | Expected Count |
|---|---|
| IWANT_OPENING | 6 |
| IWANT_MAIN | 6 |
| IWANT_DESIRE | 6 |
| IWANT_CLIMAX | 6 |
| ILOVE_OPENING | 6 |
| ILOVE_MAIN_LOVE_THEME | 6 |
| ILOVE_VARIANT_LOVE_THEME | 6 |
| ILOVE_CLIMAX | 6 |
| COMEDY_CHORUS | 6 |
| COMEDY_VERSE | 6 |
| COMEDY_BRIDGE | 6 |
| SECTION_RESOLUTION | 10 |

This check confirms no progressions were lost or accidentally doubled during the merge.

## Execution Order

1. Add tags to `ProgressionTag.kt`
2. Remove `"Rhythm Changes"` duplicate from `ChordProgressions.kt`
3. Add section tags to existing overlapping entries in `ChordProgressions.kt`
4. Add all new progressions to `ChordProgressions.kt`
5. Add `progressionsByTag` helper to `ChordProgressions.kt`
6. Update `IWantGenerator.kt`
7. Update `ILoveGenerator.kt`
8. Update `ComedyGenerator.kt`
9. Build — confirm no compile errors
10. Delete `IWantProgressions.kt`, `ILoveProgressions.kt`, `ComedyProgressions.kt`
11. Build again — confirm clean
12. Verify pool sizes match expected counts

## Success Criteria

All success criteria from `15_ProgressionConsolidation.md` apply. The concrete verification is:

- Build passes after Steps 9 and 11.
- `progressionsByTag(TAG).size` matches the table in Step 8 for every tag.
- Running the app and generating songs from each song type produces the same range of results as before.
