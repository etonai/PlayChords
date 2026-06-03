# DevCycle 2026-011: Song Scaffold Progression Import

**Status:** Planning
**Start Date:**
**Target Completion:**
**Focus:** Import all chord progressions from Song Scaffold into the PlayChords master list, resolving naming conflicts and adding new entries.

---

## Goal

The Song Scaffold application contains a curated list of chord progressions described in `doc/planning/ideas/16_SongScaffoldProgressions.md`. This cycle brings those progressions into `ChordProgressions.kt` as the authoritative master list. Conflict resolution rules from the ideas document apply: when a progression exists in both apps with different names, Song Scaffold's name takes priority; tags are combined; Song Scaffold's category takes priority.

## Desired Outcome

Every progression from Song Scaffold exists in `ChordProgressions.kt`. The four naming conflicts are resolved in favour of Song Scaffold names. Sixteen new progressions are added. All existing section-based tags and generation behaviour are preserved.

---

## Tasks

### Phase 1: Resolve Naming Conflicts

**Status:** Planning

Four progressions are already in `ChordProgressions.kt` by numerals but carry different names and categories. For each: rename, update category, and merge tags. No numerals change.

- [ ] Rename `"IWant Opening 2"` → `"Two Chord Open Loop"`, category `"Loops"`, add tags `OPEN`, `LOOP`
- [ ] Rename `"IWant Main 1"` → `"Mostly Me"`, category `"Musical Theatre / Jazz"`, add tags `LIFT`, `CADENTIAL`
- [ ] Rename `"IWant Desire 3"` → `"Sal Tlay"`, category `"Cinematic / Modern"`, add tags `LIFT`, `OPEN`, `COLOR`
- [ ] Rename `"Comedy Bridge 1"` → `"More Than a Feeling"`, category `"Classic / Standard"`, add tags `LIFT`, `LOOP`, `OPEN`

**Technical Notes:**

Full before/after for each conflict:

| PlayChords Name | SS Name | Numerals | Current Tags | Tags to Add | New Category |
|---|---|---|---|---|---|
| IWant Opening 2 | Two Chord Open Loop | `I IV` | IWANT_OPENING | OPEN, LOOP | Loops |
| IWant Main 1 | Mostly Me | `I IV ii V` | IWANT_MAIN, ILOVE_MAIN_LOVE_THEME, ENDS_ON_V | LIFT, CADENTIAL | Musical Theatre / Jazz |
| IWant Desire 3 | Sal Tlay | `I iii IV V` | IWANT_DESIRE, ILOVE_MAIN_LOVE_THEME, COMEDY_BRIDGE, ENDS_ON_V | LIFT, OPEN, COLOR | Cinematic / Modern |
| Comedy Bridge 1 | More Than a Feeling | `I IV vi V` | COMEDY_BRIDGE, ENDS_ON_V | LIFT, LOOP, OPEN | Classic / Standard |

Song Scaffold internal duplicates — no action required:
- **Rhythm Changes** (`I vi ii V`) = Circle Turnaround, which already exists in PlayChords. Rhythm Changes was removed from PlayChords in DC 9. No re-addition.
- **Epic Rise** (`vi IV I V`) = Minor Pop Loop, which already exists in PlayChords. Epic Rise's tags (LIFT, LOOP) are already present on Minor Pop Loop. No action.

---

### Phase 2: Add New Progressions

**Status:** Planning

Sixteen progressions from Song Scaffold are not present in `ChordProgressions.kt` by numerals. Add each using the `prog()` helper. Apply `ENDS_ON_I` or `ENDS_ON_V` where the final numeral qualifies (exactly `"I"` or exactly `"V"`).

- [ ] Add all 16 new progressions to `ChordProgressions.kt`

**Technical Notes:**

New progressions, grouped by category, with all tags:

**Loops (5 entries):**

| Name | Numerals | Tags |
|---|---|---|
| Two Chord Open Loop | `I IV` | OPEN, LOOP — *conflict with IWant Opening 2; handled in Phase 1* |
| Two Five Loop | `ii V` | OPEN, LOOP, ENDS_ON_V |
| Drone Loop | `I ♭VII` | OPEN, LOOP, COLOR |
| Suspended Loop | `I Vsus4` | OPEN, LOOP |
| Minor Oscillation | `i ♭VI` | OPEN, LOOP, COLOR |
| Pedal Loop | `I ii/I IV/I V/I` | OPEN, LOOP, COLOR |

*(Two Chord Open Loop is handled in Phase 1 as a rename of IWant Opening 2, not a new entry.)*

So 5 truly new Loops entries: Two Five Loop, Drone Loop, Suspended Loop, Minor Oscillation, Pedal Loop.

**Cinematic / Modern (3 entries):**

| Name | Numerals | Tags |
|---|---|---|
| Modern Film Loop | `i ♭VI III ♭VII` | OPEN, LOOP, COLOR |
| Lydian Lift | `I II IV I` | LIFT, COLOR, ENDS_ON_I |
| Ambiguous Loop | `I V ii IV` | OPEN, LOOP, PIVOT |

**Musical Theatre / Jazz (2 entries):**

| Name | Numerals | Tags |
|---|---|---|
| Easy | `I iii ii V` | OPEN, LIFT, PIVOT, ENDS_ON_V |
| Love is an Open Door | `I I/3 IV V` | OPEN, ENDS_ON_V |

**Classic / Standard (3 entries):**

| Name | Numerals | Tags |
|---|---|---|
| Stepwise Lift | `I ii IV V` | LIFT, OPEN, ENDS_ON_V |
| Ascending Walk | `I ii iii V` | LIFT, OPEN, ENDS_ON_V |
| More Than a Feeling | `I IV vi V` | LIFT, LOOP, OPEN, ENDS_ON_V — *conflict with Comedy Bridge 1; handled in Phase 1* |

*(More Than a Feeling is handled in Phase 1 as a rename of Comedy Bridge 1, not a new entry.)*

So 2 truly new Classic / Standard entries: Stepwise Lift, Ascending Walk.

**Expressive / Color (4 entries):**

| Name | Numerals | Tags |
|---|---|---|
| Who Knew | `I ii vi V` | OPEN, PIVOT, COLOR, ENDS_ON_V |
| Thirds Descent | `I iii vi V` | OPEN, COLOR, PIVOT, ENDS_ON_V |
| Four Three Drop | `I IV iii V` | COLOR, PIVOT, ENDS_ON_V |
| Life Would Suck | `I vi iii V` | OPEN, COLOR, LOOP, ENDS_ON_V |

Total new entries: 5 + 3 + 2 + 2 + 4 = **16 progressions**.

**Sal Tlay** (`I iii IV V`) is handled in Phase 1 as a rename of IWant Desire 3 and is not counted here.

---

### Phase 3: Build and Verify

**Status:** Planning

- [ ] Run `./gradlew assembleDebug` — must succeed
- [ ] Run `./gradlew testDebugUnitTest` — all tests must pass
- [ ] Confirm `allProgressions` count increased by 16 (from 68 to 84)
- [ ] Manual smoke test: generate one song of each type; verify sections and progressions appear correctly

---

## Open Questions

1. **Two Chord Open Loop category conflict**
   Song Scaffold assigns this to `"Loops"`. In PlayChords it is currently `"Song / Section"` (as IWant Opening 2). After renaming, the category becomes `"Loops"` per the conflict rule. However, it still carries `IWANT_OPENING` — a `"Loops"` entry with a section tag is an intentional outcome of the merge.

---

## Notes and Risks

- The four renames in Phase 1 affect `ChordProgressions.kt` only. No generator files reference progression names directly; generators use tag filtering, so renames are safe.
- Two new categories are introduced: `"Loops"` and `"Cinematic / Modern"`. These are display-only strings; no code handles categories programmatically.
- `ENDS_ON_I` and `ENDS_ON_V` are applied to new progressions based on the DC 10 tagging rules (exactly `"I"` or `"V"` as the final numeral). Song Scaffold did not define these tags; they are applied by PlayChords convention.
- Decorated finals such as `"Vsus4"`, `"V/I"`, and `"♭VII"` do not qualify for `ENDS_ON_V` or `ENDS_ON_I`.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:**
**Phases Completed:**
**Work Deferred:**

**Accomplishments:**

**Metrics:**
- Files modified:
- Progressions renamed: 4
- Progressions added: 16
- Unit tests:
