package com.playchords.model

data class ChordProgression(
    val name: String,
    val category: String,
    val romanNumerals: List<String>,
    val tags: List<ProgressionTag>
)
