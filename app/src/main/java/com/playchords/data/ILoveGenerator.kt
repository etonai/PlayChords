package com.playchords.data

import com.playchords.model.ChordProgression
import com.playchords.model.ILoveSection
import com.playchords.model.ILoveSong
import com.playchords.model.ProgressionTag
import com.playchords.model.ProgressionTag.*

object ILoveGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): ILoveSong {
        val key = keys.random()

        val opening = progressionsByTagFull(ILOVE_OPENING).random()
        val main = progressionsByTagExcludingFull(ILOVE_MAIN_LOVE_THEME, opening.romanNumerals).random()
        val variant = progressionsByTagExcludingFull(ILOVE_VARIANT_LOVE_THEME, opening.romanNumerals, main.romanNumerals).random()
        val climax = progressionsByTagExcludingFull(ILOVE_CLIMAX, opening.romanNumerals, main.romanNumerals, variant.romanNumerals).random()

        val rhymeWord = RhymeWords.rhymeWords.random()

        return ILoveSong(
            key = key,
            sections = listOf(
                buildSection("Opening", opening, key),
                buildSection("Main Love Theme", main, key),
                buildSection("Variant Love Theme", variant, key, isOptional = true),
                buildSection("Climax", climax, key)
            ),
            rhymeWord = rhymeWord
        )
    }

    private fun buildSection(label: String, progression: ChordProgression, key: String, isOptional: Boolean = false): ILoveSection =
        ILoveSection(
            label = label,
            progressionName = progression.name,
            romanNumerals = progression.romanNumerals,
            chords = ChordMapper.renderNumerals(key, progression.romanNumerals),
            isOptional = isOptional
        )
}
