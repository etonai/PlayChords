# DevCycle 2026-024: Bass Root Note in IV and V Training

**Status:** Work Complete
**Start Date:** 2026-06-07
**Target Completion:** 2026-06-07
**Focus:** Play the chord's root bass note alongside every chord in IV and V training.

---

## Goal

When the IV and V training screen plays any chord (the I chord on load/regenerate, or the target chord on answer reveal), it should also play a bass note one octave below the chord — specifically the root of the chord being played. For example, playing a Db chord should simultaneously sound a low Db.

## Desired Outcome

- Every chord played in `IVVTrainingScreen` is accompanied by its root note an octave lower.
- No change to screen layout, navigation, or other training features.

---

## Tasks

### Phase 1: Add Bass Root to IV and V Training Playback

**Status:** Work Complete

- [x] Add a private `withBassRoot(chordName: String): String` helper to `IVVTrainingViewModel` that extracts the root note from the chord name and appends it as a slash bass note (e.g. `"G"` → `"G/G"`, `"Db"` → `"Db/Db"`)
- [x] Update `playChord` in `IVVTrainingViewModel` to pass `withBassRoot(chordName)` to `audioEngine.playChord`

**Technical Notes:**

File: `app/src/main/java/com/playchords/viewmodel/IVVTrainingViewModel.kt`

`AudioEngine.playChord` already supports slash notation: `"Db/Db"` plays the Db chord plus a Db bass note at half rate (one octave down). No changes needed to `AudioEngine`.

Root extraction logic (mirrors the private logic already in `AudioEngine.parseChord`):
```kotlin
private fun withBassRoot(chordName: String): String {
    val root = if (chordName.length >= 2 && (chordName[1] == '#' || chordName[1] == 'b'))
        chordName.substring(0, 2)
    else
        chordName.substring(0, 1)
    return "$chordName/$root"
}
```

All three call sites in the ViewModel (`regenerate` plays the I chord twice — on init via `regenerate()` — and `showAnswer` plays the target chord) are covered by modifying the single `playChord` private function.

---

## Notes and Risks

- `AudioEngine` bass volume is set to 1.0f, matching chord tone volume.
- No other training screens are affected.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-07
**Phases Completed:** All
**Work Deferred:** None

**Accomplishments:**
- Added `withBassRoot` helper to `IVVTrainingViewModel` to append the chord's root as a slash bass note
- Every chord played in IV and V training now includes the root bass note one octave below

**Metrics:**
- Files modified: 1 (IVVTrainingViewModel.kt)

**Lessons / Notes:**
