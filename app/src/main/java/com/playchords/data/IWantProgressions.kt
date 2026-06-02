package com.playchords.data

object IWantProgressions {

    val opening: List<List<String>> = listOf(
        listOf("I", "vi"),
        listOf("I", "IV"),
        listOf("I", "vi", "IV"),
        listOf("vi", "IV"),
        listOf("I", "iii", "vi"),
        listOf("I", "vi", "IV", "I")
    )

    val mainBody: List<List<String>> = listOf(
        listOf("I", "vi", "ii", "V"),
        listOf("I", "vi", "IV", "V"),
        listOf("I", "IV", "ii", "V"),
        listOf("I", "iii", "vi", "ii", "V"),
        listOf("vi", "ii", "V", "I"),
        listOf("I", "IV", "V", "I")
    )

    val bigStatement: List<List<String>> = listOf(
        listOf("I", "V", "vi", "IV"),
        listOf("I", "vi", "IV", "V"),
        listOf("IV", "V", "I", "vi"),
        listOf("I", "IV", "I", "V"),
        listOf("vi", "IV", "I", "V"),
        listOf("I", "iii", "IV", "V")
    )

    val climax: List<List<String>> = listOf(
        listOf("IV", "V", "I"),
        listOf("ii", "V", "I"),
        listOf("IV", "I", "V", "I"),
        listOf("IV", "V", "vi", "V", "I"),
        listOf("ii", "IV", "V", "I"),
        listOf("IV", "V", "I", "vi", "IV", "V", "I")
    )
}
