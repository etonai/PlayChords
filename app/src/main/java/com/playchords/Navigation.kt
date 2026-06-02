package com.playchords

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.playchords.ui.*
import com.playchords.viewmodel.ChordPlayerViewModel
import com.playchords.viewmodel.ComedyViewModel
import com.playchords.viewmodel.ILoveViewModel
import com.playchords.viewmodel.IWantViewModel
import com.playchords.viewmodel.PlaybackViewModel
import com.playchords.viewmodel.SelectionViewModel

@Composable
fun PlayChordsNavHost() {
    val navController = rememberNavController()
    val selectionViewModel: SelectionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "main") {

        composable("main") {
            MainScreen(
                onSelectProgression = {
                    navController.navigate("select/progression")
                },
                onRandomProgression = {
                    selectionViewModel.randomize()
                    navController.navigate("playback")
                },
                onPlayChords = {
                    navController.navigate("play/chords")
                },
                onRandomIWant = {
                    navController.navigate("iwant")
                },
                onRandomILove = {
                    navController.navigate("ilove")
                },
                onRandomComedy = {
                    navController.navigate("comedy")
                }
            )
        }

        composable("comedy") {
            val comedyViewModel: ComedyViewModel = viewModel()
            ComedyScreen(
                viewModel = comedyViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("ilove") {
            val iLoveViewModel: ILoveViewModel = viewModel()
            ILoveScreen(
                viewModel = iLoveViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("iwant") {
            val iWantViewModel: IWantViewModel = viewModel()
            IWantScreen(
                viewModel = iWantViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("play/chords") {
            val chordPlayerViewModel: ChordPlayerViewModel = viewModel()
            PlayChordsScreen(
                viewModel = chordPlayerViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable("select/progression") {
            SelectProgressionScreen(
                onProgressionSelected = { progression ->
                    selectionViewModel.setProgression(progression)
                    navController.navigate("select/key")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("select/key") {
            SelectKeyScreen(
                onKeySelected = { key ->
                    selectionViewModel.setKey(key)
                    navController.navigate("select/tempo")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("select/tempo") {
            SelectTempoScreen(
                onTempoSelected = { tempo ->
                    selectionViewModel.setTempo(tempo)
                    navController.navigate("playback") {
                        popUpTo("select/progression") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable("playback") {
            val playbackViewModel: PlaybackViewModel = viewModel()
            PlaybackScreen(
                viewModel = playbackViewModel,
                progressionName = selectionViewModel.selectedProgression?.name ?: "",
                key = selectionViewModel.selectedKey ?: "",
                bpm = selectionViewModel.selectedTempo ?: 80,
                chords = selectionViewModel.resolvedChords() ?: emptyList(),
                onBack = {
                    navController.navigate("main") {
                        popUpTo("main") { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
