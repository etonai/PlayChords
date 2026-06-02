# Improvised Showtune Generator - I Want Song Type 1

## Goal

Generate a simple four-section "I Want" song suitable for improvised musical theatre performance.

The generated song consists of:

1. Opening
2. Main Body
3. Big Statement of Desire
4. Climax

The user selects a key, or the app selects a random key. The application chooses one progression from each section's progression pool.

The resulting progression sequence becomes the harmonic roadmap for the improvised song.

## Emotional Structure

### Opening

Reflective, dreamy, or dissatisfied.

This section establishes the character's current emotional state.

### Main Body

Storytelling and explanation.

This section lets the character describe their situation, problem, or longing.

### Big Statement of Desire

The character directly states what they want.

This section should feel more open, lifted, and emotionally forward-moving than the Main Body.

### Climax

Emotional commitment.

This section resolves the want into a clear musical arrival.

## Section Identity Rules

Each section should have a distinct harmonic function.

### Opening Rules

The Opening should stay harmonically simple.

Allowed emphasis:

* I
* vi
* IV

Avoid strong cadences unless necessary.

### Main Body Rules

The Main Body should introduce forward motion.

Required tendency:

* Include ii or V

This gives the section a stronger storytelling engine than the Opening.

### Big Statement of Desire Rules

The Big Statement should feel larger and more direct.

Required tendency:

* Must include V
* Should usually include IV or vi

This makes the desire feel active rather than merely reflective.

### Climax Rules

The Climax should clearly arrive.

Required tendency:

* Must end on I

Preferred cadences:

* IV - V - I
* ii - V - I

## Progression Pools

### Opening Progressions

* I - vi
* I - IV
* I - vi - IV
* vi - IV
* I - iii - vi
* I - vi - IV - I

### Main Body Progressions

* I - vi - ii - V
* I - vi - IV - V
* I - IV - ii - V
* I - iii - vi - ii - V
* vi - ii - V - I
* I - IV - V - I

### Big Statement of Desire Progressions

* I - V - vi - IV
* I - vi - IV - V
* IV - V - I - vi
* I - IV - I - V
* vi - IV - I - V
* I - iii - IV - V

### Climax Progressions

* IV - V - I
* ii - V - I
* IV - I - V - I
* IV - V - vi - V - I
* ii - IV - V - I
* IV - V - I - vi - IV - V - I

## Example Output

Selected key: C major

Opening:

C - Am

Main Body:

C - Am - Dm - G

Big Statement of Desire:

C - G - Am - F

Climax:

F - G - C

## Version 1 Constraints

No modulation.

All progressions remain in the selected key.

Use major keys only.

Each section uses one selected progression.

The pianist may loop each section as long as needed before moving on.

## Version 1 Design Principle

The app should not merely generate four random progressions.

It should generate a simple dramatic harmonic arc:

Opening: stable and reflective
Main Body: moving and explanatory
Big Statement: lifted and declarative
Climax: cadential and resolved
