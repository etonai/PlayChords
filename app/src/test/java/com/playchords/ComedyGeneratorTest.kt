package com.playchords

import com.playchords.data.ComedyGenerator
import org.junit.Assert.*
import org.junit.Test

class ComedyGeneratorTest {

    private val validKeys = listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B")

    @Test
    fun `generated song has exactly 4 sections`() {
        assertEquals(4, ComedyGenerator.generate().sections.size)
    }

    @Test
    fun `section labels are correct and in order`() {
        val sections = ComedyGenerator.generate().sections
        assertEquals("Chorus", sections[0].label)
        assertEquals("Verse", sections[1].label)
        assertEquals("Bridge", sections[2].label)
        assertEquals("Modulated Chorus", sections[3].label)
    }

    @Test
    fun `only the Modulated Chorus section has isModulated true`() {
        val sections = ComedyGenerator.generate().sections
        assertFalse(sections[0].isModulated)
        assertFalse(sections[1].isModulated)
        assertFalse(sections[2].isModulated)
        assertTrue(sections[3].isModulated)
    }

    @Test
    fun `only the Modulated Chorus section is optional`() {
        val sections = ComedyGenerator.generate().sections
        assertFalse(sections[0].isOptional)
        assertFalse(sections[1].isOptional)
        assertFalse(sections[2].isOptional)
        assertTrue(sections[3].isOptional)
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
        val chorus = sections[0]
        val modulatedChorus = sections[3]
        assertEquals(chorus.romanNumerals, modulatedChorus.romanNumerals)
    }

    @Test
    fun `chorus and modulated chorus have different rendered chords`() {
        val sections = ComedyGenerator.generate().sections
        val chorus = sections[0]
        val modulatedChorus = sections[3]
        assertNotEquals(chorus.chords, modulatedChorus.chords)
    }

    @Test
    fun `generating 20 songs produces at least 2 distinct keys`() {
        val keys = (1..20).map { ComedyGenerator.generate().key }.toSet()
        assertTrue("Expected key variety across 20 songs, got: $keys", keys.size >= 2)
    }
}
