# DevCycle 026: Hide Random Progression Button

**Status:** Work Complete
**Start Date:** 2026-06-09
**Target Completion:** 2026-06-09
**Focus:** Hide the Random Progression button from the UI without deleting it.

---

## Goal

The Random Progression button should be hidden from the UI for now. We may want to bring it back in a future cycle, so the button and its supporting logic will be kept in place — just made invisible.

## Desired Outcome

The Random Progression button is hidden (not visible) in the running app. All code supporting the button remains intact for potential future use.

---

## Tasks

### Phase 1: Hide Random Progression Button

**Status:** Work Complete

- [x] Locate the Random Progression button in the layout file(s)
- [x] Set the button's visibility to `gone` (or `invisible` if layout space should be preserved)
- [ ] Build and verify the app runs without the button visible

**Technical Notes:**
Search for the button by label or ID (e.g., `random`, `randomProgression`, `btn_random`) in layout XML. Set `android:visibility="gone"` on the button element. No Kotlin logic changes should be needed.

---

## Notes and Risks

- Low risk: no code is deleted; reverting is trivial.
- Using `gone` collapses the button's space; use `invisible` if the layout requires the space to be preserved.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:**
**Phases Completed:**
**Work Deferred:**

**Accomplishments:**

**Metrics:**
- Files modified:

**Lessons / Notes:**
