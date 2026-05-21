package com.playchords.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

class AudioEngine {

    private val sampleRate = 44100

    private val noteToSemitone = mapOf(
        "C" to 0, "C#" to 1, "Db" to 1, "D" to 2, "D#" to 3, "Eb" to 3,
        "E" to 4, "F" to 5, "F#" to 6, "Gb" to 6, "G" to 7, "G#" to 8,
        "Ab" to 8, "A" to 9, "A#" to 10, "Bb" to 10, "B" to 11
    )

    private data class ParsedChord(
        val rootSemitone: Int,
        val intervals: List<Int>,
        val bassSemitone: Int?
    )

    private fun parseChord(chordName: String): ParsedChord? {
        val bassNoteStr = if ('/' in chordName) chordName.substringAfter('/') else null
        val main = chordName.substringBefore('/')

        val root: String
        val quality: String
        if (main.length >= 2 && (main[1] == '#' || main[1] == 'b')) {
            root = main.substring(0, 2)
            quality = main.substring(2)
        } else {
            root = main.substring(0, 1)
            quality = main.substring(1)
        }

        val rootSemitone = noteToSemitone[root] ?: return null

        val intervals = when {
            quality.endsWith("maj7")              -> listOf(0, 4, 7, 11)
            quality.endsWith("7b5")               -> listOf(0, 3, 6, 10)
            quality.endsWith("dim")               -> listOf(0, 3, 6)
            quality.endsWith("sus4")              -> listOf(0, 5, 7)
            quality.endsWith("7") && !quality.contains("maj") -> listOf(0, 4, 7, 10)
            quality.startsWith("m")               -> listOf(0, 3, 7)
            else                                  -> listOf(0, 4, 7)
        }

        val bassSemitone = bassNoteStr?.let { noteToSemitone[it] }

        return ParsedChord(rootSemitone, intervals, bassSemitone)
    }

    private fun semitoneToFrequency(semitone: Int, octave: Int): Double {
        val midiNote = semitone + (octave + 1) * 12
        return 440.0 * 2.0.pow((midiNote - 69) / 12.0)
    }

    private fun generateSamples(frequencies: List<Double>, durationMs: Long): ShortArray {
        val numSamples = (sampleRate * durationMs / 1000).toInt()
        val amplitude = (Short.MAX_VALUE * 0.6 / frequencies.size).toInt()
        val fadeIn = (sampleRate * 0.015).toInt()
        val fadeOut = (sampleRate * 0.08).toInt()

        return ShortArray(numSamples) { i ->
            val t = i.toDouble() / sampleRate
            var sample = 0.0
            for (freq in frequencies) {
                sample += sin(2 * PI * freq * t)
            }
            val envelope = when {
                i < fadeIn                -> i.toDouble() / fadeIn
                i > numSamples - fadeOut  -> (numSamples - i).toDouble() / fadeOut
                else                      -> 1.0
            }.coerceIn(0.0, 1.0)
            (sample / frequencies.size * amplitude * envelope)
                .toInt()
                .coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
                .toShort()
        }
    }

    suspend fun playChord(chordName: String, durationMs: Long) {
        val parsed = parseChord(chordName) ?: return
        val frequencies = mutableListOf<Double>()

        parsed.bassSemitone?.let { bass ->
            frequencies.add(semitoneToFrequency(bass, octave = 3))
        }

        for (interval in parsed.intervals) {
            val abs = parsed.rootSemitone + interval
            frequencies.add(semitoneToFrequency(abs % 12, octave = 4 + abs / 12))
        }

        val samples = generateSamples(frequencies, durationMs)

        val audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setSampleRate(sampleRate)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(samples.size * 2)
            .setTransferMode(AudioTrack.MODE_STATIC)
            .build()

        try {
            audioTrack.write(samples, 0, samples.size)
            audioTrack.play()
            delay(durationMs)
        } finally {
            audioTrack.stop()
            audioTrack.release()
        }
    }

    fun release() {
        // AudioTrack instances are released per-chord in playChord's finally block
    }
}
