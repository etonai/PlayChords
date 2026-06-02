package com.playchords.model

data class ComedySection(
    val label: String,
    val romanNumerals: List<String>,
    val chords: List<String>,
    val isModulated: Boolean = false,
    val isOptional: Boolean = false
)

data class ComedySong(
    val key: String,
    val modulatedKey: String,
    val sections: List<ComedySection>
)
