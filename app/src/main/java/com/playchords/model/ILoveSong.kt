package com.playchords.model

data class ILoveSection(
    val label: String,
    val progressionName: String,
    val romanNumerals: List<String>,
    val chords: List<String>,
    val isOptional: Boolean = false
)

data class ILoveSong(
    val key: String,
    val sections: List<ILoveSection>
)
