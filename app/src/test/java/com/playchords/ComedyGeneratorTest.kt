package com.playchords

import com.playchords.data.ComedyGenerator
import org.junit.Assert.*
import org.junit.Test

class ComedyGeneratorTest {

    private val validKeys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    @Test
    fun `generated song has exactly 7 sections`() {
        assertEquals(7, ComedyGenerator.generate().sections.size)
    }

    @Test
    fun `section labels are correct and in order`() {
        val sections = ComedyGenerator.generate().sections
        assertEquals("Chorus", sections[0].label)
        assertEquals("Chorus Resolution", sections[1].label)
        assertEquals("Verse", sections[2].label)
        assertEquals("Verse Resolution", sections[3].label)
        assertEquals("Bridge", sections[4].label)
        assertEquals("Modulated Chorus", sections[5].label)
        assertEquals("Modulated Chorus Resolution", sections[6].label)
    }

    @Test
    fun `only Modulated Chorus and Modulated Chorus Resolution have isModulated true`() {
        val sections = ComedyGenerator.generate().sections
        assertFalse(sections[0].isModulated)
        assertFalse(sections[1].isModulated)
        assertFalse(sections[2].isModulated)
        assertFalse(sections[3].isModulated)
        assertFalse(sections[4].isModulated)
        assertTrue(sections[5].isModulated)
        assertTrue(sections[6].isModulated)
    }

    @Test
    fun `optional flags are correct for all sections`() {
        val sections = ComedyGenerator.generate().sections
        assertFalse(sections[0].isOptional) // Chorus
        assertTrue(sections[1].isOptional)  // Chorus Resolution
        assertFalse(sections[2].isOptional) // Verse
        assertTrue(sections[3].isOptional)  // Verse Resolution
        assertTrue(sections[4].isOptional)  // Bridge
        assertTrue(sections[5].isOptional)  // Modulated Chorus
        assertTrue(sections[6].isOptional)  // Modulated Chorus Resolution
    }

    @Test
    fun `each section has non-empty chord and numeral lists`() {
        ComedyGenerator.generate().sections.forEach { section ->
            assertTrue("${section.label} chords empty", section.chords.isNotEmpty())
            assertTrue("${section.label} numerals empty", section.romanNumerals.isNotEmpty())
        }
    }

    @Test
    fun `chord count matches roman numeral count per section`() {
        ComedyGenerator.generate().sections.forEach { section ->
            assertEquals(
                "${section.label}: chord/numeral count mismatch",
                section.romanNumerals.size,
                section.chords.size
            )
        }
    }

    @Test
    fun `generated key is a supported major key`() {
        assertTrue(ComedyGenerator.generate().key in validKeys)
    }

    @Test
    fun `modulated key is a supported major key`() {
        assertTrue(ComedyGenerator.generate().modulatedKey in validKeys)
    }

    @Test
    fun `modulated key differs from original key`() {
        val song = ComedyGenerator.generate()
        assertNotEquals(song.key, song.modulatedKey)
    }

    @Test
    fun `chorus and modulated chorus share the same roman numerals`() {
        val sections = ComedyGenerator.generate().sections
        assertEquals(sections[0].romanNumerals, sections[5].romanNumerals)
    }

    @Test
    fun `chorus and modulated chorus have different rendered chords`() {
        val sections = ComedyGenerator.generate().sections
        assertNotEquals(sections[0].chords, sections[5].chords)
    }

    @Test
    fun `chorus resolution and modulated chorus resolution share the same roman numerals`() {
        val sections = ComedyGenerator.generate().sections
        assertEquals(sections[1].romanNumerals, sections[6].romanNumerals)
    }

    @Test
    fun `chorus resolution and modulated chorus resolution have different rendered chords`() {
        val sections = ComedyGenerator.generate().sections
        assertNotEquals(sections[1].chords, sections[6].chords)
    }

    @Test
    fun `generating 20 songs produces at least 2 distinct keys`() {
        val keys = (1..20).map { ComedyGenerator.generate().key }.toSet()
        assertTrue("Expected key variety across 20 songs, got: $keys", keys.size >= 2)
    }
}
