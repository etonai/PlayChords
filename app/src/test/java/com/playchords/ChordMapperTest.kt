package com.playchords

import com.playchords.data.ChordMapper
import com.playchords.model.ChordProgression
import org.junit.Assert.assertEquals
import org.junit.Test

class ChordMapperTest {

    private fun prog(vararg numerals: String) =
        ChordProgression("Test", "Test", numerals.toList(), emptyList())

    @Test
    fun `C major Pop Axis maps correctly`() {
        val result = ChordMapper.renderProgression("C", prog("I", "V", "vi", "IV"))
        assertEquals(listOf("C", "G", "Am", "F"), result)
    }

    @Test
    fun `G major Two Five One maps correctly`() {
        val result = ChordMapper.renderProgression("G", prog("ii", "V", "I"))
        assertEquals(listOf("Am", "D", "G"), result)
    }

    @Test
    fun `Bb major Minor Pop Loop maps correctly`() {
        val result = ChordMapper.renderProgression("Bb", prog("vi", "IV", "I", "V"))
        assertEquals(listOf("Gm", "Eb", "Bb", "F"), result)
    }

    @Test
    fun `D major slash chord maps correctly`() {
        val result = ChordMapper.renderProgression("D", prog("I", "V/7", "vi", "V"))
        assertEquals(listOf("D", "A/C#", "Bm", "A"), result)
    }

    @Test
    fun `Eb major half-diminished maps correctly`() {
        val result = ChordMapper.renderProgression("Eb", prog("iiø", "V7", "i"))
        assertEquals(listOf("Fm7b5", "Bb7", "Ebm"), result)
    }

    @Test
    fun `unknown key returns empty list`() {
        val result = ChordMapper.renderProgression("X", prog("I", "V"))
        assertEquals(emptyList<String>(), result)
    }

    @Test
    fun `keyOneWholeStepHigher basic cases`() {
        assertEquals("D", ChordMapper.keyOneWholeStepHigher("C"))
        assertEquals("Eb", ChordMapper.keyOneWholeStepHigher("Db"))
        assertEquals("G", ChordMapper.keyOneWholeStepHigher("F"))
    }

    @Test
    fun `keyOneWholeStepHigher wraps from Bb to C`() {
        assertEquals("C", ChordMapper.keyOneWholeStepHigher("Bb"))
    }

    @Test
    fun `keyOneWholeStepHigher returns null for unknown key`() {
        assertEquals(null, ChordMapper.keyOneWholeStepHigher("X"))
    }
}
