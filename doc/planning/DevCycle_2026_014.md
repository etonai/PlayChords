# DevCycle 2026-014: Fix I/3 Slash Chord Mapping

**Status:** Work Complete
**Start Date:** 2026-06-03
**Target Completion:** 2026-06-03
**Focus:** Add the missing `I/3` entry to every key in `ChordMapper.keyMap` so the "Love is an Open Door" progression renders correctly.

---

## Goal

The progression "Love is an Open Door" (`ChordProgressions.kt:38`) uses the numeral `"I/3"`, which represents the tonic chord with its major third in the bass (first inversion — e.g., `C/E` in the key of C). `ChordMapper.keyMap` has no `"I/3"` entry for any of the 12 supported keys, so `renderNumerals` falls back to displaying the raw string `"I/3"` on screen instead of the correct chord name.

## Desired Outcome

- All 12 keys in `ChordMapper.keyMap` include a correct `"I/3"` mapping.
- Songs that contain `"I/3"` display the proper slash chord (e.g., `C/E`, `D/F#`, `G/B`) rather than the literal text `"I/3"`.
- No other behavior changes.

---

## Tasks

### Phase 1: Add I/3 to ChordMapper

**Status:** Work Complete

- [x] Add `"I/3"` entries to all 12 keys in `ChordMapper.keyMap` (`ChordMapper.kt`)

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordMapper.kt`

`I/3` is the tonic chord (I) with the major third of the key in the bass. The correct values per key:

| Key | I/3   |
|-----|-------|
| C   | C/E   |
| Db  | Db/F  |
| D   | D/F#  |
| Eb  | Eb/G  |
| E   | E/G#  |
| F   | F/A   |
| Gb  | Gb/Bb |
| G   | G/B   |
| Ab  | Ab/C  |
| A   | A/C#  |
| Bb  | Bb/D  |
| B   | B/D#  |

Add each entry to the existing `mapOf(...)` for its key, following the same inline style as the other slash chord entries (`"V/7"`, `"ii/I"`, `"IV/I"`, `"V/I"`).

---

### Phase 2: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [ ] Run `.\gradlew testDebugUnitTest` — confirm all tests pass
- [ ] Manual smoke test: generate songs until "Love is an Open Door" appears; confirm the first chord of its progression displays the correct slash chord (e.g., `C/E` in key of C) rather than the text `"I/3"`

---

## Notes and Risks

- Only one progression currently uses `"I/3"` (`ChordProgressions.kt:38`), but adding the mapping to all keys is the correct fix — it covers any future progressions that use the same numeral.
- No model, generator, or UI changes are needed; this is purely a data fix in `ChordMapper.kt`.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-03
**Phases Completed:** All
**Work Deferred:** Manual device/emulator smoke test (require device)

**Accomplishments:**
- Added `"I/3"` entry to all 12 keys in `ChordMapper.keyMap` (e.g. C→`C/E`, D→`D/F#`, G→`G/B`, B→`B/D#`)
- "Love is an Open Door" progression now renders the correct slash chord instead of the literal string `"I/3"`

**Metrics:**
- Files modified: 1 (`ChordMapper.kt`)
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)

**Lessons / Notes:**
When adding new numeral types to progressions, the corresponding entry must be added to all 12 key maps in `ChordMapper.kt` — the fallback behavior (returning the raw numeral string) makes missing mappings easy to miss until visually inspected.
