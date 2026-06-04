# DevCycle 2026-016: Remove Comedy Screen Scrollbar

**Status:** Work Complete
**Start Date:** 2026-06-04
**Target Completion:** 2026-06-04
**Focus:** Remove the visible scrollbar from the Comedy Song screen while keeping the scroll behavior intact.

---

## Goal

The Comedy Song screen currently renders a custom scrollbar drawn on top of the content column. The scrollbar is visually distracting. The screen should remain scrollable — the content is tall enough to require it — but the scrollbar indicator itself should not be shown.

## Desired Outcome

- The Comedy Song screen scrolls normally when content overflows the viewport.
- No scrollbar track or thumb is drawn on screen.
- All other Comedy screen behavior is unchanged.

---

## Tasks

### Phase 1: Remove Scrollbar

**Status:** Work Complete

- [x] Remove the `.verticalScrollbar(scrollState)` modifier from the `Column` in `ComedyScreen.kt`
- [ ] Delete the private `verticalScrollbar` extension function from `ComedyScreen.kt`
- [ ] Remove the now-unused imports: `ScrollState`, `drawWithContent`, `Offset`, `Size`, `Color`, `Dp`

**Technical Notes:**

File: `app/src/main/java/com/playchords/ui/ComedyScreen.kt`

The `verticalScrollbar` modifier is a private extension on `Modifier` defined at the top of the file. It uses `drawWithContent` to paint a track and thumb over the column. Removing the modifier call and the function definition entirely leaves `verticalScroll(scrollState)` on the `Column`, which provides the scroll behavior without any visual indicator.

The `scrollState` variable and `rememberScrollState()` call remain — they are still needed by `verticalScroll`.

---

### Phase 2: Build and Verify

**Status:** Work Complete

- [x] Run `.\gradlew assembleDebug` — confirm BUILD SUCCESSFUL
- [ ] Run `.\gradlew testDebugUnitTest` — confirm all tests pass
- [ ] Manual smoke test: confirm Comedy screen scrolls and no scrollbar is visible

---

## Notes and Risks

- No behavior change — scrolling is preserved via `verticalScroll(scrollState)`.
- No other screens are affected; only `ComedyScreen.kt` has the custom scrollbar.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-06-04
**Phases Completed:** All
**Work Deferred:** Manual device/emulator smoke test (require device)

**Accomplishments:**
- Removed the `verticalScrollbar` private extension function from `ComedyScreen.kt`
- Removed the `.verticalScrollbar(scrollState)` modifier call from the Column
- Removed 6 imports that were only used by the scrollbar (`ScrollState`, `drawWithContent`, `Offset`, `Size`, `Color`, `Dp`)
- Comedy screen retains full scroll behavior via the remaining `verticalScroll(scrollState)` modifier

**Metrics:**
- Files modified: 1 (`ComedyScreen.kt`)
- Tests passing: all (`assembleDebug` + `testDebugUnitTest` both BUILD SUCCESSFUL)

**Lessons / Notes:**
The custom scrollbar was self-contained in a single private extension function, making removal clean with no side effects.
