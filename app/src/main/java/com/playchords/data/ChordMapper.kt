package com.playchords.data

import com.playchords.model.ChordProgression

object ChordMapper {

    private val supportedMajorKeys = listOf(
        "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"
    )

    private val keyMap: Map<String, Map<String, String>> = mapOf(
        "C" to mapOf(
            "I" to "C",    "ii" to "Dm",    "iii" to "Em",    "IV" to "F",    "V" to "G",    "vi" to "Am",   "VI" to "A",
            "Imaj7" to "Cmaj7",   "I7" to "C7",     "V/vi" to "E",    "iv" to "Fm",   "♭VII" to "Bb",
            "i" to "Cm",   "♭VI" to "Ab",   "♭III" to "Eb",   "III" to "E",   "II" to "D",    "II7" to "D7",
            "V7" to "G7",  "Vsus4" to "Gsus4",  "V/7" to "G/B",   "I#dim" to "C#dim",
            "iiø" to "Dm7b5",  "ii/I" to "Dm/C",  "IV/I" to "F/C",  "V/I" to "G/C",  "I/3" to "C/E",
            "iii7" to "Em7",   "ii7" to "Dm7",    "ii7/V" to "Dm7/G",  "I/bVII" to "C/Bb",  "III7/vi" to "E7/A",  "ivadd6" to "Fmadd6"
        ),
        "Db" to mapOf(
            "I" to "Db",   "ii" to "Ebm",   "iii" to "Fm",    "IV" to "Gb",   "V" to "Ab",   "vi" to "Bbm",  "VI" to "Bb",
            "Imaj7" to "Dbmaj7",  "I7" to "Db7",    "V/vi" to "F",    "iv" to "Gbm",  "♭VII" to "B",
            "i" to "Dbm",  "♭VI" to "A",    "♭III" to "E",    "III" to "F",   "II" to "Eb",   "II7" to "Eb7",
            "V7" to "Ab7", "Vsus4" to "Absus4", "V/7" to "Ab/C",  "I#dim" to "Ddim",
            "iiø" to "Ebm7b5", "ii/I" to "Ebm/Db", "IV/I" to "Gb/Db", "V/I" to "Ab/Db", "I/3" to "Db/F",
            "iii7" to "Fm7",   "ii7" to "Ebm7",   "ii7/V" to "Ebm7/Ab",  "I/bVII" to "Db/B",  "III7/vi" to "F7/Bb",  "ivadd6" to "Gbmadd6"
        ),
        "D" to mapOf(
            "I" to "D",    "ii" to "Em",    "iii" to "F#m",   "IV" to "G",    "V" to "A",    "vi" to "Bm",   "VI" to "B",
            "Imaj7" to "Dmaj7",   "I7" to "D7",     "V/vi" to "F#",   "iv" to "Gm",   "♭VII" to "C",
            "i" to "Dm",   "♭VI" to "Bb",   "♭III" to "F",    "III" to "F#",  "II" to "E",    "II7" to "E7",
            "V7" to "A7",  "Vsus4" to "Asus4",  "V/7" to "A/C#",  "I#dim" to "D#dim",
            "iiø" to "Em7b5",  "ii/I" to "Em/D",  "IV/I" to "G/D",  "V/I" to "A/D",  "I/3" to "D/F#",
            "iii7" to "F#m7",  "ii7" to "Em7",    "ii7/V" to "Em7/A",  "I/bVII" to "D/C",  "III7/vi" to "F#7/B",  "ivadd6" to "Gmadd6"
        ),
        "Eb" to mapOf(
            "I" to "Eb",   "ii" to "Fm",    "iii" to "Gm",    "IV" to "Ab",   "V" to "Bb",   "vi" to "Cm",   "VI" to "C",
            "Imaj7" to "Ebmaj7",  "I7" to "Eb7",    "V/vi" to "G",    "iv" to "Abm",  "♭VII" to "Db",
            "i" to "Ebm",  "♭VI" to "B",    "♭III" to "Gb",   "III" to "G",   "II" to "F",    "II7" to "F7",
            "V7" to "Bb7", "Vsus4" to "Bbsus4", "V/7" to "Bb/D",  "I#dim" to "Edim",
            "iiø" to "Fm7b5",  "ii/I" to "Fm/Eb",  "IV/I" to "Ab/Eb", "V/I" to "Bb/Eb", "I/3" to "Eb/G",
            "iii7" to "Gm7",   "ii7" to "Fm7",    "ii7/V" to "Fm7/Bb",  "I/bVII" to "Eb/Db",  "III7/vi" to "G7/C",  "ivadd6" to "Abmadd6"
        ),
        "E" to mapOf(
            "I" to "E",    "ii" to "F#m",   "iii" to "G#m",   "IV" to "A",    "V" to "B",    "vi" to "C#m",  "VI" to "C#",
            "Imaj7" to "Emaj7",   "I7" to "E7",     "V/vi" to "G#",   "iv" to "Am",   "♭VII" to "D",
            "i" to "Em",   "♭VI" to "C",    "♭III" to "G",    "III" to "G#",  "II" to "F#",   "II7" to "F#7",
            "V7" to "B7",  "Vsus4" to "Bsus4",  "V/7" to "B/D#",  "I#dim" to "Fdim",
            "iiø" to "F#m7b5", "ii/I" to "F#m/E", "IV/I" to "A/E",  "V/I" to "B/E",  "I/3" to "E/G#",
            "iii7" to "G#m7",  "ii7" to "F#m7",   "ii7/V" to "F#m7/B",  "I/bVII" to "E/D",  "III7/vi" to "G#7/C#",  "ivadd6" to "Amadd6"
        ),
        "F" to mapOf(
            "I" to "F",    "ii" to "Gm",    "iii" to "Am",    "IV" to "Bb",   "V" to "C",    "vi" to "Dm",   "VI" to "D",
            "Imaj7" to "Fmaj7",   "I7" to "F7",     "V/vi" to "A",    "iv" to "Bbm",  "♭VII" to "Eb",
            "i" to "Fm",   "♭VI" to "Db",   "♭III" to "Ab",   "III" to "A",   "II" to "G",    "II7" to "G7",
            "V7" to "C7",  "Vsus4" to "Csus4",  "V/7" to "C/E",   "I#dim" to "F#dim",
            "iiø" to "Gm7b5",  "ii/I" to "Gm/F",  "IV/I" to "Bb/F", "V/I" to "C/F",  "I/3" to "F/A",
            "iii7" to "Am7",   "ii7" to "Gm7",    "ii7/V" to "Gm7/C",  "I/bVII" to "F/Eb",  "III7/vi" to "A7/D",  "ivadd6" to "Bbmadd6"
        ),
        "Gb" to mapOf(
            "I" to "Gb",   "ii" to "Abm",   "iii" to "Bbm",   "IV" to "B",    "V" to "Db",   "vi" to "Ebm",  "VI" to "Eb",
            "Imaj7" to "Gbmaj7",  "I7" to "Gb7",    "V/vi" to "Bb",   "iv" to "Bm",   "♭VII" to "E",
            "i" to "Gbm",  "♭VI" to "D",    "♭III" to "A",    "III" to "Bb",  "II" to "Ab",   "II7" to "Ab7",
            "V7" to "Db7", "Vsus4" to "Dbsus4", "V/7" to "Db/F",  "I#dim" to "Gdim",
            "iiø" to "Abm7b5", "ii/I" to "Abm/Gb", "IV/I" to "B/Gb", "V/I" to "Db/Gb", "I/3" to "Gb/Bb",
            "iii7" to "Bbm7",  "ii7" to "Abm7",   "ii7/V" to "Abm7/Db",  "I/bVII" to "Gb/E",  "III7/vi" to "Bb7/Eb",  "ivadd6" to "Bmadd6"
        ),
        "G" to mapOf(
            "I" to "G",    "ii" to "Am",    "iii" to "Bm",    "IV" to "C",    "V" to "D",    "vi" to "Em",   "VI" to "E",
            "Imaj7" to "Gmaj7",   "I7" to "G7",     "V/vi" to "B",    "iv" to "Cm",   "♭VII" to "F",
            "i" to "Gm",   "♭VI" to "Eb",   "♭III" to "Bb",   "III" to "B",   "II" to "A",    "II7" to "A7",
            "V7" to "D7",  "Vsus4" to "Dsus4",  "V/7" to "D/F#",  "I#dim" to "G#dim",
            "iiø" to "Am7b5",  "ii/I" to "Am/G",  "IV/I" to "C/G",  "V/I" to "D/G",  "I/3" to "G/B",
            "iii7" to "Bm7",   "ii7" to "Am7",    "ii7/V" to "Am7/D",  "I/bVII" to "G/F",  "III7/vi" to "B7/E",  "ivadd6" to "Cmadd6"
        ),
        "Ab" to mapOf(
            "I" to "Ab",   "ii" to "Bbm",   "iii" to "Cm",    "IV" to "Db",   "V" to "Eb",   "vi" to "Fm",   "VI" to "F",
            "Imaj7" to "Abmaj7",  "I7" to "Ab7",    "V/vi" to "C",    "iv" to "Dbm",  "♭VII" to "Gb",
            "i" to "Abm",  "♭VI" to "E",    "♭III" to "B",    "III" to "C",   "II" to "Bb",   "II7" to "Bb7",
            "V7" to "Eb7", "Vsus4" to "Ebsus4", "V/7" to "Eb/G",  "I#dim" to "Adim",
            "iiø" to "Bbm7b5", "ii/I" to "Bbm/Ab", "IV/I" to "Db/Ab", "V/I" to "Eb/Ab", "I/3" to "Ab/C",
            "iii7" to "Cm7",   "ii7" to "Bbm7",   "ii7/V" to "Bbm7/Eb",  "I/bVII" to "Ab/Gb",  "III7/vi" to "C7/F",  "ivadd6" to "Dbmadd6"
        ),
        "A" to mapOf(
            "I" to "A",    "ii" to "Bm",    "iii" to "C#m",   "IV" to "D",    "V" to "E",    "vi" to "F#m",  "VI" to "F#",
            "Imaj7" to "Amaj7",   "I7" to "A7",     "V/vi" to "C#",   "iv" to "Dm",   "♭VII" to "G",
            "i" to "Am",   "♭VI" to "F",    "♭III" to "C",    "III" to "C#",  "II" to "B",    "II7" to "B7",
            "V7" to "E7",  "Vsus4" to "Esus4",  "V/7" to "E/G#",  "I#dim" to "A#dim",
            "iiø" to "Bm7b5",  "ii/I" to "Bm/A",  "IV/I" to "D/A",  "V/I" to "E/A",  "I/3" to "A/C#",
            "iii7" to "C#m7",  "ii7" to "Bm7",    "ii7/V" to "Bm7/E",  "I/bVII" to "A/G",  "III7/vi" to "C#7/F#",  "ivadd6" to "Dmadd6"
        ),
        "Bb" to mapOf(
            "I" to "Bb",   "ii" to "Cm",    "iii" to "Dm",    "IV" to "Eb",   "V" to "F",    "vi" to "Gm",   "VI" to "G",
            "Imaj7" to "Bbmaj7",  "I7" to "Bb7",    "V/vi" to "D",    "iv" to "Ebm",  "♭VII" to "Ab",
            "i" to "Bbm",  "♭VI" to "Gb",   "♭III" to "Db",   "III" to "D",   "II" to "C",    "II7" to "C7",
            "V7" to "F7",  "Vsus4" to "Fsus4",  "V/7" to "F/A",   "I#dim" to "Bdim",
            "iiø" to "Cm7b5",  "ii/I" to "Cm/Bb", "IV/I" to "Eb/Bb", "V/I" to "F/Bb", "I/3" to "Bb/D",
            "iii7" to "Dm7",   "ii7" to "Cm7",    "ii7/V" to "Cm7/F",  "I/bVII" to "Bb/Ab",  "III7/vi" to "D7/G",  "ivadd6" to "Ebmadd6"
        ),
        "B" to mapOf(
            "I" to "B",    "ii" to "C#m",   "iii" to "D#m",   "IV" to "E",    "V" to "F#",   "vi" to "G#m",  "VI" to "G#",
            "Imaj7" to "Bmaj7",   "I7" to "B7",     "V/vi" to "D#",   "iv" to "Em",   "♭VII" to "A",
            "i" to "Bm",   "♭VI" to "G",    "♭III" to "D",    "III" to "D#",  "II" to "C#",   "II7" to "C#7",
            "V7" to "F#7", "Vsus4" to "F#sus4", "V/7" to "F#/A#", "I#dim" to "Cdim",
            "iiø" to "C#m7b5", "ii/I" to "C#m/B", "IV/I" to "E/B",  "V/I" to "F#/B", "I/3" to "B/D#",
            "iii7" to "D#m7",  "ii7" to "C#m7",   "ii7/V" to "C#m7/F#",  "I/bVII" to "B/A",  "III7/vi" to "D#7/G#",  "ivadd6" to "Emadd6"
        )
    )

    fun renderProgression(key: String, progression: ChordProgression): List<String> {
        val chords = keyMap[key] ?: return emptyList()
        return progression.romanNumerals.map { numeral -> chords[numeral] ?: numeral }
    }

    fun keyOneWholeStepHigher(key: String): String? {
        val index = supportedMajorKeys.indexOf(key)
        if (index == -1) return null
        return supportedMajorKeys[(index + 2) % supportedMajorKeys.size]
    }

    fun renderNumerals(key: String, numerals: List<String>): List<String> {
        val chords = keyMap[key] ?: return emptyList()
        return numerals.map { numeral -> chords[numeral] ?: numeral }
    }

    fun renderProgressionOneWholeStepHigher(
        key: String,
        progression: ChordProgression
    ): Pair<String, List<String>>? {
        val transposedKey = keyOneWholeStepHigher(key) ?: return null
        return transposedKey to renderProgression(transposedKey, progression)
    }
}
