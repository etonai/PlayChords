package com.playchords.data

import com.playchords.model.ComedySection
import com.playchords.model.ComedySong

object ComedyGenerator {

    private val keys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    fun generate(): ComedySong {
        val key = keys.random()
        val modulatedKey = ChordMapper.keyOneWholeStepHigher(key)!!

        val chorusNumerals = ComedyProgressions.chorus.random()
        val verseNumerals = ComedyProgressions.verse.random()
        val bridgeNumerals = ComedyProgressions.bridge.random()

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
                    label = "Verse",
                    romanNumerals = verseNumerals,
                    chords = ChordMapper.renderNumerals(key, verseNumerals)
                ),
                ComedySection(
                    label = "Bridge",
                    romanNumerals = bridgeNumerals,
                    chords = ChordMapper.renderNumerals(key, bridgeNumerals),
                    isModulated = false
                ),
                ComedySection(
                    label = "Modulated Chorus",
                    romanNumerals = chorusNumerals,
                    chords = ChordMapper.renderNumerals(modulatedKey, chorusNumerals),
                    isModulated = true,
                    isOptional = true
                )
            )
        )
    }
}
