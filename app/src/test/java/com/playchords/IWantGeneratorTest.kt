package com.playchords

import com.playchords.data.IWantGenerator
import org.junit.Assert.*
import org.junit.Test

class IWantGeneratorTest {

    private val validKeys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    @Test
    fun `generated song has exactly 4 sections`() {
        assertEquals(4, IWantGenerator.generate().sections.size)
    }

    @Test
    fun `section labels are correct and in order`() {
        val sections = IWantGenerator.generate().sections
        assertEquals("Opening", sections[0].label)
        assertEquals("Main Body", sections[1].label)
        assertEquals("Desire Statement", sections[2].label)
        assertEquals("Climax", sections[3].label)
    }

    @Test
    fun `each section has non-empty chord and numeral lists`() {
        IWantGenerator.generate().sections.forEach { section ->
            assertTrue("${section.label} chords empty", section.chords.isNotEmpty())
            assertTrue("${section.label} numerals empty", section.romanNumerals.isNotEmpty())
        }
    }

    @Test
    fun `chord count matches roman numeral count per section`() {
        IWantGenerator.generate().sections.forEach { section ->
            assertEquals(
                "${section.label}: chord/numeral count mismatch",
                section.romanNumerals.size,
                section.chords.size
            )
        }
    }

    @Test
    fun `generated key is a supported major key`() {
        assertTrue(IWantGenerator.generate().key in validKeys)
    }

    @Test
    fun `generating 20 songs produces at least 2 distinct keys`() {
        val keys = (1..20).map { IWantGenerator.generate().key }.toSet()
        assertTrue("Expected key variety across 20 songs, got: $keys", keys.size >= 2)
    }

    @Test
    fun `generating 20 songs varies opening progressions`() {
        val openings = (1..20).map { IWantGenerator.generate().sections[0].romanNumerals }.toSet()
        assertTrue("Expected progression variety across 20 songs", openings.size >= 2)
    }
}
