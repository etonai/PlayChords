package com.playchords.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playchords.ui.theme.AccentColor
import com.playchords.ui.theme.MutedText
import com.playchords.ui.theme.SurfaceColor
import com.playchords.viewmodel.PlaybackViewModel
import com.playchords.viewmodel.PlaybackState
import kotlinx.coroutines.launch

@Composable
fun PlaybackScreen(
    viewModel: PlaybackViewModel,
    progressionName: String,
    key: String,
    bpm: Int,
    chords: List<String>,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startPlayback(chords, bpm)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PlaybackHeader(progressionName = progressionName, key = key, bpm = bpm, state = state)

            CurrentChordDisplay(chords = chords, state = state)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                ChordProgressionBar(chords = chords, currentIndex = state.currentChordIndex, isPlaying = state.isPlaying)

                Spacer(modifier = Modifier.height(32.dp))

                if (state.isDone) {
                    DoneButtons(
                        onPlayAgain = { viewModel.playAgain() },
                        onBack = onBack
                    )
                } else {
                    Text(
                        text = "Playing loop ${state.currentLoop} of ${state.totalLoops}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MutedText
                    )
                }
            }
        }
    }
}

@Composable
private fun PlaybackHeader(
    progressionName: String,
    key: String,
    bpm: Int,
    state: PlaybackState
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = progressionName,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InfoBadge(label = "Key", value = key)
            InfoBadge(label = "BPM", value = "$bpm")
        }
    }
}

@Composable
private fun InfoBadge(label: String, value: String) {
    Surface(color = SurfaceColor, shape = MaterialTheme.shapes.small) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.labelSmall, color = MutedText)
            Text(
                text = value,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun CurrentChordDisplay(chords: List<String>, state: PlaybackState) {
    val currentChord = if (chords.isNotEmpty() && state.currentChordIndex < chords.size) {
        chords[state.currentChordIndex]
    } else ""

    Box(contentAlignment = Alignment.Center) {
        Text(
            text = currentChord,
            fontSize = 96.sp,
            fontWeight = FontWeight.Bold,
            color = if (state.isDone) MutedText else AccentColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ChordProgressionBar(
    chords: List<String>,
    currentIndex: Int,
    isPlaying: Boolean
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentIndex) {
        if (isPlaying) {
            scope.launch { listState.animateScrollToItem(currentIndex) }
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(chords) { index, chord ->
            val isCurrent = index == currentIndex && isPlaying
            Surface(
                color = if (isCurrent) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else SurfaceColor,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .border(
                        width = if (isCurrent) 2.dp else 1.dp,
                        color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Text(
                    text = chord,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun DoneButtons(onPlayAgain: () -> Unit, onBack: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onPlayAgain,
            modifier = Modifier.fillMaxWidth(0.7f).height(52.dp)
        ) {
            Text("Play Again", fontWeight = FontWeight.SemiBold)
        }
        TextButton(onClick = onBack) {
            Text("Back to Main", color = MutedText)
        }
    }
}
