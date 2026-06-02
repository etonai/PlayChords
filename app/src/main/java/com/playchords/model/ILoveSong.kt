package com.playchords.model

data class ILoveSection(
    val label: String,
    val romanNumerals: List<String>,
    val chords: List<String>
)

data class ILoveSong(
    val key: String,
    val sections: List<ILoveSection>
)
