package com.playchords.data

object PlayableChords {

    private val roots = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    data class ChordGroup(val root: String, val chords: List<String>)

    val groups: List<ChordGroup> = roots.map { root ->
        ChordGroup(root, listOf(root, "${root}m", "${root}dim", "${root}7", "${root}maj7"))
    }

    val allChords: List<String> = groups.flatMap { it.chords }
}
