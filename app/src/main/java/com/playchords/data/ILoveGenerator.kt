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
                buildSection("Main Love Theme", ILoveProgressions.story, key),
                buildSection("Variant Love Theme", ILoveProgressions.declaration, key, isOptional = true),
                buildSection("Climax", ILoveProgressions.finale, key)
            )
        )
    }

    private fun buildSection(label: String, pool: List<List<String>>, key: String, isOptional: Boolean = false): ILoveSection {
        val numerals = pool.random()
        return ILoveSection(
            label = label,
            romanNumerals = numerals,
            chords = ChordMapper.renderNumerals(key, numerals),
            isOptional = isOptional
        )
    }
}
