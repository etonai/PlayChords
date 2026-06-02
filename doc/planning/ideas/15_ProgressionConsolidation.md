# Chord Progression Consolidation Strategy

## Purpose

The purpose of this effort is to consolidate all chord progressions used throughout the application into a single master progression list.

This work is intended to simplify data management, reduce duplication, and create a foundation for future progression classification work.

The immediate goal is not to change song generation behavior.

The immediate goal is only to reorganize existing progression data into a more maintainable structure.

## Guiding Principle

This effort should preserve all existing functionality.

A generated song should behave exactly the same before and after consolidation.

No song templates should gain or lose available progressions.

No progression selection rules should change.

No new progression classifications should be invented during this phase.

## Current State

Currently, chord progressions are stored separately within individual song templates and section definitions.

Examples include:

### I Want Song Type 1

* Opening Progressions
* Main Body Progressions
* Big Statement of Desire Progressions
* Climax Progressions

### I Love Song Type 1

* Opening Progressions
* Main Love Theme Progressions
* Variant Love Theme Progressions
* Climax Progressions

### Comedy Song Type 1

* Chorus Progressions
* Verse Progressions
* Bridge Progressions

### Section Resolution System

* Section Resolution Progressions

This results in duplicated progressions appearing in multiple locations.

## Target State

All progressions should exist in a single master progression list.

Each progression should appear only once.

Each progression should contain a collection of classifications indicating where it may currently be used.

Example:

Progression:

I - vi - IV - V

Classifications:

* IWant.Main
* ILove.MainLoveTheme
* Comedy.Chorus

The exact classification names should mirror the existing section names.

## Initial Classification Strategy

During this phase, classifications should be derived directly from the current application structure.

No attempt should be made to create abstract musical classifications.

For example:

### I Want Song Type 1

* Opening
* Main
* Desire
* Climax

### I Love Song Type 1

* Opening
* MainLoveTheme
* VariantLoveTheme
* Climax

### Comedy Song Type 1

* Chorus
* Verse
* Bridge

### Section Resolution System

* SectionResolution

These classifications represent existing usage only.

They are not intended to be permanent.

## Duplicate Progressions

Many progressions are expected to appear in multiple sections.

For example:

I - vi - IV - V

may appear in:

* IWant.Main
* ILove.MainLoveTheme
* Comedy.Chorus

The master list should contain a single instance of the progression.

The progression should simply contain multiple classifications.

## Non-Goals

This phase does not attempt to:

* Create musical taxonomies
* Define progression characteristics
* Introduce progression scoring
* Introduce progression tiers
* Define emotional qualities
* Define harmonic complexity
* Modify generation logic

Those activities belong to future phases.

## Future Work

Once all progressions have been consolidated into a master list, future work may investigate more generalized progression classifications.

Examples may include:

* EndsOnI
* StartsOnI
* Resolving
* Loopable

However, these classifications are outside the scope of the current effort.

The current effort should focus exclusively on preserving existing behavior while centralizing progression data.

## Success Criteria

This effort is successful if:

* Every progression exists in a single master list.
* No duplicate progression entries exist.
* All existing song templates continue to function identically.
* All current section-specific progression pools can be recreated entirely from progression classifications.
* No user-visible behavior changes.
* Future classification work becomes easier because all progression data exists in a single location.
