package com.playchords.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.playchords.data.ChordMapper
import com.playchords.data.majorKeys
import com.playchords.data.progressionsByTagFull
import com.playchords.model.ProgressionTag

class VChordEndingsViewModel : ViewModel() {

    private val instructions = listOf(
        "Change the end V to IV/5.",
        "End with I/V V.",
        "Add an extra ii, IV, or vi before V."
    )

    private val _progressionName = mutableStateOf("")
    val progressionName: State<String> = _progressionName

    private val _key = mutableStateOf("")
    val key: State<String> = _key

    private val _progressionChords = mutableStateOf("")
    val progressionChords: State<String> = _progressionChords

    private val _instruction = mutableStateOf("")
    val instruction: State<String> = _instruction

    init {
        regenerate()
    }

    fun regenerate() {
        val progression = progressionsByTagFull(ProgressionTag.ENDS_ON_V).random()
        val selectedKey = majorKeys.random()
        _progressionName.value = progression.name
        _key.value = selectedKey
        _progressionChords.value = ChordMapper.renderProgression(selectedKey, progression).joinToString(" - ")
        _instruction.value = instructions.random()
    }
}
