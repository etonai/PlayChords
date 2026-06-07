# DevCycle 2026-023: IV and V Training Screen

**Status:** Planning
**Start Date:** 2026-06-06
**Target Completion:** 2026-06-06
**Focus:** Add a IV and V chord training screen, temporarily removing I Want and I Love from the main menu to make room.

---

## Goal

Add a new training feature that presents the user with a random key and asks them to play either the IV or V chord. The user can reveal the correct chord name and hear it played. This supports ear training and chord recognition practice.

## Desired Outcome

- Random I Want and Random I Love buttons temporarily removed from the main screen.
- A new "IV and V Training" button on the main screen navigates to the new screen.
- The training screen shows a random key and a random chord prompt (IV or V), plays the I chord on load, reveals the answer and plays the chord on demand, and supports regeneration and navigation home.

---

## Tasks

### Phase 1: Temporarily Remove Random I Want and Random I Love Buttons

**Status:** Planning

- [ ] Remove the `onRandomIWant` and `onRandomILove` parameters and their buttons from `MainScreen.kt`
- [ ] Remove the `onRandomIWant` and `onRandomILove` arguments from the `MainScreen(...)` call in `Navigation.kt`
- [ ] Remove the `"iwant"` and `"ilove"` composable routes from `Navigation.kt`

**Technical Notes:**

Files: `app/src/main/java/com/playchords/ui/MainScreen.kt`, `app/src/main/java/com/playchords/Navigation.kt`

The IWant and ILove screens, ViewModels, and generators are not deleted — only the entry points are removed. They can be restored later.

---

### Phase 2: Add IV and V Training Button

**Status:** Planning

- [ ] Add `onIVVTraining: () -> Unit` parameter to `MainScreen.kt`
- [ ] Add the "IV and V Training" button to the main screen button list
- [ ] Add `onIVVTraining` argument to the `MainScreen(...)` call in `Navigation.kt`, navigating to `"ivv/training"`

**Technical Notes:**

Files: `app/src/main/java/com/playchords/ui/MainScreen.kt`, `app/src/main/java/com/playchords/Navigation.kt`

Button style should match the other `OutlinedButton` entries on the main screen.

---

### Phase 3: Create IV and V Training Screen

**Status:** Planning

- [ ] Create `app/src/main/java/com/playchords/viewmodel/IVVTrainingViewModel.kt`
- [ ] Create `app/src/main/java/com/playchords/ui/IVVTrainingScreen.kt`
- [ ] Add `"ivv/training"` composable route to `Navigation.kt`

**Screen behaviour:**

- On load: a random key and a random chord (IV or V) are selected; the I chord for that key plays automatically
- Prompt displayed: e.g. "Key of C — play the V chord"
- **Show Answer** button: reveals the chord name (e.g. "G") and plays that chord
- **Regenerate** button: selects a new key and chord, plays the I chord again, hides the answer
- **Home** button: navigates back to `"main"`

**ViewModel state:**

```kotlin
val key: String               // e.g. "C"
val targetNumeral: String     // "IV" or "V"
val targetChordName: String   // resolved via ChordMapper, e.g. "G"
val answerVisible: Boolean
```

**Technical Notes:**

Files:
- `app/src/main/java/com/playchords/viewmodel/IVVTrainingViewModel.kt`
- `app/src/main/java/com/playchords/ui/IVVTrainingScreen.kt`
- `app/src/main/java/com/playchords/Navigation.kt`

Key and numeral selection: draw from `majorKeys` and `listOf("IV", "V")` at random. Chord resolution uses `ChordMapper.renderNumerals(key, listOf(numeral))`. Audio playback follows the same pattern as `ChordPlayerViewModel` — play a single chord by name. The I chord plays on `init` and on each regenerate; the target chord plays when the answer is shown.

---

## Notes and Risks

- I Want and I Love are removed from the UI only — all backing code is preserved.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:**
**Phases Completed:**
**Work Deferred:**

**Accomplishments:**
-

**Metrics:**
- Files modified:
- Files created:
- Tests passing:

**Lessons / Notes:**
