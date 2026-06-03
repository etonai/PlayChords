# DevCycle 2026-013: Rhyme Word Display

**Status:** Verified
**Start Date:** 2026-06-03
**Target Completion:** 2026-06-03
**Focus:** Show a randomly selected rhyme word below the progression summary on each randomly generated song screen.

---

## Goal

Each time an I Want, I Love, or Comedy song is generated, a single rhyme word is randomly chosen from the project's rhyme word list and displayed just below the progression summary block (the section name / Roman numeral lines added in DC-012). The word gives the songwriter a quick lyrical starting point without cluttering the screen. It regenerates together with the song whenever the Regenerate button is tapped.

## Desired Outcome

- All three song screens (I Want, I Love, Comedy) display one rhyme word in muted text immediately below the progression summary block and above the Regenerate button.
- The word is chosen at random from `RhymeWords.rhymeWords` each time a song is generated or regenerated.
- The rhyme word is stored on the song model so it travels with the song state rather than being re-drawn independently by the UI.

---

## Tasks

### Phase 1: Rhyme Word Data

**Status:** Work Complete

- [x] Create `app/src/main/java/com/playchords/data/RhymeWords.kt` as an `object` that holds the `rhymeWords: List<String>` from `doc/planning/ideas/18_RhymeWords.md`
- [ ] Add `rhymeWord: String` field to `IWantSong` (`IWantSong.kt`)
- [ ] Add `rhymeWord: String` field to `ILoveSong` (`ILoveSong.kt`)
- [ ] Add `rhymeWord: String` field to `ComedySong` (`ComedySong.kt`)

**Technical Notes:**

The rhyme word list lives in `doc/planning/ideas/18_RhymeWords.md`. Move it into a Kotlin `object` in the `com.playchords.data` package so generators can reference it without depending on a raw file.

Adding `rhymeWord` directly to the song-level model (not section-level) is correct — one word per generated song, not one per section.

---

### Phase 2: Generator Updates

**Status:** Work Complete

- [x] Update `IWantGenerator.generate()` to pick `RhymeWords.rhymeWords.random()` and pass it into the returned `IWantSong`
- [ ] Update `ILoveGenerator.generate()` to pick `RhymeWords.rhymeWords.random()` and pass it into the returned `ILoveSong`
- [ ] Update `ComedyGenerator.generate()` to pick `RhymeWords.rhymeWords.random()` and pass it into the returned `ComedySong`

**Technical Notes:**

Relevant files:
- `app/src/main/java/com/playchords/data/IWantGenerator.kt`
- `app/src/main/java/com/playchords/data/ILoveGenerator.kt`
- `app/src/main/java/com/playchords/data/ComedyGenerator.kt`

The pick is a single `RhymeWords.rhymeWords.random()` call added at the top of each `generate()` function alongside the key and section picks.

---

### Phase 3: UI Updates

**Status:** Planning

Add a `Text` displaying `song.rhymeWord` in muted italics to each song screen. The placement is between the progression summary `Column` and the Regenerate `OutlinedButton` (i.e., immediately below the last progression summary line and above the Regenerate button on all three screens).

- [ ] Add rhyme word `Text` to `IWantScreen.kt` between the progression summary block and the `HorizontalDivider` / Regenerate button
- [ ] Add rhyme word `Text` to `ILoveScreen.kt` between the progression summary block and the Regenerate button
- [ ] Add rhyme word `Text` to `ComedyScreen.kt` between the progression summary block and the Regenerate button

**Technical Notes:**

Relevant files:
- `app/src/main/java/com/playchords/ui/IWantScreen.kt`
- `app/src/main/java/com/playchords/ui/ILoveScreen.kt`
- `app/src/main/java/com/playchords/ui/ComedyScreen.kt`

In each screen the current layout order after the key heading is:

1. Progression summary `Column` (added DC-012)
2. Regenerate button (with a preceding `HorizontalDivider` in IWant only)
3. Section buttons

Insert the rhyme word `Text` as item 2, pushing the Regenerate button to item 3. Use `MutedText` color and `bodySmall` style (consistent with the progression summary lines above it). Italic style distinguishes it visually as a lyrical prompt rather than structural information.

Example composable:
```kotlin
Text(
    text = song.rhymeWord,
    style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
    color = MutedText
)
```

---

### Phase 4: Build and Verify

**Status:** Work Complete

- [x] Run `./gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [ ] Run `./gradlew testDebugUnitTest` — confirm all tests pass
- [ ] Manual smoke test: generate each song type several times; confirm a rhyme word appears below the progression summary on all three screens and changes on each regeneration

---

## Notes and Risks

- `RhymeWords.rhymeWords.random()` uses the default Kotlin `Random` — no seeding needed since the requirement is simply a different word each regeneration.
- Adding `rhymeWord` to the song models is non-breaking: all three song types are data classes constructed only inside their respective generators, so no call sites outside the generators need updating.
- The IWant screen has a `HorizontalDivider` between the key heading area and the Regenerate button. The rhyme word should appear before that divider (i.e., above it, grouped visually with the progression summary) to stay consistent with the ILove and Comedy screens which have no divider in that position.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-03
**Phases Completed:** All
**Work Deferred:** Manual device/emulator smoke tests (require device)

**Accomplishments:**
- Created `RhymeWords.kt` object in `com.playchords.data` with the full rhyme word list
- Added `rhymeWord: String` field to `IWantSong`, `ILoveSong`, and `ComedySong`
- Updated all three generators to pick `RhymeWords.rhymeWords.random()` and populate `rhymeWord`
- All three song screens display the rhyme word in muted italic `bodySmall` text between the progression summary block and the Regenerate button

**Metrics:**
- Files modified: 10 (`RhymeWords.kt` new; `IWantSong.kt`, `ILoveSong.kt`, `ComedySong.kt`, `IWantGenerator.kt`, `ILoveGenerator.kt`, `ComedyGenerator.kt`, `IWantScreen.kt`, `ILoveScreen.kt`, `ComedyScreen.kt` updated)
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)

**Lessons / Notes:**
Storing `rhymeWord` on the song model rather than generating it in the UI compositor keeps the word stable across recompositions and consistent with how other generated song data is handled.
