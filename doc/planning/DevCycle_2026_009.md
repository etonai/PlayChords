# DevCycle 2026-009: Progression Consolidation

**Status:** Planning
**Start Date:** 2026-06-02
**Target Completion:** 2026-06-03
**Focus:** Consolidate all chord progressions into a single master list in `ChordProgressions.kt`, with section-based tags replacing the three separate progression files.

---

## Goal

Three separate progression files (`IWantProgressions.kt`, `ILoveProgressions.kt`, `ComedyProgressions.kt`) currently hold the pool data for song generation. This duplicates progressions that appear across multiple song types and creates multiple sources of truth. This cycle moves all progressions into `ChordProgressions.kt` and replaces per-section pool objects with tag-based filtering, as specified in `doc/planning/ideas/15a_ProgressionConsolidation.md`.

## Desired Outcome

`ChordProgressions.kt` is the only file that contains chord progressions. Each progression carries section-based tags (`IWANT_OPENING`, `COMEDY_VERSE`, etc.) that identify where it may be used. Song generators filter the master list by tag instead of referencing dedicated pool objects. The three separate progression files are deleted. All generated songs produce the same range of progressions as before the change.

---

## Tasks

### Phase 1: Tags and Master List

**Status:** Work Complete

- [x] In `model/ProgressionTag.kt`: add 12 section-based tags
  - `IWANT_OPENING`, `IWANT_MAIN`, `IWANT_DESIRE`, `IWANT_CLIMAX`
  - `ILOVE_OPENING`, `ILOVE_MAIN_LOVE_THEME`, `ILOVE_VARIANT_LOVE_THEME`, `ILOVE_CLIMAX`
  - `COMEDY_CHORUS`, `COMEDY_VERSE`, `COMEDY_BRIDGE`
  - `SECTION_RESOLUTION`
- [x] In `data/ChordProgressions.kt`: remove the `"Rhythm Changes"` entry (identical numerals to `"Circle Turnaround"` — pre-existing duplicate)
- [x] In `data/ChordProgressions.kt`: add section tags to the 9 existing entries that overlap with song-section pools (see Technical Notes)
- [x] In `data/ChordProgressions.kt`: add all new progressions not already present (see Technical Notes)
- [x] In `data/ChordProgressions.kt`: add `progressionsByTag` helper function

**Technical Notes:**

Existing entries to retag (numerals unchanged):

| Entry | Add Tags |
|---|---|
| Classic Cadence `I IV V I` | IWANT_MAIN, ILOVE_MAIN_LOVE_THEME, ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX, COMEDY_CHORUS |
| Pop Axis `I V vi IV` | IWANT_DESIRE, COMEDY_CHORUS |
| Minor Pop Loop `vi IV I V` | IWANT_DESIRE, COMEDY_CHORUS, COMEDY_BRIDGE |
| 50s Progression `I vi IV V` | IWANT_MAIN, IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE |
| Plagal Loop `IV I V I` | IWANT_CLIMAX, ILOVE_VARIANT_LOVE_THEME |
| Two Five One `ii V I` | IWANT_CLIMAX, ILOVE_CLIMAX |
| Circle Turnaround `I vi ii V` | IWANT_MAIN |
| Extended Turnaround `iii vi ii V` | COMEDY_BRIDGE |
| Minor To Resolution `vi ii V I` | IWANT_MAIN, SECTION_RESOLUTION |

New progressions to add (use category `"Song / Section"`):

**I Want Opening:**
- `I vi` → IWANT_OPENING, ILOVE_OPENING
- `I IV` → IWANT_OPENING
- `I vi IV` → IWANT_OPENING
- `vi IV` → IWANT_OPENING
- `I iii vi` → IWANT_OPENING
- `I vi IV I` → IWANT_OPENING

**I Want Main:**
- `I IV ii V` → IWANT_MAIN, ILOVE_MAIN_LOVE_THEME
- `I iii vi ii V` → IWANT_MAIN

**I Want Desire:**
- `IV V I vi` → IWANT_DESIRE, COMEDY_CHORUS
- `I IV I V` → IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE
- `I iii IV V` → IWANT_DESIRE, ILOVE_MAIN_LOVE_THEME, COMEDY_BRIDGE

**I Want Climax:**
- `IV V I` → IWANT_CLIMAX, ILOVE_CLIMAX
- `IV V vi V I` → IWANT_CLIMAX
- `ii IV V I` → IWANT_CLIMAX, SECTION_RESOLUTION
- `IV V I vi IV V I` → IWANT_CLIMAX

**I Love Opening:**
- `I IV I` → ILOVE_OPENING
- `I Imaj7 IV` → ILOVE_OPENING
- `vi I IV` → ILOVE_OPENING
- `I iii IV` → ILOVE_OPENING
- `I IV vi I` → ILOVE_OPENING

**I Love Main Love Theme:**
- `IV I ii V` → ILOVE_MAIN_LOVE_THEME
- `I Imaj7 IV V` → ILOVE_MAIN_LOVE_THEME
- `I ii V I` → ILOVE_MAIN_LOVE_THEME

**I Love Variant Love Theme:**
- `I V IV I` → ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX
- `IV V I IV` → ILOVE_VARIANT_LOVE_THEME
- `I V I IV` → ILOVE_VARIANT_LOVE_THEME, COMEDY_VERSE
- `I IV V vi` → ILOVE_VARIANT_LOVE_THEME

**I Love Climax:**
- `IV I IV V I` → ILOVE_CLIMAX
- `I IV I V I` → ILOVE_CLIMAX
- `ii V I IV I` → ILOVE_CLIMAX

**Comedy Verse:**
- `I IV V` → COMEDY_VERSE
- `I IV` → COMEDY_VERSE
- `I V` → COMEDY_VERSE

**Comedy Bridge:**
- `I IV vi V` → COMEDY_BRIDGE
- `vi ii IV V` → COMEDY_BRIDGE
- `I IV I ii V` → COMEDY_BRIDGE

**Section Resolution:**
- `ii V I I` → SECTION_RESOLUTION
- `IV V I I` → SECTION_RESOLUTION
- `vi IV V I` → SECTION_RESOLUTION
- `IV I I I` → SECTION_RESOLUTION
- `V V I I` → SECTION_RESOLUTION
- `I vi V I` → SECTION_RESOLUTION
- `I V I I` → SECTION_RESOLUTION
- `iii vi V I` → SECTION_RESOLUTION

Note: `vi ii V I` (Minor To Resolution) and `ii IV V I` already receive `SECTION_RESOLUTION` in the retag step above.

**Unplanned fix — `ui/SelectProgressionScreen.kt`:**
The `TagChip` composable used an exhaustive `when` on `ProgressionTag`. Adding the 12 new tags caused a compile error. Fixed by adding `else -> return` so section-routing tags are silently skipped in the chip display. This is correct behavior — section tags are internal and should not appear as UI chips.

Helper function to add to `ChordProgressions.kt`:

```kotlin
fun progressionsByTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag in it.tags }
        .map { it.romanNumerals }
```

---

### Phase 2: Generator Updates

**Status:** Planning

- [ ] In `data/IWantGenerator.kt`: replace `IWantProgressions` references with `progressionsByTag` calls
- [ ] In `data/ILoveGenerator.kt`: replace `ILoveProgressions` references with `progressionsByTag` calls
- [ ] In `data/ComedyGenerator.kt`: replace `ComedyProgressions` references with `progressionsByTag` calls

**Technical Notes:**

Mapping of old references to new calls:

| Generator | Old | New |
|---|---|---|
| IWantGenerator | `IWantProgressions.opening.random()` | `progressionsByTag(IWANT_OPENING).random()` |
| IWantGenerator | `IWantProgressions.mainBody.random()` | `progressionsByTag(IWANT_MAIN).random()` |
| IWantGenerator | `IWantProgressions.bigStatement.random()` | `progressionsByTag(IWANT_DESIRE).random()` |
| IWantGenerator | `IWantProgressions.climax.random()` | `progressionsByTag(IWANT_CLIMAX).random()` |
| ILoveGenerator | `ILoveProgressions.opening.random()` | `progressionsByTag(ILOVE_OPENING).random()` |
| ILoveGenerator | `ILoveProgressions.story.random()` | `progressionsByTag(ILOVE_MAIN_LOVE_THEME).random()` |
| ILoveGenerator | `ILoveProgressions.declaration.random()` | `progressionsByTag(ILOVE_VARIANT_LOVE_THEME).random()` |
| ILoveGenerator | `ILoveProgressions.finale.random()` | `progressionsByTag(ILOVE_CLIMAX).random()` |
| ComedyGenerator | `ComedyProgressions.chorus.random()` | `progressionsByTag(COMEDY_CHORUS).random()` |
| ComedyGenerator | `ComedyProgressions.sectionResolution.random()` | `progressionsByTag(SECTION_RESOLUTION).random()` |
| ComedyGenerator | `ComedyProgressions.verse.random()` | `progressionsByTag(COMEDY_VERSE).random()` |
| ComedyGenerator | `ComedyProgressions.bridge.random()` | `progressionsByTag(COMEDY_BRIDGE).random()` |

Add import of `progressionsByTag` and the new tag names to each generator file.

---

### Phase 3: Cleanup and Verification

**Status:** Planning

- [ ] Run `./gradlew assembleDebug` — must succeed before deleting old files
- [ ] Delete `data/IWantProgressions.kt`
- [ ] Delete `data/ILoveProgressions.kt`
- [ ] Delete `data/ComedyProgressions.kt`
- [ ] Run `./gradlew assembleDebug` again — must succeed with files removed
- [ ] Run `./gradlew testDebugUnitTest` — all tests pass
- [ ] Verify progression pool sizes by tag match expected counts (see below)
- [ ] Manual smoke test: generate one song of each type; verify sections and progressions appear as expected

**Technical Notes:**

Expected pool sizes after consolidation:

| Tag | Expected |
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

These counts can be verified with a unit test or a temporary log statement during development.

---

## Notes and Risks

- The `"Rhythm Changes"` entry has the same numerals as `"Circle Turnaround"` (`I vi ii V`). This is a pre-existing duplicate in `ChordProgressions.kt` unrelated to the song generators (neither was used by a generator before this cycle). Removing it is safe and required for the no-duplicate rule.
- The existing 6 musical tags (`CADENTIAL`, `LIFT`, `LOOP`, `OPEN`, `PIVOT`, `COLOR`) are untouched. The new 12 section tags coexist with them on the same entries.
- Generator files currently import the progression objects directly. After the update they will import `progressionsByTag` and the tag enum values from `ProgressionTag`. Watch for import line updates.
- The `ComedyGenerator` picks `chorusResolutionNumerals` and `verseResolutionNumerals` separately. Both become `progressionsByTag(SECTION_RESOLUTION).random()` — same behavior.
- No model, ViewModel, screen, or test file changes are expected. All changes are in data layer files only.
- No new progressions are introduced from a song-generation perspective. All added progressions already exist in the separate pool files.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:**
**Phases Completed:**
**Work Deferred:**

**Accomplishments:**

**Metrics:**
- Files modified:
- Files deleted:
- Unit tests:
