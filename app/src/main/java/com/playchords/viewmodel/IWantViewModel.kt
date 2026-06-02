package com.playchords.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.playchords.audio.AudioEngine
import com.playchords.data.IWantGenerator
import com.playchords.model.IWantSong
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IWantViewModel(application: Application) : AndroidViewModel(application) {

    private val audioEngine = AudioEngine(application)
    private var playbackJob: Job? = null

    private val _song = MutableStateFlow(IWantGenerator.generate())
    val song: StateFlow<IWantSong> = _song

    private val _playingSection = MutableStateFlow<Int?>(null)
    val playingSection: StateFlow<Int?> = _playingSection

    fun playSection(index: Int) {
        if (_playingSection.value == index) {
            stopPlayback()
            return
        }
        playbackJob?.cancel()
        _playingSection.value = index

        val chords = _song.value.sections[index].chords
        playbackJob = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                for (chord in chords) {
                    ensureActive()
                    audioEngine.playChord(chord, 2000L)
                }
            }
        }
    }

    fun regenerate() {
        stopPlayback()
        _song.value = IWantGenerator.generate()
    }

    fun stopPlayback() {
        playbackJob?.cancel()
        _playingSection.value = null
    }

    override fun onCleared() {
        playbackJob?.cancel()
        audioEngine.release()
        super.onCleared()
    }
}
