package com.playchords.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playchords.ui.theme.MutedText
import com.playchords.viewmodel.ComedyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComedyScreen(
    viewModel: ComedyViewModel,
    onBack: () -> Unit
) {
    val song by viewModel.song.collectAsState()
    val playingSection by viewModel.playingSection.collectAsState()

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
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Key of ${song.key}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

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

            song.sections.forEachIndexed { index, section ->
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
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = section.label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                if (section.isOptional) {
                                    Text(
                                        text = "(optional)",
                                        fontSize = 10.sp,
                                        color = LocalContentColor.current.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    } else {
                        OutlinedButton(
                            onClick = { viewModel.playSection(index) },
                            modifier = Modifier
                                .width(160.dp)
                                .height(52.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = section.label,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary,
                                    maxLines = 1
                                )
                                if (section.isOptional) {
                                    Text(
                                        text = "(optional)",
                                        fontSize = 10.sp,
                                        color = MutedText
                                    )
                                }
                            }
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
            }
        }
    }
}
