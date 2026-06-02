# Improvised Showtune Generator - I Love Song Type 1

## Goal

Generate a simple four-section "I Love" song suitable for improvised musical theatre performance.

The generated song consists of:

1. Tender Opening
2. Story
3. Declaration
4. Joyful Finale

The user selects a key, or the app selects a random key. The application chooses one progression from each section's progression pool.

The resulting progression sequence becomes the harmonic roadmap for the improvised song.

## Emotional Structure

### Tender Opening

Warm, intimate, and loving.

This section establishes the character's affectionate state. It should feel gentle and inviting, not urgent. The character is in a good place.

### Story

Storytelling about the beloved.

This section lets the character describe who or what they love and why. It needs forward motion — the character is actively sharing something — but the warmth of the Opening should carry through.

### Declaration

The direct, joyful statement of love.

This section should feel open, lifted, and celebratory. The character commits to the feeling and says it plainly. It is brighter and more active than the Story.

### Joyful Finale

Triumphant and resolved.

This section celebrates the love with a clear, satisfying musical arrival. It should feel complete and joyful — a moment of shared warmth with the audience.

## Section Identity Rules

Each section should have a distinct harmonic function.

### Tender Opening Rules

The Opening should stay warm and intimate.

Allowed emphasis:

* I
* IV
* vi
* Imaj7

Avoid strong cadences. The goal is to establish a loving glow, not to arrive anywhere yet.

### Story Rules

The Story should introduce forward motion.

Required tendency:

* Include IV or ii

This gives the section a narrative engine — the character is explaining, building toward something.

### Declaration Rules

The Declaration should feel open and joyful.

Required tendency:

* Must include V
* Should include IV

This makes the love feel active and outward-facing, not merely reflective.

### Joyful Finale Rules

The Finale should arrive triumphantly.

Required tendency:

* Must end on I

Preferred cadences:

* IV - V - I
* I - V - IV - I
* ii - V - I

## Progression Pools

### Tender Opening Progressions

* I - vi
* I - IV - I
* I - Imaj7 - IV
* vi - I - IV
* I - iii - IV
* I - IV - vi - I

### Story Progressions

* I - IV - V - I
* I - IV - ii - V
* IV - I - ii - V
* I - Imaj7 - IV - V
* I - ii - V - I
* I - iii - IV - V

### Declaration Progressions

* I - V - IV - I
* IV - V - I - IV
* I - IV - V - I
* IV - I - V - I
* I - V - I - IV
* I - IV - V - vi

### Joyful Finale Progressions

* IV - V - I
* I - IV - V - I
* I - V - IV - I
* IV - I - IV - V - I
* I - IV - I - V - I
* ii - V - I - IV - I

## Example Output

Selected key: G major

Tender Opening:

G - Gmaj7 - C

Story:

G - C - Am - D

Declaration:

C - D - G - C

Joyful Finale:

C - D - G

## Version 1 Constraints

No modulation.

All progressions remain in the selected key.

Use major keys only.

Each section uses one selected progression.

The pianist may loop each section as long as needed before moving on.

## Version 1 Design Principle

The app should not merely generate four random progressions.

It should generate a simple emotional arc of love:

Tender Opening: warm and intimate
Story: moving and explanatory
Declaration: lifted and joyful
Joyful Finale: triumphant and resolved

## Harmonic Character vs. I Want Song

The I Love Song differs from the I Want Song in harmonic emphasis:

* I Love favours IV prominently — IV is a warm, open sound associated with affirmation
* I Love avoids the urgency of the I Want song — fewer unresolved dominants in early sections
* The line cliché (I - Imaj7 - I7 - IV) is idiomatic for love songs and appears in the Opening and Story pools
* The Joyful Finale is celebratory rather than merely cadential — it arrives with warmth, not just resolution
