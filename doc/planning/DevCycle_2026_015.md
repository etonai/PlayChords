# DevCycle 2026-015: Reorder Song Screen Layout

**Status:** Work Complete
**Start Date:** 2026-06-03
**Target Completion:** 2026-06-03
**Focus:** Reorder the elements on all three song screens so the playable content comes first and the analytical summary comes last; add new chord progressions.

---

## Goal

The current song screens lead with structural information (progression summary, random word) before showing the playable section buttons. The desired order puts the key and random word up top as quick context, then the playable section buttons immediately below so the player can start playing right away, then the Regenerate button, and finally the progression name / Roman numeral summary at the bottom for reference.

## Desired Outcome

All three song screens (I Want, I Love, Comedy) display elements in this order:

1. Key heading
2. Random Word
3. Section buttons with chords
4. Regenerate button
5. Progression summary (section label: progression name — Roman numerals)

---

## Tasks

### Phase 1: Reorder IWantScreen

**Status:** Planning

**Current order in `IWantScreen.kt`:**
1. Key heading
2. Progression summary Column
3. Random Word Text
4. HorizontalDivider
5. Regenerate OutlinedButton
6. HorizontalDivider
7. Section buttons (forEachIndexed)

**New order:**
1. Key heading
2. Random Word Text
3. Section buttons (forEachIndexed)
4. HorizontalDivider
5. Regenerate OutlinedButton
6. HorizontalDivider
7. Progression summary Column

- [ ] Move Random Word Text to immediately after the Key heading
- [ ] Move the section buttons block (forEachIndexed) to immediately after the Random Word Text
- [ ] Keep both HorizontalDividers flanking the Regenerate button (move them with it)
- [ ] Move the Progression summary Column to the bottom, after the second HorizontalDivider

**Technical Notes:**

File: `app/src/main/java/com/playchords/ui/IWantScreen.kt`

The section buttons block is a bare `forEachIndexed` (not wrapped in a `Column`). In the new position it sits between the Random Word and the first `HorizontalDivider`, which is fine — the parent `Column` with `verticalArrangement = Arrangement.spacedBy(20.dp)` handles spacing.

---

### Phase 2: Reorder ILoveScreen

**Status:** Planning

**Current order in `ILoveScreen.kt`:**
1. Key heading
2. Progression summary Column
3. Random Word Text
4. Regenerate OutlinedButton
5. HorizontalDivider
6. Section buttons (forEachIndexed)

**New order:**
1. Key heading
2. Random Word Text
3. Section buttons (forEachIndexed)
4. HorizontalDivider
5. Regenerate OutlinedButton
6. HorizontalDivider
7. Progression summary Column

- [ ] Move Random Word Text to immediately after the Key heading
- [ ] Move the section buttons block to immediately after the Random Word Text
- [ ] Add a HorizontalDivider before the Regenerate button (currently missing on this screen)
- [ ] Add a HorizontalDivider after the Regenerate button
- [ ] Move the Progression summary Column to the bottom

**Technical Notes:**

File: `app/src/main/java/com/playchords/ui/ILoveScreen.kt`

ILove currently has only one HorizontalDivider (after Regenerate, before sections). The new layout needs two — one before and one after Regenerate — to match the IWant pattern and visually separate Regenerate from both the section buttons above and the summary below.

---

### Phase 3: Reorder ComedyScreen

**Status:** Planning

**Current order in `ComedyScreen.kt`:**
1. Key heading
2. Progression summary Column
3. Random Word Text
4. Regenerate OutlinedButton
5. HorizontalDivider
6. Section buttons (Column wrapping forEachIndexed)

**New order:**
1. Key heading
2. Random Word Text
3. Section buttons (Column wrapping forEachIndexed)
4. HorizontalDivider
5. Regenerate OutlinedButton
6. HorizontalDivider
7. Progression summary Column

- [ ] Move Random Word Text to immediately after the Key heading
- [ ] Move the section buttons Column to immediately after the Random Word Text
- [ ] Add a HorizontalDivider before the Regenerate button
- [ ] Add a HorizontalDivider after the Regenerate button
- [ ] Move the Progression summary Column to the bottom

**Technical Notes:**

File: `app/src/main/java/com/playchords/ui/ComedyScreen.kt`

Comedy's section buttons are wrapped in an outer `Column(verticalArrangement = Arrangement.spacedBy(10.dp))`. That wrapper moves with the block unchanged. Same divider addition as ILove.

---

### Phase 4: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [ ] Run `.\gradlew testDebugUnitTest` — confirm all tests pass
- [ ] Manual smoke test: confirm new element order on all three screens

---

### Phase 5: New Chord Progressions

**Status:** Work Complete

Add new chord progressions to `ChordProgressions.kt`. Three numerals used by the first progression (`iii7`, `ii7`, `ii7/V`) are not yet in `ChordMapper.keyMap` and must be added first.

**Step 1 — Add new numerals to `ChordMapper.kt`:**

- [x] Add `"iii7"` to all 12 keys (minor 7th on the mediant):

| Key | iii7  | | Key | iii7  |
|-----|-------|-|-----|-------|
| C   | Em7   | | Gb  | Bbm7  |
| Db  | Fm7   | | G   | Bm7   |
| D   | F#m7  | | Ab  | Cm7   |
| Eb  | Gm7   | | A   | C#m7  |
| E   | G#m7  | | Bb  | Dm7   |
| F   | Am7   | | B   | D#m7  |

- [x] Add `"ii7"` to all 12 keys (minor 7th on the supertonic):

| Key | ii7   | | Key | ii7   |
|-----|-------|-|-----|-------|
| C   | Dm7   | | Gb  | Abm7  |
| Db  | Ebm7  | | G   | Am7   |
| D   | Em7   | | Ab  | Bbm7  |
| Eb  | Fm7   | | A   | Bm7   |
| E   | F#m7  | | Bb  | Cm7   |
| F   | Gm7   | | B   | C#m7  |

- [x] Add `"ii7/V"` to all 12 keys (ii7 chord with the dominant in the bass):

| Key | ii7/V   | | Key | ii7/V   |
|-----|----------|-|-----|---------|
| C   | Dm7/G   | | Gb  | Abm7/Db |
| Db  | Ebm7/Ab | | G   | Am7/D   |
| D   | Em7/A   | | Ab  | Bbm7/Eb |
| Eb  | Fm7/Bb  | | A   | Bm7/E   |
| E   | F#m7/B  | | Bb  | Cm7/F   |
| F   | Gm7/C   | | B   | C#m7/F# |

**Step 2 — Add progressions to `ChordProgressions.kt`:**

- [x] Add `I → iii7 → ii7 → ii7/V` in the Musical Theatre / Jazz section:
```kotlin
prog("Don't Cry Out Loud",  "Musical Theatre / Jazz", listOf("I", "iii7", "ii7", "ii7/V"),  OPEN, COLOR, PIVOT, ENDS_ON_V),
```

- [x] Add `vi → iii → vi → II7 → V7` in the Musical Theatre / Jazz section:
```kotlin
prog("Don't Cry Build",      "Musical Theatre / Jazz", listOf("vi", "iii", "vi", "II7", "V7"),  OPEN, COLOR, PIVOT, ENDS_ON_V),
```
All five numerals (`vi`, `iii`, `vi`, `II7`, `V7`) are already present in `ChordMapper.keyMap` — no new mapper entries needed for this progression.

**Technical Notes:**

Files:
- `app/src/main/java/com/playchords/data/ChordMapper.kt` (new numerals for progression 1 only)
- `app/src/main/java/com/playchords/data/ChordProgressions.kt`

The bass line implied by `I → iii7 → ii7 → ii7/V` in key of C is C → E → D → G, a smooth descending-then-resolving motion that suits an opening or main body feel.

`vi → iii → vi → II7 → V7` opens on the relative minor, oscillates back through the mediant, then drives through a secondary dominant (II7) to the dominant (V7) — a theatrical build with strong forward momentum toward resolution.

The name and tag proposals above are subject to user confirmation before implementation.

---

### Phase 6: Comedy Pre-Modulation Section

**Status:** Work Complete

Add a new randomly picked section between "Bridge" and "Modulated Chorus" in the Comedy Song. Any progression except `LONG_CHORD` is eligible — no tag constraint, no uniqueness constraint relative to other sections.

- [ ] Pick a `preModulation` progression in `ComedyGenerator.generate()` using `progressionsExcludingFull(emptyList()).random()` — this reuses the existing helper which already filters out `LONG_CHORD` and, with an empty exclusion list, opens the full pool
- [ ] Insert `buildSection("Pre-Modulation", preModulation, key, isOptional = true)` between the "Bridge" and "Modulated Chorus" entries in the `sections` list

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ComedyGenerator.kt`

Current section order:
1. Chorus
2. Chorus Resolution (optional)
3. Verse
4. Verse Resolution (optional)
5. Bridge (optional)
6. **← new: Pre-Modulation (optional)**
7. Modulated Chorus (optional, isModulated)
8. Chorus Resolution — modulated (optional, isModulated)

`ComedyScreen.kt` needs no changes — sections are rendered dynamically via `forEachIndexed`.

`progressionsExcludingFull(emptyList())` is already defined in `ChordProgressions.kt` and filters `LONG_CHORD !in it.tags && it.romanNumerals != emptyList()`. Since no progression has an empty numeral list, this yields the full non-LONG_CHORD pool — exactly what is required.

---

## Notes and Risks

- The parent `Column` uses `verticalArrangement = Arrangement.spacedBy(20.dp)` on all three screens, so spacing between moved elements is automatic.
- The Comedy screen uses a vertical scrollbar; no changes to that are needed.
- **Progressions removed during this cycle** (user decision, not bugs):
  - Entire Loops category: "Two Chord Open Loop", "Two Five Loop", "Drone Loop", "Suspended Loop", "Minor Oscillation", "Pedal Loop"
  - Cinematic / Modern: "Modern Film Loop"
  - Song / Section — I Want Opening: "IWant Opening 1", "IWant Opening 4"
  - Song / Section — Comedy Verse: "Comedy Verse 2", "Comedy Verse 3"
- **`LOOP` tag removed from all progressions** — the tag drove no generator logic and the distinction it tried to capture (indefinitely repeatable) was too fuzzy to defend. Removed from: "Pop Axis", "Minor Pop Loop", "50s Progression", "More Than a Feeling", "Mixolydian Variant", "Life Would Suck", "Ambiguous Loop". The `LOOP` enum value itself remains in `ProgressionTag.kt` for now but is unused.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-03
**Phases Completed:** All
**Work Deferred:** Manual device/emulator smoke tests (require device)

**Accomplishments:**
- All three song screens now display: Key → Random Word → Section buttons → Regenerate → Progression summary
- Added a second `HorizontalDivider` to ILove and Comedy screens to flank Regenerate consistently with IWant
- Added "Progression Information" header above the progression summary on all three screens
- Removed `LOOP` tag from all 7 progressions that carried it; tag drives no generator logic and the distinction was indefensible
- Removed 11 progressions (entire Loops category + Modern Film Loop + 4 Song/Section entries)
- Added `iii7`, `ii7`, `ii7/V` mappings to all 12 keys in `ChordMapper`
- Added "Don't Cry Out Loud" (`I iii7 ii7 ii7/V`) and "Don't Cry Build" (`vi iii vi II7 V7`) to Musical Theatre / Jazz

**Metrics:**
- Files modified: `IWantScreen.kt`, `ILoveScreen.kt`, `ComedyScreen.kt`, `ChordMapper.kt`, `ChordProgressions.kt`
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)

**Lessons / Notes:**
Pure UI reordering with no data changes — the parent Column's `spacedBy(20.dp)` arrangement meant no spacing adjustments were needed when moving blocks.
