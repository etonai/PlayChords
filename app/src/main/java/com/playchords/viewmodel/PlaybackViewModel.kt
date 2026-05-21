package com.playchords.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.playchords.audio.AudioEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PlaybackState(
    val isPlaying: Boolean = false,
    val isDone: Boolean = false,
    val currentChordIndex: Int = 0,
    val currentLoop: Int = 1,
    val totalLoops: Int = 2
)

class PlaybackViewModel : ViewModel() {

    private val audioEngine = AudioEngine()
    private var playbackJob: Job? = null
    private var lastChords: List<String> = emptyList()
    private var lastBpm: Int = 80

    private val _state = MutableStateFlow(PlaybackState())
    val state: StateFlow<PlaybackState> = _state

    companion object {
        const val BEATS_PER_CHORD = 4L
    }

    fun startPlayback(chords: List<String>, bpm: Int) {
        if (chords.isEmpty()) return
        lastChords = chords
        lastBpm = bpm
        playbackJob?.cancel()

        val chordDurationMs = (60_000L / bpm) * BEATS_PER_CHORD

        _state.value = PlaybackState(isPlaying = true)

        playbackJob = viewModelScope.launch(Dispatchers.IO) {
            repeat(2) { loopIndex ->
                chords.forEachIndexed { chordIndex, chord ->
                    ensureActive()
                    _state.update { it.copy(currentChordIndex = chordIndex, currentLoop = loopIndex + 1) }
                    audioEngine.playChord(chord, chordDurationMs)
                }
            }
            _state.update { it.copy(isPlaying = false, isDone = true) }
        }
    }

    fun playAgain() = startPlayback(lastChords, lastBpm)

    override fun onCleared() {
        playbackJob?.cancel()
        audioEngine.release()
        super.onCleared()
    }
}
