package com.playchords.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.playchords.audio.AudioEngine
import com.playchords.data.ChordMapper
import com.playchords.data.majorKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class IVVTrainingViewModel(application: Application) : AndroidViewModel(application) {

    private val audioEngine = AudioEngine(application)
    private var playJob: Job? = null

    private val _key = mutableStateOf("")
    val key: State<String> = _key

    private val _targetNumeral = mutableStateOf("")
    val targetNumeral: State<String> = _targetNumeral

    private val _targetChordName = mutableStateOf("")
    val targetChordName: State<String> = _targetChordName

    private val _answerVisible = mutableStateOf(false)
    val answerVisible: State<Boolean> = _answerVisible

    init {
        regenerate()
    }

    fun regenerate() {
        _answerVisible.value = false
        _key.value = majorKeys.random()
        _targetNumeral.value = listOf("IV", "V").random()
        _targetChordName.value = ChordMapper.renderNumerals(_key.value, listOf(_targetNumeral.value)).first()
        playChord(ChordMapper.renderNumerals(_key.value, listOf("I")).first())
    }

    fun showAnswer() {
        _answerVisible.value = true
        playChord(_targetChordName.value)
    }

    private fun playChord(chordName: String) {
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
