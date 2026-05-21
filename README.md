# PlayChords

An Android app that plays chord progressions. Choose a progression, key, and tempo, then listen as each chord is played through the sequence twice. A random mode picks everything for you automatically.

## Features

- 25 chord progressions across three categories: Classic/Standard, Musical Theatre/Jazz, and Expressive/Color
- 12 major keys
- Tempos from 50 to 120 BPM
- Synthesized audio via `AudioTrack` — no sample assets required
- Random mode: one tap picks a progression, key, and tempo and goes straight to playback

## Screens

1. **Main** — choose between Select Progression and Random Progression
2. **Select Progression** — scrollable list grouped by category, with tags shown for each progression
3. **Select Key** — grid of the 12 major keys
4. **Select Tempo** — picker for 8 BPM options
5. **Playback** — shows the current chord, progression name, key, and tempo; Play Again and Back to Main when done

## Tech Stack

- Kotlin, Jetpack Compose, single-Activity architecture
- Navigation Compose, ViewModel, Coroutines
- `AudioTrack` for sine-wave chord synthesis
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
  audio/          AudioEngine — chord synthesis and playback sequencing
  data/           ChordProgressions (the full list), ChordMapper (roman numeral → chord name)
  model/          ChordProgression, ProgressionTag
  ui/             Composable screens and theme
  viewmodel/      PlaybackViewModel, SelectionViewModel
```

## Development

See `doc/planning/DevelopmentProcess.md` for the planning and dev cycle workflow.
