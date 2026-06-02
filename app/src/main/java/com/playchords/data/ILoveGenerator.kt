package com.playchords.data

import com.playchords.model.ILoveSection
import com.playchords.model.ILoveSong

object ILoveGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): ILoveSong {
        val key = keys.random()
        return ILoveSong(
            key = key,
            sections = listOf(
                buildSection("Opening", ILoveProgressions.opening, key),
                buildSection("Story", ILoveProgressions.story, key),
                buildSection("Declaration", ILoveProgressions.declaration, key),
                buildSection("Finale", ILoveProgressions.finale, key)
            )
        )
    }

    private fun buildSection(label: String, pool: List<List<String>>, key: String): ILoveSection {
        val numerals = pool.random()
        return ILoveSection(
            label = label,
            romanNumerals = numerals,
            chords = ChordMapper.renderNumerals(key, numerals)
        )
    }
}
