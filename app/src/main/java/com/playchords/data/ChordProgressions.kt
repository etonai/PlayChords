package com.playchords.data

import com.playchords.model.ChordProgression
import com.playchords.model.ProgressionTag
import com.playchords.model.ProgressionTag.*

private fun prog(
    name: String,
    category: String,
    numerals: List<String>,
    vararg tags: ProgressionTag
) = ChordProgression(name, category, numerals, tags.toList())

val allProgressions: List<ChordProgression> = listOf(

    // Classic / Standard
    prog("Classic Cadence",          "Classic / Standard",     listOf("I", "IV", "V", "I"),              CADENTIAL, ENDS_ON_I, IWANT_MAIN, ILOVE_MAIN_LOVE_THEME, ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX, COMEDY_CHORUS),
    prog("Pop Axis",                 "Classic / Standard",     listOf("I", "V", "vi", "IV"),              LIFT, IWANT_DESIRE, COMEDY_CHORUS),
    prog("Minor Pop Loop",           "Classic / Standard",     listOf("vi", "IV", "I", "V"),              OPEN, LIFT, ENDS_ON_V, IWANT_DESIRE, COMEDY_CHORUS, COMEDY_BRIDGE),
    prog("50s Progression",          "Classic / Standard",     listOf("I", "vi", "IV", "V"),              LIFT, ENDS_ON_V, IWANT_MAIN, IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE),
    prog("Plagal Loop",              "Classic / Standard",     listOf("IV", "I", "V", "I"),               CADENTIAL, LIFT, ENDS_ON_I, IWANT_CLIMAX, ILOVE_VARIANT_LOVE_THEME),
    prog("Descending Bass Line",     "Classic / Standard",     listOf("I", "V/7", "vi", "V"),             OPEN, COLOR, ENDS_ON_V),
    prog("Axis Variant",             "Classic / Standard",     listOf("vi", "V", "IV", "I"),              CADENTIAL, LIFT, ENDS_ON_I),
    prog("Stepwise Lift",            "Classic / Standard",     listOf("I", "ii", "IV", "V"),              LIFT, OPEN, ENDS_ON_V),
    prog("Ascending Walk",           "Classic / Standard",     listOf("I", "ii", "iii", "V"),             LIFT, OPEN, ENDS_ON_V),
    prog("More Than a Feeling",      "Classic / Standard",     listOf("I", "IV", "vi", "V"),              LIFT, OPEN, ENDS_ON_V, COMEDY_BRIDGE),

    // Musical Theatre / Jazz
    prog("Two Five One",             "Musical Theatre / Jazz", listOf("ii", "V", "I"),                    CADENTIAL, ENDS_ON_I, IWANT_CLIMAX, ILOVE_CLIMAX),
    prog("Circle Turnaround",        "Musical Theatre / Jazz", listOf("I", "vi", "ii", "V"),              OPEN, CADENTIAL, ENDS_ON_V, IWANT_MAIN),
    prog("Extended Turnaround",      "Musical Theatre / Jazz", listOf("iii", "vi", "ii", "V"),            OPEN, PIVOT, ENDS_ON_V, COMEDY_BRIDGE),
    prog("Borrowed Minor Four",      "Musical Theatre / Jazz", listOf("I", "Imaj7", "IV", "iv"),          COLOR, PIVOT),
    prog("Dominant Lift Setup",      "Musical Theatre / Jazz", listOf("IV", "I", "II7", "V"),             OPEN, LIFT, PIVOT, COLOR, ENDS_ON_V),
    prog("Backdoor Resolution",      "Musical Theatre / Jazz", listOf("ii", "♭VII", "I"),                 CADENTIAL, COLOR, ENDS_ON_I),
    prog("Minor Two Five One",       "Musical Theatre / Jazz", listOf("iiø", "V7", "i"),                  CADENTIAL, COLOR),
    prog("Chromatic Walk-Up",        "Musical Theatre / Jazz", listOf("I", "I#dim", "ii", "V"),           PIVOT, COLOR, ENDS_ON_V),
    prog("Easy",                     "Musical Theatre / Jazz", listOf("I", "iii", "ii", "V"),             OPEN, LIFT, PIVOT, ENDS_ON_V),
    prog("Love is an Open Door",     "Musical Theatre / Jazz", listOf("I", "I/3", "IV", "V"),             OPEN, ENDS_ON_V),
    prog("Mostly Me",                "Musical Theatre / Jazz", listOf("I", "IV", "ii", "V"),              LIFT, CADENTIAL, ENDS_ON_V, IWANT_MAIN, ILOVE_MAIN_LOVE_THEME),
    prog("Don't Cry Out Loud",       "Musical Theatre / Jazz", listOf("I", "iii7", "ii7", "ii7/V"),        OPEN, COLOR, PIVOT, ENDS_ON_V),
    prog("Don't Cry Build",          "Musical Theatre / Jazz", listOf("vi", "iii", "vi", "II7", "V7"),     OPEN, COLOR, PIVOT, ENDS_ON_V),

    // Expressive / Color
    prog("Secondary Dominant Lift",  "Expressive / Color",     listOf("I", "V/vi", "vi", "IV"),           LIFT, COLOR),
    prog("Minor To Resolution",      "Expressive / Color",     listOf("vi", "ii", "V", "I"),              CADENTIAL, LIFT, ENDS_ON_I, IWANT_MAIN, SECTION_RESOLUTION),
    prog("Flat Seven Color",         "Expressive / Color",     listOf("I", "♭VII", "IV", "I"),            COLOR, CADENTIAL, ENDS_ON_I),
    prog("Line Cliche Major",        "Expressive / Color",     listOf("I", "Imaj7", "I7", "IV"),          OPEN, COLOR, PIVOT),
    prog("Mixolydian Variant",       "Expressive / Color",     listOf("I", "♭VII", "I", "IV"),            COLOR),
    prog("Chromatic Mediants",       "Expressive / Color",     listOf("I", "♭III", "IV", "I"),            COLOR, LIFT, ENDS_ON_I),
    prog("Deceptive Cycle",          "Expressive / Color",     listOf("V", "vi", "IV", "I"),              CADENTIAL, PIVOT, ENDS_ON_I),
    prog("Who Knew",                 "Expressive / Color",     listOf("I", "ii", "vi", "V"),              OPEN, PIVOT, COLOR, ENDS_ON_V),
    prog("Thirds Descent",           "Expressive / Color",     listOf("I", "iii", "vi", "V"),             OPEN, COLOR, PIVOT, ENDS_ON_V),
    prog("Four Three Drop",          "Expressive / Color",     listOf("I", "IV", "iii", "V"),             COLOR, PIVOT, ENDS_ON_V),
    prog("Life Would Suck",          "Expressive / Color",     listOf("I", "vi", "iii", "V"),             OPEN, COLOR, ENDS_ON_V),


    // Cinematic / Modern
    prog("Lydian Lift",              "Cinematic / Modern",     listOf("I", "II", "IV", "I"),              LIFT, COLOR, ENDS_ON_I),
    prog("Ambiguous Loop",           "Cinematic / Modern",     listOf("I", "V", "ii", "IV"),              OPEN, PIVOT),
    prog("Sal Tlay",                 "Cinematic / Modern",     listOf("I", "iii", "IV", "V"),             LIFT, OPEN, COLOR, ENDS_ON_V, IWANT_DESIRE, ILOVE_MAIN_LOVE_THEME, COMEDY_BRIDGE),

    // Song / Section — I Want Opening
    prog("IWant Opening 3",          "Song / Section",         listOf("I", "vi", "IV"),                   IWANT_OPENING),
    prog("IWant Opening 5",          "Song / Section",         listOf("I", "iii", "vi"),                  IWANT_OPENING),
    prog("IWant Opening 6",          "Song / Section",         listOf("I", "vi", "IV", "I"),              IWANT_OPENING, ENDS_ON_I),

    // Song / Section — I Want Main
    prog("IWant Main 2",             "Song / Section",         listOf("I", "iii", "vi", "ii", "V"),       IWANT_MAIN, ENDS_ON_V),

    // Song / Section — I Want Desire
    prog("IWant Desire 1",           "Song / Section",         listOf("IV", "V", "I", "vi"),              IWANT_DESIRE, COMEDY_CHORUS),
    prog("IWant Desire 2",           "Song / Section",         listOf("I", "IV", "I", "V"),               IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE, ENDS_ON_V),

    // Song / Section — I Want Climax
    prog("IWant Climax 1",           "Song / Section",         listOf("IV", "V", "I"),                    IWANT_CLIMAX, ILOVE_CLIMAX, ENDS_ON_I),
    prog("IWant Climax 2",           "Song / Section",         listOf("IV", "V", "vi", "V", "I"),         IWANT_CLIMAX, ENDS_ON_I),
    prog("IWant Climax 3",           "Song / Section",         listOf("ii", "IV", "V", "I"),              IWANT_CLIMAX, SECTION_RESOLUTION, ENDS_ON_I),
    prog("IWant Climax 4",           "Song / Section",         listOf("IV", "V", "I", "vi", "IV", "V", "I"), IWANT_CLIMAX, LONG_CHORD, ENDS_ON_I),

    // Song / Section — I Love Opening
    prog("ILove Opening 1",          "Song / Section",         listOf("I", "IV", "I"),                    ILOVE_OPENING, ENDS_ON_I),
    prog("ILove Opening 2",          "Song / Section",         listOf("I", "Imaj7", "IV"),                ILOVE_OPENING),
    prog("ILove Opening 3",          "Song / Section",         listOf("vi", "I", "IV"),                   ILOVE_OPENING),
    prog("ILove Opening 4",          "Song / Section",         listOf("I", "iii", "IV"),                  ILOVE_OPENING),
    prog("ILove Opening 5",          "Song / Section",         listOf("I", "IV", "vi", "I"),              ILOVE_OPENING, ENDS_ON_I),

    // Song / Section — I Love Main Love Theme
    prog("ILove Main 1",             "Song / Section",         listOf("IV", "I", "ii", "V"),              ILOVE_MAIN_LOVE_THEME, ENDS_ON_V),
    prog("ILove Main 2",             "Song / Section",         listOf("I", "Imaj7", "IV", "V"),           ILOVE_MAIN_LOVE_THEME, ENDS_ON_V),
    prog("ILove Main 3",             "Song / Section",         listOf("I", "ii", "V", "I"),               ILOVE_MAIN_LOVE_THEME, ENDS_ON_I),

    // Song / Section — I Love Variant Love Theme
    prog("ILove Variant 1",          "Song / Section",         listOf("I", "V", "IV", "I"),               ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX, ENDS_ON_I),
    prog("ILove Variant 2",          "Song / Section",         listOf("IV", "V", "I", "IV"),              ILOVE_VARIANT_LOVE_THEME),
    prog("ILove Variant 3",          "Song / Section",         listOf("I", "V", "I", "IV"),               ILOVE_VARIANT_LOVE_THEME, COMEDY_VERSE),
    prog("ILove Variant 4",          "Song / Section",         listOf("I", "IV", "V", "vi"),              ILOVE_VARIANT_LOVE_THEME),

    // Song / Section — I Love Climax
    prog("ILove Climax 1",           "Song / Section",         listOf("IV", "I", "IV", "V", "I"),         ILOVE_CLIMAX, ENDS_ON_I),
    prog("ILove Climax 2",           "Song / Section",         listOf("I", "IV", "I", "V", "I"),          ILOVE_CLIMAX, ENDS_ON_I),
    prog("ILove Climax 3",           "Song / Section",         listOf("ii", "V", "I", "IV", "I"),         ILOVE_CLIMAX, ENDS_ON_I),

    // Song / Section — Comedy Verse
    prog("Comedy Verse 1",           "Song / Section",         listOf("I", "IV", "V"),                    COMEDY_VERSE, ENDS_ON_V),

    // Song / Section — Comedy Bridge
    prog("Comedy Bridge 2",          "Song / Section",         listOf("vi", "ii", "IV", "V"),             COMEDY_BRIDGE, ENDS_ON_V),
    prog("Comedy Bridge 3",          "Song / Section",         listOf("I", "IV", "I", "ii", "V"),         COMEDY_BRIDGE, ENDS_ON_V),

    // Song / Section — Section Resolution
    prog("Resolution 1",             "Song / Section",         listOf("ii", "V", "I", "I"),               SECTION_RESOLUTION, ENDS_ON_I),
    prog("Resolution 2",             "Song / Section",         listOf("IV", "V", "I", "I"),               SECTION_RESOLUTION, ENDS_ON_I),
    prog("Resolution 3",             "Song / Section",         listOf("vi", "IV", "V", "I"),              SECTION_RESOLUTION, ENDS_ON_I),
    prog("Resolution 5",             "Song / Section",         listOf("V", "V", "I", "I"),                SECTION_RESOLUTION, ENDS_ON_I),
    prog("Resolution 6",             "Song / Section",         listOf("I", "vi", "V", "I"),               SECTION_RESOLUTION, ENDS_ON_I),
    prog("Resolution 8",             "Song / Section",         listOf("iii", "vi", "V", "I"),             SECTION_RESOLUTION, ENDS_ON_I),
)

fun progressionsByTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag in it.tags && LONG_CHORD !in it.tags }
        .map { it.romanNumerals }

fun progressionsWithoutTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag !in it.tags && LONG_CHORD !in it.tags }
        .map { it.romanNumerals }

fun progressionsExcluding(excluded: List<String>): List<List<String>> =
    allProgressions
        .filter { LONG_CHORD !in it.tags && it.romanNumerals != excluded }
        .map { it.romanNumerals }

fun progressionsByTagExcluding(tag: ProgressionTag, vararg excluded: List<String>): List<List<String>> =
    allProgressions
        .filter { tag in it.tags && LONG_CHORD !in it.tags && it.romanNumerals !in excluded }
        .map { it.romanNumerals }

fun progressionsByTagFull(tag: ProgressionTag): List<ChordProgression> =
    allProgressions
        .filter { tag in it.tags && LONG_CHORD !in it.tags }

fun progressionsByTagExcludingFull(tag: ProgressionTag, vararg excluded: List<String>): List<ChordProgression> =
    allProgressions
        .filter { tag in it.tags && LONG_CHORD !in it.tags && it.romanNumerals !in excluded }

fun progressionsWithoutTagFull(tag: ProgressionTag): List<ChordProgression> =
    allProgressions
        .filter { tag !in it.tags && LONG_CHORD !in it.tags }

fun progressionsExcludingFull(excluded: List<String>): List<ChordProgression> =
    allProgressions
        .filter { LONG_CHORD !in it.tags && it.romanNumerals != excluded }

fun progressionsExcludingManyFull(vararg excluded: List<String>): List<ChordProgression> =
    allProgressions
        .filter { LONG_CHORD !in it.tags && it.romanNumerals !in excluded }

val majorKeys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

val tempoOptions = listOf(50, 60, 70, 80, 90, 100, 110, 120)
