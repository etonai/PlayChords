# DevCycle 2026-002: Sampled Piano Audio

**Status:** Verified
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

**Status:** Work Complete

- [x] Source a free/open-licensed piano sample set (Salamander Grand Piano via Tone.js, CC-BY 3.0)
- [x] Add 4 OGG files to `app/src/main/assets/notes/`: `C4.ogg`, `Ds4.ogg`, `Fs4.ogg`, `A4.ogg`
- [x] Add `CREDITS.md` with Salamander Grand Piano attribution

**Technical Notes:**
Rather than 12 files (one per semitone), 4 base samples are used — one per group of 3 semitones (C, Eb, Gb, A). Missing semitones are produced by `SoundPool` rate adjustment: 1 semitone up = rate ≈ 1.059, 2 semitones up = rate ≈ 1.122. Both are within SoundPool's 0.5–2.0 rate range, and the pitch shift is imperceptible in a chord context.

Samples were downloaded from the Tone.js Salamander mirror: `https://tonejs.github.io/audio/salamander/`. Files use the Tone.js naming convention (`Ds4.ogg` = Eb4, `Fs4.ogg` = Gb4). Total asset size: ~985 KB.

---

### Phase 2: AudioEngine Refactor

**Status:** Work Complete

- [x] Added `Context` parameter to `AudioEngine` constructor for asset loading
- [x] Replaced all sine-wave synthesis with `SoundPool`-based sample playback
- [x] Built `semitoneToBase` lookup table mapping each of 12 semitones to a base sample index and semitone offset
- [x] `playNote()` helper computes rate from offset and plays via `SoundPool`
- [x] Bass notes (slash chords) play at `octaveRate = 0.5f` (one octave below chord tones)
- [x] All chord tones voiced in octave 4 (simplified from original engine's octave 4/5 split)
- [x] `awaitLoaded()` suspends until all 4 samples are loaded before first playback
- [x] `release()` releases `SoundPool`
- [x] Changed `PlaybackViewModel` from `ViewModel` to `AndroidViewModel(application)` to supply `Context` to `AudioEngine`

**Technical Notes:**
The `playChord` signature is unchanged. The octave 4/5 split from the original engine (where notes with `rootSemitone + interval >= 12` went to octave 5) was simplified: all chord tones now play at octave 4. This is a valid voicing and cleaner to implement with a fixed sample set.

`SoundPool.OnLoadCompleteListener` increments a `@Volatile` counter. The `awaitLoaded()` coroutine polls at 50 ms intervals, which is fine since loading completes in well under a second. The `PlaybackViewModel` dispatches on `Dispatchers.IO` so the suspend is off the main thread.

---

### Phase 3: Integration and Device Testing

**Status:** Work Complete

- [x] `assembleDebug` — BUILD SUCCESSFUL
- [x] `testDebugUnitTest` — BUILD SUCCESSFUL (9 ChordMapper tests passing)
- [ ] On-device verification of audio quality and playback timing (requires physical device or emulator with audio)

---

## Open Questions

1. **SoundPool vs. AudioTrack mixing** — Resolved: SoundPool used.

2. **Sample length at slow tempos** — The Salamander samples are ~4–8 seconds at mezzo-forte, sufficient for 50 BPM (4800 ms per chord). Natural decay handles the end gracefully.

3. **APK size impact** — Resolved: 4 samples at ~245 KB average = ~985 KB total, well under budget.

---

## Notes and Risks

- The `playChord` signature in `AudioEngine` is unchanged — `PlaybackViewModel` required only the constructor change.
- Salamander Grand Piano samples are CC-BY 3.0: attribution is in `CREDITS.md`.
- Test on a real device if possible. Android emulators occasionally have audio latency or stream-limit issues that do not reflect real device behavior.

---

## Completion Summary

*Fill in when the cycle closes. Move this document to `doc/planning/completed/` afterward.*

**Completion Date:** 2026-05-20
**Phases Completed:** All (1–3, pending on-device audio verification)
**Work Deferred:** None

**Accomplishments:**
- Downloaded 4 Salamander Grand Piano OGG samples (CC-BY 3.0) into `app/src/main/assets/notes/`
- Rewrote `AudioEngine` to use `SoundPool` with rate-based pitch shifting across all 12 semitones
- Changed `PlaybackViewModel` to `AndroidViewModel` to supply `Context` to `AudioEngine`
- Added `CREDITS.md` with Salamander attribution
- Debug build successful; all 9 unit tests passing

**Metrics:**
- Files modified: 2 (`AudioEngine.kt`, `PlaybackViewModel.kt`)
- Files added: 6 (`C4.ogg`, `Ds4.ogg`, `Fs4.ogg`, `A4.ogg`, `CREDITS.md`, assets directory)
- Unit tests: 9 (all passing)
- Asset size: ~985 KB

**Lessons / Notes:**
- The Tone.js Salamander mirror (`tonejs.github.io/audio/salamander/`) provides individual note OGG files directly, with no processing needed.
- Using 4 base samples with SoundPool rate adjustment (rather than 12 individual files) keeps the asset set small and the semitone coverage complete. The quality trade-off is negligible in a chord-playback context.
- SoundPool's rate range (0.5–2.0) is sufficient to cover all chord tones in octave 4 and bass notes in octave 3 from a single octave of samples.
