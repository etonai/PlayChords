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
    prog("Classic Cadence",          "Classic / Standard",     listOf("I", "IV", "V", "I"),               CADENTIAL, ENDS_ON_I, IWANT_MAIN, ILOVE_MAIN_LOVE_THEME, ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX, COMEDY_CHORUS),
    prog("Pop Axis",                 "Classic / Standard",     listOf("I", "V", "vi", "IV"),              LIFT, IWANT_DESIRE, COMEDY_CHORUS),
    prog("Minor Pop Loop",           "Classic / Standard",     listOf("vi", "IV", "I", "V"),              LIFT, ENDS_ON_V, IWANT_DESIRE, COMEDY_CHORUS, COMEDY_BRIDGE),
    prog("50s Progression",          "Classic / Standard",     listOf("I", "vi", "IV", "V"),              LIFT, ENDS_ON_V, IWANT_MAIN, IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE),
    prog("Plagal Loop",              "Classic / Standard",     listOf("IV", "I", "V", "I"),               CADENTIAL, LIFT, ENDS_ON_I, IWANT_CLIMAX, ILOVE_VARIANT_LOVE_THEME),
    prog("Descending Bass Line",     "Classic / Standard",     listOf("I", "V/7", "vi", "V"),             COLOR, ENDS_ON_V),
    prog("Axis Variant",             "Classic / Standard",     listOf("vi", "V", "IV", "I"),              CADENTIAL, LIFT, ENDS_ON_I),
    prog("Stepwise Lift",            "Classic / Standard",     listOf("I", "ii", "IV", "V"),              LIFT, ENDS_ON_V),
    prog("Ascending Walk",           "Classic / Standard",     listOf("I", "ii", "iii", "V"),             LIFT, ENDS_ON_V),
    prog("More Than a Feeling",      "Classic / Standard",     listOf("I", "IV", "vi", "V"),              LIFT, ENDS_ON_V, COMEDY_BRIDGE),
    prog("Coldplay Progression",     "Classic / Standard",     listOf("IV", "I", "V"),                    OPEN, LIFT, ENDS_ON_V),
    prog("Swiftie 5th",              "Classic / Standard",     listOf("IV", "I", "V", "vi"),              LIFT),
    prog("Swiftie 4th",              "Classic / Standard",     listOf("vi", "IV", "I", "V"),              LIFT, ENDS_ON_V),
    prog("Yellow",                   "Classic / Standard",     listOf("I", "V", "IV"),                    OPEN, LIFT),
    prog("Odo shinko",               "Classic / Standard",     listOf("IV", "V", "iii", "vi"),            LIFT),
    prog("Pachelbel Canon",          "Classic / Standard",     listOf("I", "V", "vi", "iii", "IV", "I", "IV", "V"),  LONG_CHORD, LIFT, ENDS_ON_V),

    // Musical Theatre / Jazz
    prog("Two Five One",             "Musical Theatre / Jazz", listOf("ii", "V", "I"),                    OPEN, CADENTIAL, ENDS_ON_I, IWANT_CLIMAX, ILOVE_CLIMAX),
    prog("Circle Turnaround",        "Musical Theatre / Jazz", listOf("I", "vi", "ii", "V"),              CADENTIAL, ENDS_ON_V, IWANT_MAIN),
    prog("Extended Turnaround",      "Musical Theatre / Jazz", listOf("iii", "vi", "ii", "V"),            PIVOT, ENDS_ON_V, COMEDY_BRIDGE),
    prog("Borrowed Minor Four",      "Musical Theatre / Jazz", listOf("I", "Imaj7", "IV", "iv"),          COLOR, PIVOT),
    prog("Dominant Lift Setup",      "Musical Theatre / Jazz", listOf("IV", "I", "II7", "V"),             LIFT, PIVOT, COLOR, ENDS_ON_V),
    prog("Backdoor Resolution",      "Musical Theatre / Jazz", listOf("ii", "♭VII", "I"),                 OPEN, CADENTIAL, COLOR, ENDS_ON_I),
    prog("Minor Two Five One",       "Musical Theatre / Jazz", listOf("iiø", "V7", "i"),                  OPEN, CADENTIAL, COLOR),
    prog("Chromatic Walk-Up",        "Musical Theatre / Jazz", listOf("I", "I#dim", "ii", "V"),           PIVOT, COLOR, ENDS_ON_V),
    prog("Easy",                     "Musical Theatre / Jazz", listOf("I", "iii", "ii", "V"),             LIFT, PIVOT, ENDS_ON_V),
    prog("Love is an Open Door",     "Musical Theatre / Jazz", listOf("I", "I/3", "IV", "V"),             ENDS_ON_V),
    prog("Mostly Me",                "Musical Theatre / Jazz", listOf("I", "IV", "ii", "V"),              LIFT, CADENTIAL, ENDS_ON_V, IWANT_MAIN, ILOVE_MAIN_LOVE_THEME),
    prog("Don't Cry Out Loud",       "Musical Theatre / Jazz", listOf("I", "iii7", "ii7", "ii7/V"),       COLOR, PIVOT, ENDS_ON_V),
    prog("Don't Cry Build",          "Musical Theatre / Jazz", listOf("vi", "iii", "vi", "II7", "V7"),    COLOR, PIVOT, ENDS_ON_V),

    // Expressive / Color
    prog("Secondary Dominant Lift",  "Expressive / Color",     listOf("I", "V/vi", "vi", "IV"),           LIFT, COLOR),
    prog("Minor To Resolution",      "Expressive / Color",     listOf("vi", "ii", "V", "I"),              CADENTIAL, LIFT, ENDS_ON_I, IWANT_MAIN, SECTION_RESOLUTION),
    prog("Flat Seven Color",         "Expressive / Color",     listOf("I", "♭VII", "IV", "I"),            COLOR, CADENTIAL, ENDS_ON_I),
    prog("Line Cliche Major",        "Expressive / Color",     listOf("I", "Imaj7", "I7", "IV"),          COLOR, PIVOT),
    prog("Something Plus",           "Expressive / Color",     listOf("I", "Imaj7", "I7", "IV", "iv", "I"),  LONG_CHORD, CADENTIAL, COLOR, ENDS_ON_I),
    prog("Mixolydian Variant",       "Expressive / Color",     listOf("I", "♭VII", "I", "IV"),            COLOR),
    prog("Chromatic Mediants",       "Expressive / Color",     listOf("I", "♭III", "IV", "I"),            COLOR, LIFT, ENDS_ON_I),
    prog("Deceptive Cycle",          "Expressive / Color",     listOf("V", "vi", "IV", "I"),              CADENTIAL, PIVOT, ENDS_ON_I),
    prog("Who Knew",                 "Expressive / Color",     listOf("I", "ii", "vi", "V"),              PIVOT, COLOR, ENDS_ON_V),
    prog("Thirds Descent",           "Expressive / Color",     listOf("I", "iii", "vi", "V"),             COLOR, PIVOT, ENDS_ON_V),
    prog("Four Three Drop",          "Expressive / Color",     listOf("I", "IV", "iii", "V"),             COLOR, PIVOT, ENDS_ON_V),
    prog("Life Would Suck",          "Expressive / Color",     listOf("I", "vi", "iii", "V"),             COLOR, ENDS_ON_V),
    prog("Creep",                    "Expressive / Color",     listOf("I", "III", "IV", "iv"),            COLOR),
    prog("You'll Be Back",           "Expressive / Color",     listOf("I", "I/bVII", "IV", "ii7"),        COLOR),
    prog("She Use To Be Mine Bridge","Expressive / Color",     listOf("I", "III7/vi", "vi", "IV"),        COLOR),
    prog("Sam's Progression",        "Expressive / Color",     listOf("I", "iii", "vi", "ivadd6"),        COLOR),


    // Cinematic / Modern
    prog("Lydian Lift",              "Cinematic / Modern",     listOf("I", "II", "IV", "I"),              LIFT, COLOR, ENDS_ON_I),
    prog("Swiftie 2nd",              "Cinematic / Modern",     listOf("I", "V", "ii", "IV"),              PIVOT),
    prog("Sal Tlay",                 "Cinematic / Modern",     listOf("I", "iii", "IV", "V"),             LIFT, COLOR, ENDS_ON_V, IWANT_DESIRE, ILOVE_MAIN_LOVE_THEME, COMEDY_BRIDGE),

    // Song / Section — I Want Opening
    prog("IWant Opening 3",          "Song / Section",         listOf("I", "vi", "IV"),                   OPEN, IWANT_OPENING),
    prog("IWant Opening 5",          "Song / Section",         listOf("I", "iii", "vi"),                  OPEN, IWANT_OPENING),

    // Song / Section — I Want Main
    prog("IWant Main 2",             "Song / Section",         listOf("I", "iii", "vi", "ii", "V"),       IWANT_MAIN, ENDS_ON_V),

    // Song / Section — I Want Desire
    prog("IWant Desire 1",           "Song / Section",         listOf("IV", "V", "I", "vi"),              IWANT_DESIRE, COMEDY_CHORUS),
    prog("IWant Desire 2",           "Song / Section",         listOf("I", "IV", "I", "V"),               IWANT_DESIRE, COMEDY_CHORUS, COMEDY_VERSE, ENDS_ON_V),

    // Song / Section — I Want Climax
    prog("IWant Climax 1",           "Song / Section",         listOf("IV", "V", "I"),                    OPEN, IWANT_CLIMAX, ILOVE_CLIMAX, ENDS_ON_I),
    prog("IWant Climax 2",           "Song / Section",         listOf("IV", "V", "vi", "V"),              IWANT_CLIMAX),
    prog("IWant Climax 3",           "Song / Section",         listOf("ii", "IV", "V"),                   OPEN, IWANT_CLIMAX, SECTION_RESOLUTION),

    // Song / Section — I Love Opening
    prog("ILove Opening 2",          "Song / Section",         listOf("I", "Imaj7", "IV"),                OPEN, ILOVE_OPENING),
    prog("ILove Opening 3",          "Song / Section",         listOf("vi", "I", "IV"),                   OPEN, ILOVE_OPENING),
    prog("ILove Opening 4",          "Song / Section",         listOf("I", "iii", "IV"),                  OPEN, ILOVE_OPENING),
    prog("ILove Opening 5",          "Song / Section",         listOf("I", "IV", "vi"),                   OPEN, ILOVE_OPENING),

    // Song / Section — I Love Main Love Theme
    prog("ILove Main 2",             "Song / Section",         listOf("I", "Imaj7", "IV", "V"),           ILOVE_MAIN_LOVE_THEME, ENDS_ON_V),
    prog("ILove Main 3",             "Song / Section",         listOf("I", "ii", "V"),                    OPEN, ILOVE_MAIN_LOVE_THEME),

    // Song / Section — I Love Variant Love Theme
    prog("ILove Variant 1",          "Song / Section",         listOf("I", "V", "IV"),                    OPEN, ILOVE_VARIANT_LOVE_THEME, ILOVE_CLIMAX),
    prog("ILove Variant 2",          "Song / Section",         listOf("IV", "V", "I", "IV"),              ILOVE_VARIANT_LOVE_THEME),
    prog("ILove Variant 3",          "Song / Section",         listOf("I", "V", "I", "IV"),               ILOVE_VARIANT_LOVE_THEME, COMEDY_VERSE),
    prog("ILove Variant 4",          "Song / Section",         listOf("I", "IV", "V", "vi"),              ILOVE_VARIANT_LOVE_THEME),

    // Song / Section — I Love Climax
    prog("ILove Climax 1",           "Song / Section",         listOf("IV", "I", "IV", "V", "I"),         ILOVE_CLIMAX, ENDS_ON_I),
    prog("ILove Climax 2",           "Song / Section",         listOf("I", "IV", "I", "V", "I"),          ILOVE_CLIMAX, ENDS_ON_I),
    prog("ILove Climax 3",           "Song / Section",         listOf("ii", "V", "I", "IV", "I"),         ILOVE_CLIMAX, ENDS_ON_I),

    // Song / Section — Comedy Bridge
    prog("Comedy Bridge 2",          "Song / Section",         listOf("vi", "ii", "IV", "V"),             COMEDY_BRIDGE, ENDS_ON_V),
    prog("Comedy Bridge 3",          "Song / Section",         listOf("I", "IV", "I", "ii", "V"),         COMEDY_BRIDGE, ENDS_ON_V),

    // Song / Section — Section Resolution
    prog("Resolution 3",             "Song / Section",         listOf("vi", "IV", "V"),                   OPEN, SECTION_RESOLUTION),
    prog("Resolution 6",             "Song / Section",         listOf("I", "vi", "V"),                    OPEN, SECTION_RESOLUTION),
    prog("Resolution 8",             "Song / Section",         listOf("iii", "vi", "V"),                  OPEN, SECTION_RESOLUTION),
)

fun progressionsByTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag in it.tags }
        .map { it.romanNumerals }

fun progressionsWithoutTag(tag: ProgressionTag): List<List<String>> =
    allProgressions
        .filter { tag !in it.tags }
        .map { it.romanNumerals }

fun progressionsExcluding(excluded: List<String>): List<List<String>> =
    allProgressions
        .filter { it.romanNumerals != excluded }
        .map { it.romanNumerals }

fun progressionsByTagExcluding(tag: ProgressionTag, vararg excluded: List<String>): List<List<String>> =
    allProgressions
        .filter { tag in it.tags && it.romanNumerals !in excluded }
        .map { it.romanNumerals }

fun progressionsByTagFull(tag: ProgressionTag): List<ChordProgression> =
    allProgressions
        .filter { tag in it.tags }

fun progressionsByTagExcludingFull(tag: ProgressionTag, vararg excluded: List<String>): List<ChordProgression> =
    allProgressions
        .filter { tag in it.tags && it.romanNumerals !in excluded }

fun progressionsWithoutTagFull(tag: ProgressionTag): List<ChordProgression> =
    allProgressions
        .filter { tag !in it.tags }

fun progressionsExcludingFull(excluded: List<String>): List<ChordProgression> =
    allProgressions
        .filter { it.romanNumerals != excluded }

fun progressionsExcludingManyFull(vararg excluded: List<String>): List<ChordProgression> =
    allProgressions
        .filter { it.romanNumerals !in excluded }

val majorKeys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

val tempoOptions = listOf(50, 60, 70, 80, 90, 100, 110, 120)
