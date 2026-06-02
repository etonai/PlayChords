package com.playchords.data

import com.playchords.model.ComedySection
import com.playchords.model.ComedySong
import com.playchords.model.ProgressionTag.*

object ComedyGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): ComedySong {
        val key = keys.random()
        val modulatedKey = ChordMapper.keyOneWholeStepHigher(key)!!

        val chorusNumerals = progressionsByTag(COMEDY_CHORUS).random()
        val verseNumerals = progressionsByTag(COMEDY_VERSE).random()
        val bridgeNumerals = progressionsByTag(COMEDY_BRIDGE).random()
        val chorusResolutionNumerals = progressionsByTag(SECTION_RESOLUTION).random()
        val verseResolutionNumerals = progressionsByTag(SECTION_RESOLUTION).random()

        return ComedySong(
            key = key,
            modulatedKey = modulatedKey,
            sections = listOf(
                ComedySection(
                    label = "Chorus",
                    romanNumerals = chorusNumerals,
                    chords = ChordMapper.renderNumerals(key, chorusNumerals)
                ),
                ComedySection(
                    label = "Chorus Resolution",
                    romanNumerals = chorusResolutionNumerals,
                    chords = ChordMapper.renderNumerals(key, chorusResolutionNumerals),
                    isOptional = true
                ),
                ComedySection(
                    label = "Verse",
                    romanNumerals = verseNumerals,
                    chords = ChordMapper.renderNumerals(key, verseNumerals)
                ),
                ComedySection(
                    label = "Verse Resolution",
                    romanNumerals = verseResolutionNumerals,
                    chords = ChordMapper.renderNumerals(key, verseResolutionNumerals),
                    isOptional = true
                ),
                ComedySection(
                    label = "Bridge",
                    romanNumerals = bridgeNumerals,
                    chords = ChordMapper.renderNumerals(key, bridgeNumerals),
                    isOptional = true
                ),
                ComedySection(
                    label = "Modulated Chorus",
                    romanNumerals = chorusNumerals,
                    chords = ChordMapper.renderNumerals(modulatedKey, chorusNumerals),
                    isModulated = true,
                    isOptional = true
                ),
                ComedySection(
                    label = "Modulated Chorus Resolution",
                    romanNumerals = chorusResolutionNumerals,
                    chords = ChordMapper.renderNumerals(modulatedKey, chorusResolutionNumerals),
                    isModulated = true,
                    isOptional = true
                )
            )
        )
    }
}
