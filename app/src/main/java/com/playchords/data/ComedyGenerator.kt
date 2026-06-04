package com.playchords.data

import com.playchords.model.ChordProgression
import com.playchords.model.ComedySection
import com.playchords.model.ComedySong
import com.playchords.model.ProgressionTag.*

object ComedyGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): ComedySong {
        val key = keys.random()
        val modulatedKey = ChordMapper.keyOneWholeStepHigher(key)!!

        val chorus = progressionsWithoutTagFull(ENDS_ON_I).random()
        val verse = progressionsExcludingFull(chorus.romanNumerals).random()
        val bridge = progressionsByTagExcludingFull(ENDS_ON_V, chorus.romanNumerals, verse.romanNumerals).random()
        val chorusResolution = progressionsByTagFull(ENDS_ON_I).random()
        val verseResolution = progressionsByTagFull(ENDS_ON_I).random()

        val preModulation = progressionsExcludingManyFull(
            chorus.romanNumerals, verse.romanNumerals, bridge.romanNumerals,
            chorusResolution.romanNumerals, verseResolution.romanNumerals
        ).random()
        val rhymeWord = RhymeWords.rhymeWords.random()

        return ComedySong(
            key = key,
            modulatedKey = modulatedKey,
            sections = listOf(
                buildSection("Chorus", chorus, key),
                buildSection("Chorus Resolution", chorusResolution, key, isOptional = true),
                buildSection("Verse", verse, key),
                buildSection("Verse Resolution", verseResolution, key, isOptional = true),
                buildSection("Bridge", bridge, key, isOptional = true),
                buildSection("Pre-Modulation", preModulation, key, isOptional = true),
                buildSection("Modulated Chorus", chorus, modulatedKey, isModulated = true, isOptional = true),
                buildSection("Chorus Resolution", chorusResolution, modulatedKey, isModulated = true, isOptional = true)
            ),
            rhymeWord = rhymeWord
        )
    }

    private fun buildSection(
        label: String,
        progression: ChordProgression,
        key: String,
        isModulated: Boolean = false,
        isOptional: Boolean = false
    ): ComedySection =
        ComedySection(
            label = label,
            progressionName = progression.name,
            romanNumerals = progression.romanNumerals,
            chords = ChordMapper.renderNumerals(key, progression.romanNumerals),
            isModulated = isModulated,
            isOptional = isOptional
        )
}
