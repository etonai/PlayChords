# DevCycle 2026-018: New Chord Progressions

**Status:** Work Complete
**Start Date:** 2026-06-04
**Target Completion:** 2026-06-04
**Focus:** Add new chord progressions to the progression library.

---

## Goal

Expand the progression library with new entries. No new Roman numeral mappings are required for this cycle — all numerals used are already present in `ChordMapper.keyMap`.

## Desired Outcome

- New progressions are available in the library and appear in generated songs according to their tags.

---

## Tasks

### Phase 1: Add Progressions

**Status:** Work Complete

- [x] Add "Coldplay Progression" (`IV → I → V`) to `ChordProgressions.kt`
- [ ] Add "Swiftie 5th" (`IV → I → V → vi`) to `ChordProgressions.kt`
- [ ] Add "Swiftie 4th" (`vi → IV → I → V`) to `ChordProgressions.kt`

Proposed entries:
```kotlin
prog("Coldplay Progression",  "Classic / Standard", listOf("IV", "I", "V"),         OPEN, LIFT, ENDS_ON_V),
prog("Swiftie 5th",          "Classic / Standard", listOf("IV", "I", "V", "vi"),   OPEN, LIFT),
prog("Swiftie 4th",          "Classic / Standard", listOf("vi", "IV", "I", "V"),   OPEN, LIFT, ENDS_ON_V),
```

**Technical Notes:**

File: `app/src/main/java/com/playchords/data/ChordProgressions.kt`

All numerals (`IV`, `I`, `V`, `vi`) are already in `ChordMapper.keyMap` — no mapper changes needed.

`IV → I → V` opens on the subdominant for an immediate emotional lift, returns to the tonic, then lands on the dominant — leaving the progression open and wanting to resolve. OPEN, LIFT, ENDS_ON_V.

`IV → I → V → vi` is the same opening motion but resolves to the relative minor instead of stopping on V, giving it a bittersweet quality. It ends on vi (neither I nor V), so it carries no `ENDS_ON_I` or `ENDS_ON_V` tag. OPEN, LIFT.

`vi → IV → I → V` opens on the relative minor for a darker, more driving feel before lifting through IV and I to land on the dominant. The minor opening makes it feel more urgent than the IV-first variants. OPEN, LIFT, ENDS_ON_V.

Song-section tag assignments to be confirmed by user before or during implementation.

---

### Phase 2: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [ ] Run `.\gradlew testDebugUnitTest` — confirm all tests pass

---

## Open Questions

1. **Song-section tags for "Coldplay Progression"**
   Should it be assigned to any song-section pools (e.g. `IWANT_DESIRE`, `COMEDY_CHORUS`, `ILOVE_MAIN_LOVE_THEME`, etc.)?

2. **Additional progressions this cycle**
   Are there other progressions to add beyond the two currently listed?

---

## Notes and Risks

- No `ChordMapper` changes needed — all numerals in this cycle are already mapped.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-04
**Phases Completed:** All
**Work Deferred:** Song-section tag assignments (progressions enter the general pool only)

**Accomplishments:**
- Added "Coldplay Progression" (`IV I V`) — Classic / Standard, OPEN, LIFT, ENDS_ON_V
- Added "Swiftie 5th" (`IV I V vi`) — Classic / Standard, OPEN, LIFT
- Added "Swiftie 4th" (`vi IV I V`) — Classic / Standard, OPEN, LIFT, ENDS_ON_V

**Metrics:**
- Files modified: 1 (`ChordProgressions.kt`)
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)

**Lessons / Notes:**
All three progressions used only existing numerals — no ChordMapper changes needed.
