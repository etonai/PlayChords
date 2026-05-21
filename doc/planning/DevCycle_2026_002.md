# DevCycle 2026-002: Sampled Piano Audio

**Status:** Planning
**Start Date:** 2026-05-20
**Target Completion:** TBD
**Focus:** Replace synthesized sine-wave tones with sampled piano audio for all chord playback.

---

## Goal

The current `AudioEngine` generates chords by summing sine waves per note, which produces a thin, electronic sound. This cycle replaces that synthesis path with real piano samples, giving the app a natural, musical quality that matches the chord-progression focus of the product.

## Desired Outcome

Chord playback uses recorded piano samples. The rest of the app — navigation, selection flow, playback screen, random mode — is unchanged. Users hear realistic piano chords instead of synthesized tones at all tempos and in all 12 keys.

---

## Tasks

### Phase 1: Asset Preparation

**Status:** Planning

- [ ] Source a free/open-licensed chromatic piano sample set (one note per semitone, C4–B4 = 12 files)
- [ ] Convert all samples to OGG Vorbis at a consistent sample rate (44100 Hz, stereo or mono)
- [ ] Trim each sample to a consistent length suitable for chord playback at the slowest supported tempo (50 BPM = 4800 ms per chord); apply a short fade-out tail
- [ ] Add the 12 OGG files to `app/src/main/assets/notes/`
- [ ] Name files by note name (e.g., `C4.ogg`, `Db4.ogg`, … `B4.ogg`)

**Technical Notes:**
The recommended sample source is the **Salamander Grand Piano** (CC-BY 3.0, Alexander Holm). It provides high-quality stereo grand piano samples for every semitone across multiple velocity layers. Use a single velocity layer (mezzo-forte) and downsample if necessary to keep APK size reasonable. Target ≤ 200 KB per sample after OGG encoding; 12 files should add ≤ 2.5 MB to the APK.

One octave (C4–B4) covers all chord tones. Bass notes (for slash chords) are played using the same 12 samples at `rate = 0.5f` via `SoundPool`, which shifts them down one octave to octave 3. This is a known quality trade-off — the transposed samples are slightly less natural than true octave-3 recordings — but keeps the asset set simple.

---

### Phase 2: AudioEngine Refactor

**Status:** Planning

- [ ] Add a `SoundPool` initialization path to `AudioEngine` that loads all 12 note samples from `assets/notes/` on first use
- [ ] Replace `generateSamples()` and the sine-wave path with a `playSamples()` method that triggers the loaded note samples for each note in the chord
- [ ] Map chord notes to sample files using the existing semitone arithmetic in `AudioEngine` (the `noteToSemitone` map and interval logic are already correct — reuse them)
- [ ] Handle bass notes (slash chords) by playing the matching octave-4 sample at `rate = 0.5f` to shift it down one octave — same logic as the existing engine, different sound source
- [ ] Preserve the existing `playChord(chordName: String, durationMs: Long)` signature so `PlaybackViewModel` requires no changes
- [ ] Release `SoundPool` resources in `release()`

**Technical Notes:**
`SoundPool` is the right tool here: it is designed for loading short sounds into memory and playing them with low latency, supports simultaneous streams, and handles the asset-loading lifecycle cleanly. Use `SoundPool.Builder` with `AudioAttributes.USAGE_MEDIA`.

`SoundPool.load(AssetFileDescriptor, priority)` loads a sample; `SoundPool.play(soundId, leftVol, rightVol, priority, loop, rate)` plays it. Chord tones use `rate = 1.0f`; bass notes use `rate = 0.5f` (one octave lower).

`SoundPool` is limited to around 8 simultaneous streams by default. A chord has at most 4 tones (maj7) plus 1 bass note = 5 streams — within that limit.

Loading 12 samples is fast but asynchronous. Use `SoundPool.OnLoadCompleteListener` to track readiness before the first `playChord` call. The `PlaybackViewModel` already dispatches on `Dispatchers.IO`, so a suspending wait-until-loaded approach fits naturally.

---

### Phase 3: Integration and Device Testing

**Status:** Planning

- [ ] Build and run on a device or emulator; verify all chord types play audibly (major, minor, maj7, dom7, dim, sus4, m7b5)
- [ ] Verify slash-chord bass notes play at the correct lower pitch (e.g., `G/B` should have a B3 bass note under a G4 chord)
- [ ] Test at the slowest (50 BPM) and fastest (120 BPM) tempos to confirm sample length and playback timing are correct
- [ ] Test all 12 keys to confirm note lookup is correct across the full semitone range
- [ ] Confirm no audio resource leaks: navigate away from playback, return, play again — no crashes or silence

---

## Open Questions

1. **SoundPool vs. AudioTrack mixing**
   Recommendation: Start with `SoundPool`. It is simpler to implement and well-suited to this use case (short polyphonic sounds, ≤ 8 simultaneous streams). Only fall back to manual `AudioTrack` PCM mixing if SoundPool introduces unacceptable latency or quality issues on target devices.

2. **Sample length at slow tempos**
   At 50 BPM, each chord lasts 4800 ms. Piano samples decay naturally; if the sample is shorter than the chord duration, it should simply end (silence is fine — the natural decay is the musical intent). Looping is not needed. Confirm the Salamander samples are long enough at the mezzo-forte layer (they typically are — ~4–8 seconds per note at that velocity).

3. **APK size impact**
   12 OGG samples at ~150–200 KB each adds approximately 2–2.5 MB to the APK. This is acceptable for a music app.

---

## Notes and Risks

- The `playChord` signature in `AudioEngine` must remain unchanged — `PlaybackViewModel` calls it directly and should not need modification.
- `SoundPool` must be initialized with the `Context` to access assets. `AudioEngine` currently takes no constructor arguments. This will require passing `Context` (or `AssetManager`) into `AudioEngine` — update the constructor and `PlaybackViewModel` accordingly.
- Salamander Grand Piano samples are CC-BY 3.0: include attribution in the app's about screen or in a `CREDITS` file in the repo.
- Test on a real device if possible. Android emulators occasionally have audio latency or stream-limit issues that do not reflect real device behavior.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:**
**Phases Completed:**
**Work Deferred:**

**Accomplishments:**

**Metrics:**

**Lessons / Notes:**
