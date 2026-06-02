package com.playchords.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.playchords.data.allProgressions
import com.playchords.model.ChordProgression
import com.playchords.model.ProgressionTag
import com.playchords.ui.theme.MutedText
import com.playchords.ui.theme.SurfaceColor

private val categoryOrder = listOf(
    "Classic / Standard",
    "Musical Theatre / Jazz",
    "Expressive / Color"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProgressionScreen(
    onProgressionSelected: (ChordProgression) -> Unit,
    onBack: () -> Unit
) {
    val grouped = categoryOrder.associateWith { cat ->
        allProgressions.filter { it.category == cat }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Progression") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            grouped.forEach { (category, progressions) ->
                item {
                    Text(
                        text = category,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
                    )
                    HorizontalDivider(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
                }
                items(progressions) { progression ->
                    ProgressionRow(
                        progression = progression,
                        onClick = { onProgressionSelected(progression) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProgressionRow(
    progression: ChordProgression,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = progression.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = progression.romanNumerals.joinToString(" – "),
                style = MaterialTheme.typography.bodySmall,
                color = MutedText
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            progression.tags.take(3).forEach { tag ->
                TagChip(tag)
            }
        }
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.06f),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun TagChip(tag: ProgressionTag) {
    val label = when (tag) {
        ProgressionTag.CADENTIAL -> "CAD"
        ProgressionTag.LIFT      -> "LIFT"
        ProgressionTag.LOOP      -> "LOOP"
        ProgressionTag.OPEN      -> "OPEN"
        ProgressionTag.PIVOT     -> "PIVOT"
        ProgressionTag.COLOR     -> "COLOR"
        else                     -> return
    }
    Surface(
        color = SurfaceColor,
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
        )
    }
}
