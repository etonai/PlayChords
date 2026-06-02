package com.playchords.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.playchords.audio.AudioEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChordPlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val audioEngine = AudioEngine(application)
    private var playJob: Job? = null

    fun playChord(chordName: String) {
        playJob?.cancel()
        playJob = viewModelScope.launch(Dispatchers.IO) {
            audioEngine.playChord(chordName, 2000L)
        }
    }

    override fun onCleared() {
        playJob?.cancel()
        audioEngine.release()
        super.onCleared()
    }
}
