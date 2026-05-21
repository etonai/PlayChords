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
    prog("Classic Cadence",          "Classic / Standard",     listOf("I", "IV", "V", "I"),              CADENTIAL),
    prog("Pop Axis",                 "Classic / Standard",     listOf("I", "V", "vi", "IV"),              LIFT, LOOP),
    prog("Minor Pop Loop",           "Classic / Standard",     listOf("vi", "IV", "I", "V"),              OPEN, LIFT, LOOP),
    prog("50s Progression",          "Classic / Standard",     listOf("I", "vi", "IV", "V"),              LIFT, LOOP),
    prog("Plagal Loop",              "Classic / Standard",     listOf("IV", "I", "V", "I"),               CADENTIAL, LIFT),
    prog("Descending Bass Line",     "Classic / Standard",     listOf("I", "V/7", "vi", "V"),             OPEN, COLOR),
    prog("Axis Variant",             "Classic / Standard",     listOf("vi", "V", "IV", "I"),              CADENTIAL, LIFT),

    prog("Two Five One",             "Musical Theatre / Jazz", listOf("ii", "V", "I"),                    CADENTIAL),
    prog("Circle Turnaround",        "Musical Theatre / Jazz", listOf("I", "vi", "ii", "V"),              OPEN, CADENTIAL),
    prog("Extended Turnaround",      "Musical Theatre / Jazz", listOf("iii", "vi", "ii", "V"),            OPEN, PIVOT),
    prog("Borrowed Minor Four",      "Musical Theatre / Jazz", listOf("I", "Imaj7", "IV", "iv"),          COLOR, PIVOT),
    prog("Dominant Lift Setup",      "Musical Theatre / Jazz", listOf("IV", "I", "II7", "V"),             OPEN, LIFT, PIVOT, COLOR),
    prog("Backdoor Resolution",      "Musical Theatre / Jazz", listOf("ii", "♭VII", "I"),                 CADENTIAL, COLOR),
    prog("Rhythm Changes",           "Musical Theatre / Jazz", listOf("I", "vi", "ii", "V"),              OPEN, CADENTIAL),
    prog("Minor Two Five One",       "Musical Theatre / Jazz", listOf("iiø", "V7", "i"),                  CADENTIAL, COLOR),
    prog("Chromatic Walk-Up",        "Musical Theatre / Jazz", listOf("I", "I#dim", "ii", "V"),           PIVOT, COLOR),
    prog("Major Six Turnaround",     "Musical Theatre / Jazz", listOf("I", "VI", "ii", "I"),              OPEN, LOOP, COLOR, PIVOT),

    prog("Secondary Dominant Lift",  "Expressive / Color",     listOf("I", "V/vi", "vi", "IV"),           LIFT, COLOR),
    prog("Major To Minor Four",      "Expressive / Color",     listOf("I", "IV", "iv", "I"),              CADENTIAL, COLOR),
    prog("Minor To Resolution",      "Expressive / Color",     listOf("vi", "ii", "V", "I"),              CADENTIAL, LIFT),
    prog("Flat Seven Color",         "Expressive / Color",     listOf("I", "♭VII", "IV", "I"),            COLOR, CADENTIAL),
    prog("Line Cliche Major",        "Expressive / Color",     listOf("I", "Imaj7", "I7", "IV"),          OPEN, COLOR, PIVOT),
    prog("Mixolydian Variant",       "Expressive / Color",     listOf("I", "♭VII", "I", "IV"),            COLOR, LOOP),
    prog("Chromatic Mediants",       "Expressive / Color",     listOf("I", "♭III", "IV", "I"),            COLOR, LIFT),
    prog("Deceptive Cycle",          "Expressive / Color",     listOf("V", "vi", "IV", "I"),              CADENTIAL, PIVOT),
)

val majorKeys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

val tempoOptions = listOf(50, 60, 70, 80, 90, 100, 110, 120)
