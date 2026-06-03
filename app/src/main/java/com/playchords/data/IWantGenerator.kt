package com.playchords.data

import com.playchords.model.ChordProgression
import com.playchords.model.IWantSection
import com.playchords.model.IWantSong
import com.playchords.model.ProgressionTag
import com.playchords.model.ProgressionTag.*

object IWantGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): IWantSong {
        val key = keys.random()

        val opening = progressionsByTagFull(IWANT_OPENING).random()
        val main = progressionsByTagFull(IWANT_MAIN).random()
        val desire = progressionsByTagExcludingFull(IWANT_DESIRE, main.romanNumerals).random()
        val climax = progressionsByTagExcludingFull(IWANT_CLIMAX, main.romanNumerals, desire.romanNumerals).random()

        return IWantSong(
            key = key,
            sections = listOf(
                buildSection("Opening", opening, key),
                buildSection("Main Body", main, key),
                buildSection("Desire Statement", desire, key),
                buildSection("Climax", climax, key)
            )
        )
    }

    private fun buildSection(label: String, progression: ChordProgression, key: String): IWantSection =
        IWantSection(
            label = label,
            progressionName = progression.name,
            romanNumerals = progression.romanNumerals,
            chords = ChordMapper.renderNumerals(key, progression.romanNumerals)
        )
}
