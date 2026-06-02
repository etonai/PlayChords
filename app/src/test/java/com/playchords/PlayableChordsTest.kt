package com.playchords

import com.playchords.data.PlayableChords
import org.junit.Assert.*
import org.junit.Test

class PlayableChordsTest {

    @Test
    fun `allChords is non-empty`() {
        assertTrue(PlayableChords.allChords.isNotEmpty())
    }

    @Test
    fun `allChords contains all five types for C`() {
        val all = PlayableChords.allChords
        assertTrue("C" in all)
        assertTrue("Cm" in all)
        assertTrue("Cdim" in all)
        assertTrue("C7" in all)
        assertTrue("Cmaj7" in all)
    }

    @Test
    fun `allChords contains all five types for Bb`() {
        val all = PlayableChords.allChords
        assertTrue("Bb" in all)
        assertTrue("Bbm" in all)
        assertTrue("Bbdim" in all)
        assertTrue("Bb7" in all)
        assertTrue("Bbmaj7" in all)
    }

    @Test
    fun `groups covers all 12 roots`() {
        val roots = PlayableChords.groups.map { it.root }
        assertEquals(12, roots.size)
        listOf("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B").forEach { root ->
            assertTrue("Expected root $root", root in roots)
        }
    }

    @Test
    fun `each group has exactly five chords`() {
        PlayableChords.groups.forEach { group ->
            assertEquals("Group ${group.root} should have 5 chords", 5, group.chords.size)
        }
    }

    @Test
    fun `allChords has no duplicates`() {
        val all = PlayableChords.allChords
        assertEquals(all.distinct().size, all.size)
    }
}
