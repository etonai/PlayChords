package com.playchords.viewmodel

import androidx.lifecycle.ViewModel
import com.playchords.data.ChordMapper
import com.playchords.data.allProgressions
import com.playchords.data.majorKeys
import com.playchords.data.tempoOptions
import com.playchords.model.ChordProgression
import kotlin.random.Random

class SelectionViewModel(private val random: Random = Random) : ViewModel() {

    var selectedProgression: ChordProgression? = null
        private set
    var selectedKey: String? = null
        private set
    var selectedTempo: Int? = null
        private set

    fun setProgression(progression: ChordProgression) {
        selectedProgression = progression
    }

    fun setKey(key: String) {
        selectedKey = key
    }

    fun setTempo(tempo: Int) {
        selectedTempo = tempo
    }

    fun randomize() {
        selectedProgression = allProgressions[random.nextInt(allProgressions.size)]
        selectedKey = majorKeys[random.nextInt(majorKeys.size)]
        selectedTempo = tempoOptions[random.nextInt(tempoOptions.size)]
    }

    fun resolvedChords(): List<String>? {
        val prog = selectedProgression ?: return null
        val key = selectedKey ?: return null
        return ChordMapper.renderProgression(key, prog)
    }
}
