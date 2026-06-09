# DevCycle 2026-025: V Chord Endings Screen

**Status:** Work Complete
**Start Date:** 2026-06-08
**Target Completion:** 2026-06-08
**Focus:** Add a "V Chord Endings" button on the main screen that opens a screen showing a random ENDS_ON_V progression with a random technique for handling the ending V chord.

---

## Goal

Ear training for V chord endings is useful on its own, but players also need ideas for what to do *after* the V — how to vary the resolution or approach. This cycle adds a dedicated screen that randomly pairs an `ENDS_ON_V` progression with one of three practical techniques for handling the final V chord. Players can regenerate to explore different combinations.

## Desired Outcome

- Main screen has a new "V Chord Endings" button.
- Tapping it opens a new screen that shows one randomly selected `ENDS_ON_V` progression (name + numerals) and one randomly selected technique instruction.
- A Regenerate button re-randomizes both the progression and the instruction independently.
- A Home button returns to the main screen.

---

## Tasks

### Phase 1: ViewModel

**Status:** Work Complete

- [x] Create `VChordEndingsViewModel` in `app/src/main/java/com/playchords/viewmodel/VChordEndingsViewModel.kt`
- [x] Expose `progressionName: State<String>`, `progressionNumerals: State<String>`, and `instruction: State<String>`
- [x] Implement `regenerate()` to randomly pick one `ENDS_ON_V` progression and one of the three technique instructions
- [x] Call `regenerate()` in `init`

**Technical Notes:**

File: `app/src/main/java/com/playchords/viewmodel/VChordEndingsViewModel.kt`

The three technique instructions (pick one at random each regenerate):
1. `"Change the end V to IV/5."`
2. `"End with I/V V."`
3. `"Add an extra ii, IV, or vi before V."`

Source for ENDS_ON_V progressions:
```kotlin
progressionsByTagFull(ProgressionTag.ENDS_ON_V)
```
This returns a `List<ChordProgression>`. Pick one at random. Expose its `name` as `progressionName` and join its `romanNumerals` with `" - "` as `progressionNumerals`.

No audio in this ViewModel — the screen is display-only.

Since no audio is needed, this can be a plain `ViewModel` (not `AndroidViewModel`).

---

### Phase 2: Screen UI

**Status:** Work Complete

- [x] Create `VChordEndingsScreen` in `app/src/main/java/com/playchords/ui/VChordEndingsScreen.kt`
- [x] Display the progression name in a large heading
- [x] Display the numerals string below the name
- [x] Display the instruction text below the numerals
- [x] Add a Regenerate `OutlinedButton` that calls `viewModel.regenerate()`
- [x] Add a Home `OutlinedButton` that calls `onBack()`

**Technical Notes:**

File: `app/src/main/java/com/playchords/ui/VChordEndingsScreen.kt`

Follow the layout and style pattern of `IVVTrainingScreen`:
- `Scaffold` with `TopAppBar` (title "V Chord Endings", back arrow icon calling `onBack`)
- `Column` with `padding(horizontal = 32.dp, vertical = 24.dp)` and `verticalArrangement = Arrangement.spacedBy(32.dp)`
- Progression name: `headlineLarge`, `FontWeight.Bold`, `colorScheme.primary`
- Numerals: `headlineMedium`, `FontWeight.SemiBold`, `colorScheme.onBackground`
- Instruction: `bodyLarge` or `headlineSmall`, centered, `colorScheme.onBackground`
- `Spacer(modifier = Modifier.weight(1f))` to push buttons to the bottom
- Regenerate: `OutlinedButton`, full width, height 56.dp
- Home: `OutlinedButton`, full width, height 56.dp

---

### Phase 3: Navigation and Main Screen Wiring

**Status:** Work Complete

- [x] Add `onVChordEndings: () -> Unit` parameter to `MainScreen`
- [x] Add an `OutlinedButton` for "V Chord Endings" in `MainScreen` (after the "IV and V Training" button, same style)
- [x] Add a `"vchordendings"` route in `Navigation.kt` that instantiates `VChordEndingsViewModel` and renders `VChordEndingsScreen`
- [x] Wire the new route from the `"main"` composable: `onVChordEndings = { navController.navigate("vchordendings") }`

**Technical Notes:**

Files:
- `app/src/main/java/com/playchords/ui/MainScreen.kt`
- `app/src/main/java/com/playchords/Navigation.kt`

Follow the exact same pattern used for `onIVVTraining` / `"ivv/training"`. The new composable in `NavHost`:

```kotlin
composable("vchordendings") {
    val vChordEndingsViewModel: VChordEndingsViewModel = viewModel()
    VChordEndingsScreen(
        viewModel = vChordEndingsViewModel,
        onBack = { navController.popBackStack() }
    )
}
```

---

## Notes and Risks

- No audio playback in this feature — display only. No `AndroidViewModel` needed.
- `progressionsByTagFull(ENDS_ON_V)` is defined in `ChordProgressions.kt` and returns the full `ChordProgression` objects, which include both `name` and `romanNumerals`.
- The three instructions are a closed list of exactly three strings; no data model needed beyond a local `listOf(...)`.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-08
**Phases Completed:** All
**Work Deferred:** None

**Accomplishments:**
- Created `VChordEndingsViewModel` with `regenerate()` picking a random `ENDS_ON_V` progression and a random technique instruction
- Created `VChordEndingsScreen` displaying progression name, numerals, and instruction with Regenerate and Home buttons
- Added "V Chord Endings" button to `MainScreen` and wired the `"vchordendings"` route in `Navigation.kt`

**Metrics:**
- Files modified: 2 (MainScreen.kt, Navigation.kt)
- Files created: 2 (VChordEndingsViewModel.kt, VChordEndingsScreen.kt)

**Lessons / Notes:**
