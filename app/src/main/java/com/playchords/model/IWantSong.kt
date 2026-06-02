package com.playchords.model

data class IWantSection(
    val label: String,
    val romanNumerals: List<String>,
    val chords: List<String>
)

data class IWantSong(
    val key: String,
    val sections: List<IWantSection>
)
