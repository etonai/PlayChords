package com.playchords.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playchords.ui.theme.MutedText
import com.playchords.viewmodel.ComedyViewModel

private fun Modifier.verticalScrollbar(
    state: ScrollState,
    trackColor: Color = Color.Gray.copy(alpha = 0.2f),
    thumbColor: Color = Color.Gray.copy(alpha = 0.6f),
    width: Dp = 4.dp
): Modifier = this.drawWithContent {
    drawContent()
    if (state.maxValue > 0) {
        val thumbWidth = width.toPx()
        val viewportHeight = size.height
        val contentHeight = viewportHeight + state.maxValue
        val thumbHeight = (viewportHeight * viewportHeight / contentHeight).coerceAtLeast(40f)
        val thumbTop = (viewportHeight - thumbHeight) * state.value / state.maxValue
        drawRect(
            color = trackColor,
            topLeft = Offset(size.width - thumbWidth, 0f),
            size = Size(thumbWidth, viewportHeight)
        )
        drawRect(
            color = thumbColor,
            topLeft = Offset(size.width - thumbWidth, thumbTop),
            size = Size(thumbWidth, thumbHeight)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComedyScreen(
    viewModel: ComedyViewModel,
    onBack: () -> Unit
) {
    val song by viewModel.song.collectAsState()
    val playingSection by viewModel.playingSection.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comedy Song", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.stopPlayback()
                        onBack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScrollbar(scrollState)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Key of ${song.key}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Random Word: ${song.rhymeWord}",
                style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
                color = MutedText
            )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) { song.sections.forEachIndexed { index, section ->
                val isPlaying = playingSection == index
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (isPlaying) {
                        Button(
                            onClick = { viewModel.playSection(index) },
                            modifier = Modifier
                                .width(160.dp)
                                .height(52.dp)
                        ) {
                            Text(
                                text = section.label,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = { viewModel.playSection(index) },
                            modifier = Modifier
                                .width(160.dp)
                                .height(52.dp)
                        ) {
                            Text(
                                text = section.label,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary,
                                maxLines = 1
                            )
                        }
                    }

                    Column {
                        Text(
                            text = section.chords.joinToString(" – "),
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isPlaying) MaterialTheme.colorScheme.primary else MutedText,
                            fontWeight = if (isPlaying) FontWeight.SemiBold else FontWeight.Normal
                        )
                        if (section.isModulated) {
                            Text(
                                text = "(${song.modulatedKey})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedText
                            )
                        }
                    }
                }
            } }

            HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))

            OutlinedButton(
                onClick = { viewModel.regenerate() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Regenerate",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))

            Text(
                text = "Progression Information",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = MutedText
            )

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                song.sections.forEach { section ->
                    Text(
                        text = "${section.label}: ${section.progressionName} — ${section.romanNumerals.joinToString(" ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MutedText
                    )
                }
            }
        }
    }
}
