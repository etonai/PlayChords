package com.playchords.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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

    private val _progressionNumerals = mutableStateOf("")
    val progressionNumerals: State<String> = _progressionNumerals

    private val _instruction = mutableStateOf("")
    val instruction: State<String> = _instruction

    init {
        regenerate()
    }

    fun regenerate() {
        val progression = progressionsByTagFull(ProgressionTag.ENDS_ON_V).random()
        _progressionName.value = progression.name
        _progressionNumerals.value = progression.romanNumerals.joinToString(" - ")
        _instruction.value = instructions.random()
    }
}
