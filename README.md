# PlayChords

An Android app that plays chord progressions for musical practice and exploration. Choose a progression, key, and tempo, or let the app randomize everything. Play individual chords to hear how they sound, or generate a complete song structure for musical theatre improvisation — including I Want songs, I Love songs, and Comedy songs.

This app also serves as a test bed for ideas around generating chord progressions — a place to try out harmonic structures and generation approaches before committing them to a larger project.

## Features

- **82 chord progressions** across six categories: Classic/Standard, Musical Theatre/Jazz, Expressive/Color, Loops, Cinematic/Modern, and Song/Section
- **12 major keys**
- **Tempos** from 50 to 120 BPM, played twice through
- **Sampled piano audio** via `SoundPool` — four Salamander Grand Piano samples cover all 12 semitones through pitch shifting (Salamander Grand Piano by Alexander Holm, CC-BY 3.0)
- **Random Progression** — one tap picks a progression, key, and tempo and goes straight to playback
- **Play Chords** — browse all 60 common chords (5 types × 12 roots) and tap any to hear it immediately
- **Random I Want** — generates a four-section "I Want" song for musical theatre improvisation: Opening, Main Body, Desire Statement, and Climax, each with its own looping progression; no two constrained sections share the same progression
- **Random I Love** — generates a four-section "I Love" song: Opening, Main Love Theme, Variant Love Theme, and Climax; all four sections are guaranteed distinct
- **Random Comedy Song** — generates a six-section comedy song structure with key modulation: Chorus, Chorus Resolution (optional), Verse, Verse Resolution (optional), Bridge (optional), and a Modulated Chorus a whole step higher

## Screens

1. **Main** — six entry points: Select Progression, Random Progression, Play Chords, Random I Want, Random I Love, Random Comedy Song; app version shown at top
2. **Select Progression** — scrollable list grouped by category, with tags (CADENTIAL, LIFT, LOOP, OPEN, PIVOT, COLOR) shown for each progression
3. **Select Key** — grid of the 12 major keys
4. **Select Tempo** — picker for 8 BPM options (50–120)
5. **Playback** — shows the current chord large, with progression name, key, BPM, and a scrolling chord bar; Play Again and Back to Main when done
6. **Play Chords** — 12 root-note sections, each with major, minor, diminished, dominant 7, and major 7 buttons; tap to play, tap another to switch
7. **I Want Song** — key at top, progression summary list, Regenerate button, then four section rows (Opening, Main Body, Desire Statement, Climax); each row shows the chord names in the selected key; tap a section to loop it at 120 BPM, tap again to stop
8. **I Love Song** — same layout as I Want Song with four sections: Opening, Main Love Theme, Variant Love Theme (optional), and Climax
9. **Comedy Song** — scrollable screen with key at top, progression summary list, Regenerate button, then seven section rows; Chorus Resolution, Verse Resolution, Bridge, Modulated Chorus, and the modulated Chorus Resolution are marked optional; the Modulated Chorus and its resolution are played in a key one whole step higher

## Song Structures

### I Want Song

The "I Want" form is a common musical theatre structure in which a character expresses their desire. PlayChords generates one by randomly selecting a key and one progression from each section's curated pool. The Desire Statement is guaranteed not to match the Main Body, and the Climax is guaranteed not to match either.

| Section | Harmonic character |
|---|---|
| Opening | Stable and reflective — establishes the character's current state |
| Main Body | Forward-moving — introduces ii or V for storytelling momentum |
| Desire Statement | Lifted and declarative — must differ from Main Body |
| Climax | Cadential and resolved — must differ from Main Body and Desire Statement |

### I Love Song

The "I Love" form expresses a character's adoration or devotion. All four sections are guaranteed distinct.

| Section | Harmonic character |
|---|---|
| Opening | Establishes the emotional world of the song |
| Main Love Theme | The primary recurring progression — the heart of the song |
| Variant Love Theme | A contrasting or developmental variation (optional in performance) |
| Climax | Cadential resolution — brings the song to a close |

### Comedy Song

The comedy song structure is built for maximum harmonic contrast between sections and ends with a key modulation to heighten comic energy.

| Section | Notes |
|---|---|
| Chorus | The hook — does not end on I |
| Chorus Resolution | Optional cadential tag after the chorus |
| Verse | Different progression from Chorus |
| Verse Resolution | Optional cadential tag after the verse |
| Bridge | Ends on V — sets up the modulation |
| Modulated Chorus | Chorus progression played a whole step higher |
| Chorus Resolution | Resolution in the modulated key (optional) |

Each section loops independently. The pianist moves between sections as the scene demands.

## Tech Stack

- Kotlin, Jetpack Compose, single-Activity architecture
- Navigation Compose, ViewModel, Coroutines
- `SoundPool` for sampled piano playback with pitch-shift rate adjustment
- Min SDK 26, Target SDK 35 (Android 15)
- AGP 8.11.2 / Gradle 8.13 / JDK 21

## Building

Requires JDK 21. JDK 24+ is not compatible with the current AGP version.

```
./gradlew assembleDebug
```

Run unit tests:

```
./gradlew testDebugUnitTest
```

## Project Structure

```
app/src/main/java/com/playchords/
  audio/          AudioEngine — SoundPool-based sampled piano playback
  data/           ChordProgressions, ChordMapper, PlayableChords,
                  IWantGenerator, ILoveGenerator, ComedyGenerator
  model/          ChordProgression, ProgressionTag,
                  IWantSong, ILoveSong, ComedySong
  ui/             Composable screens and theme
  viewmodel/      SelectionViewModel, PlaybackViewModel,
                  ChordPlayerViewModel, IWantViewModel,
                  ILoveViewModel, ComedyViewModel

app/src/main/assets/notes/
  C4.ogg, Ds4.ogg, Fs4.ogg, A4.ogg   (Salamander Grand Piano samples)
```

## Credits

Piano samples: Salamander Grand Piano by Alexander Holm, licensed CC-BY 3.0. See `CREDITS.md`.

## License

MIT License. Copyright (c) 2026 Edward T. Tonai. See `LICENSE` for the full text.

## Development

See `doc/planning/DevelopmentProcess.md` for the planning and dev cycle workflow.
