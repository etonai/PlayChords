# DevCycle 2026-012: Progression Uniqueness and Summary Display

**Status:** Work Complete
**Start Date:** 2026-06-03
**Target Completion:** 2026-06-03
**Focus:** Enforce distinct progression picks within I Want and I Love songs, and display progression names and Roman numerals on all song screens.

---

## Goal

Two song types currently allow the same progression to appear in multiple sections. The I Want Song should prevent the Desire Statement from duplicating the Main Body, and the Climax from duplicating either. The I Love Song should ensure all four sections are distinct. Separately, all three random song screens should show a compact summary of which named progression was chosen for each section, placed between the Key display and the Regenerate button so the player can see what they got at a glance.

## Desired Outcome

- Generating an I Want Song never produces the same progression in Desire Statement + Main Body, or in Climax + Desire Statement, or in Climax + Main Body.
- Generating an I Love Song never produces the same progression in any two sections.
- All three song screens (I Want, I Love, Comedy) display a progression summary list — one line per section, showing the progression name and Roman numerals — between the Key heading and the Regenerate button.

---

## Tasks

### Phase 1: I Want Song — Progression Uniqueness

**Status:** Work Complete

The Opening section has no uniqueness constraint. The three constrained sections are picked in order: Main Body first, then Desire Statement (must differ from Main Body), then Climax (must differ from both).

- [x] Rewrite `IWantGenerator.generate()` to pick sections sequentially with exclusions:
  - Opening: pick freely from `IWANT_OPENING` pool (no constraint)
  - Main Body: pick freely from `IWANT_MAIN` pool
  - Desire Statement: pick from `IWANT_DESIRE` pool excluding Main Body numerals
  - Climax: pick from `IWANT_CLIMAX` pool excluding Main Body and Desire Statement numerals
- [x] Use the existing `progressionsByTagExcluding` helper for the constrained picks

**Technical Notes:**

`IWantGenerator` is at `app/src/main/java/com/playchords/data/IWantGenerator.kt`. The current `generate()` picks all four sections independently via `buildSection`, which calls `pool.random()` with no exclusion logic.

The helper `progressionsByTagExcluding(tag, vararg excluded: List<String>)` already exists in `ChordProgressions.kt:144` and filters by tag while excluding specific numeral lists — it handles the constrained picks without new infrastructure.

New generation order:

```
val openingNumerals  = progressionsByTag(IWANT_OPENING).random()
val mainNumerals     = progressionsByTag(IWANT_MAIN).random()
val desireNumerals   = progressionsByTagExcluding(IWANT_DESIRE, mainNumerals).random()
val climaxNumerals   = progressionsByTagExcluding(IWANT_CLIMAX, mainNumerals, desireNumerals).random()
```

Each pool is large enough that exclusions will not produce empty lists in practice, but a runtime check is worthwhile.

---

### Phase 2: I Love Song — Progression Uniqueness

**Status:** Work Complete

All four I Love sections must be distinct. Sections are picked in order; each new pick excludes all previously picked numerals.

- [x] Rewrite `ILoveGenerator.generate()` to pick sections sequentially with exclusions:
  - Opening: pick freely from `ILOVE_OPENING` pool
  - Main Love Theme: pick from `ILOVE_MAIN_LOVE_THEME` pool excluding Opening
  - Variant Love Theme: pick from `ILOVE_VARIANT_LOVE_THEME` pool excluding Opening and Main Love Theme
  - Climax: pick from `ILOVE_CLIMAX` pool excluding all three preceding sections

**Technical Notes:**

`ILoveGenerator` is at `app/src/main/java/com/playchords/data/ILoveGenerator.kt`. Same pattern as Phase 1 — use `progressionsByTagExcluding` for each constrained pick.

New generation order:

```
val openingNumerals  = progressionsByTag(ILOVE_OPENING).random()
val mainNumerals     = progressionsByTagExcluding(ILOVE_MAIN_LOVE_THEME, openingNumerals).random()
val variantNumerals  = progressionsByTagExcluding(ILOVE_VARIANT_LOVE_THEME, openingNumerals, mainNumerals).random()
val climaxNumerals   = progressionsByTagExcluding(ILOVE_CLIMAX, openingNumerals, mainNumerals, variantNumerals).random()
```

---

### Phase 3: Progression Summary Display

**Status:** Work Complete

All three song screens must show each section's progression name and Roman numerals in a compact list below the Key heading and above the Regenerate button. The section models currently store `romanNumerals` but not the progression `name`, so the name must be added to each section model and populated in each generator.

**Model changes — add `progressionName: String` to each section type:**

- [x] Add `progressionName: String` to `IWantSection` (`IWantSong.kt`)
- [x] Add `progressionName: String` to `ILoveSection` (`ILoveSong.kt`)
- [x] Add `progressionName: String` to `ComedySection` (`ComedySong.kt`)

**Generator changes — pick full `ChordProgression` objects instead of bare numeral lists:**

- [x] Add a `progressionsByTagFull(tag)` helper to `ChordProgressions.kt` that returns `List<ChordProgression>`
- [x] Add a `progressionsByTagExcludingFull(tag, vararg excluded)` helper for constrained picks that also returns `List<ChordProgression>`
- [x] Add `progressionsWithoutTagFull` and `progressionsExcludingFull` helpers for Comedy's chorus/verse picking
- [x] Update `IWantGenerator` to pick `ChordProgression` objects and populate `progressionName` from `progression.name`
- [x] Update `ILoveGenerator` to pick `ChordProgression` objects and populate `progressionName`
- [x] Update `ComedyGenerator` to pick `ChordProgression` objects and populate `progressionName`

**UI changes — add progression summary block to each screen:**

The summary block sits between the Key `Text` and the Regenerate `OutlinedButton` in each screen's `Column`. It lists one row per section: `"[Section Label]: [Progression Name] — [Roman Numerals]"` in muted text at `bodySmall` size.

- [x] Add progression summary block to `IWantScreen.kt` (between Key heading and first divider)
- [x] Add progression summary block to `ILoveScreen.kt` (between Key heading and Regenerate button)
- [x] Add progression summary block to `ComedyScreen.kt` (between Key heading and Regenerate button)

**Technical Notes:**

Relevant files:
- `app/src/main/java/com/playchords/model/IWantSong.kt`
- `app/src/main/java/com/playchords/model/ILoveSong.kt`
- `app/src/main/java/com/playchords/model/ComedySong.kt`
- `app/src/main/java/com/playchords/data/ChordProgressions.kt`
- `app/src/main/java/com/playchords/data/IWantGenerator.kt`
- `app/src/main/java/com/playchords/data/ILoveGenerator.kt`
- `app/src/main/java/com/playchords/data/ComedyGenerator.kt`
- `app/src/main/java/com/playchords/ui/IWantScreen.kt`
- `app/src/main/java/com/playchords/ui/ILoveScreen.kt`
- `app/src/main/java/com/playchords/ui/ComedyScreen.kt`

`ComedyGenerator` already uses sequential exclusion for some sections (Chorus → Verse → Bridge). It currently calls `progressionsByTag`, `progressionsWithoutTag`, `progressionsExcluding`, and `progressionsByTagExcluding` — all returning bare numeral lists. The new `*Full` helpers return the same filtered sets as full `ChordProgression` objects so existing exclusion logic can be carried over unchanged.

For the Comedy screen, the Modulated Chorus and its Chorus Resolution repeat progressions that were already picked — this is expected and those sections should still show the same progression name in the summary.

---

### Phase 4: Build and Verify

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — BUILD SUCCESSFUL
- [x] Run `./gradlew testDebugUnitTest` — all tests pass
- [ ] Manual smoke test: generate each song type several times; confirm no duplicate progressions appear in constrained sections
- [ ] Manual smoke test: confirm the progression summary list appears correctly on all three screens

---

## Open Questions

1. **Comedy section uniqueness**
   The Comedy Generator already enforces: Chorus ≠ Verse, and Bridge ≠ Chorus/Verse. This cycle does not change Comedy uniqueness rules — only the display is affected. If tighter Comedy uniqueness is desired, that belongs in a future cycle.

2. **Pool exhaustion**
   If a tag pool becomes very small (e.g., only two progressions share a tag), excluding one or two from it could leave zero options. This is unlikely given the current pool sizes but should be checked. A defensive `.takeIf { it.isNotEmpty() }?.random()` fallback (falling back to the unconstrained pool) would guard against crashes without changing the happy path.

---

## Notes and Risks

- `progressionsByTagExcluding` already exists and supports multiple vararg exclusions — no new exclusion infrastructure is needed for Phases 1 and 2.
- The `*Full` helpers in Phase 3 are additive to `ChordProgressions.kt`; no existing helper changes.
- Adding `progressionName` to section models is a non-breaking addition since all three section types are data classes constructed only within their respective generators.
- The IWant screen currently places a `HorizontalDivider` between the Key heading and the Regenerate button. The progression summary should be inserted between the Key heading and that divider (or between the divider and the button — keep whatever looks cleaner and is consistent across all three screens).

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-03
**Phases Completed:** All
**Work Deferred:** Manual device/emulator smoke tests (require device)

**Accomplishments:**
- I Want Song: Desire Statement and Climax now guaranteed distinct from each other and from Main Body
- I Love Song: all four sections now guaranteed distinct from each other
- Added `progressionsByTagFull`, `progressionsByTagExcludingFull`, `progressionsWithoutTagFull`, `progressionsExcludingFull` helpers to `ChordProgressions.kt`
- Added `progressionName` field to `IWantSection`, `ILoveSection`, and `ComedySection`
- All three generators updated to pick full `ChordProgression` objects and populate the name
- All three song screens now display a progression summary list (name + Roman numerals per section) between the Key heading and the Regenerate button

**Metrics:**
- Files modified: 10 (`IWantSong.kt`, `ILoveSong.kt`, `ComedySong.kt`, `ChordProgressions.kt`, `IWantGenerator.kt`, `ILoveGenerator.kt`, `ComedyGenerator.kt`, `IWantScreen.kt`, `ILoveScreen.kt`, `ComedyScreen.kt`)
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)
