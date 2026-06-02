package com.playchords.data

import com.playchords.model.IWantSection
import com.playchords.model.IWantSong

object IWantGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): IWantSong {
        val key = keys.random()
        return IWantSong(
            key = key,
            sections = listOf(
                buildSection("Opening", IWantProgressions.opening, key),
                buildSection("Main Body", IWantProgressions.mainBody, key),
                buildSection("Desire Statement", IWantProgressions.bigStatement, key),
                buildSection("Climax", IWantProgressions.climax, key)
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
