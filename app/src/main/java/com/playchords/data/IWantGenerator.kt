package com.playchords.data

import com.playchords.model.IWantSection
import com.playchords.model.IWantSong
import com.playchords.model.ProgressionTag.*

object IWantGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): IWantSong {
        val key = keys.random()
        return IWantSong(
            key = key,
            sections = listOf(
                buildSection("Opening", progressionsByTag(IWANT_OPENING), key),
                buildSection("Main Body", progressionsByTag(IWANT_MAIN), key),
                buildSection("Desire Statement", progressionsByTag(IWANT_DESIRE), key),
                buildSection("Climax", progressionsByTag(IWANT_CLIMAX), key)
            )
        )
    }

    private fun buildSection(label: String, pool: List<List<String>>, key: String): IWantSection {
        val numerals = pool.random()
        return IWantSection(
            label = label,
            romanNumerals = numerals,
            chords = ChordMapper.renderNumerals(key, numerals)
        )
    }
}
