package com.playchords.data

object ComedyProgressions {

    val chorus: List<List<String>> = listOf(
        listOf("I", "V", "vi", "IV"),
        listOf("I", "vi", "IV", "V"),
        listOf("I", "IV", "V", "I"),
        listOf("IV", "V", "I", "vi"),
        listOf("I", "IV", "I", "V"),
        listOf("vi", "IV", "I", "V")
    )

    val verse: List<List<String>> = listOf(
        listOf("I", "IV", "V"),
        listOf("I", "IV"),
        listOf("I", "V"),
        listOf("I", "vi", "IV", "V"),
        listOf("I", "IV", "I", "V"),
        listOf("I", "V", "I", "IV")
    )

    val bridge: List<List<String>> = listOf(
        listOf("vi", "IV", "I", "V"),
        listOf("I", "IV", "vi", "V"),
        listOf("I", "iii", "IV", "V"),
        listOf("iii", "vi", "ii", "V"),
        listOf("vi", "ii", "IV", "V"),
        listOf("I", "IV", "I", "ii", "V")
    )
}
