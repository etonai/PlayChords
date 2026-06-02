package com.playchords.data

import com.playchords.model.ILoveSection
import com.playchords.model.ILoveSong
import com.playchords.model.ProgressionTag.*

object ILoveGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): ILoveSong {
        val key = keys.random()
        return ILoveSong(
            key = key,
            sections = listOf(
                buildSection("Opening", progressionsByTag(ILOVE_OPENING), key),
                buildSection("Main Love Theme", progressionsByTag(ILOVE_MAIN_LOVE_THEME), key),
                buildSection("Variant Love Theme", progressionsByTag(ILOVE_VARIANT_LOVE_THEME), key, isOptional = true),
                buildSection("Climax", progressionsByTag(ILOVE_CLIMAX), key)
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
