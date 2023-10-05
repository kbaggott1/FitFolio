package com.example.fitfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fitfolio.ui.theme.FitFolioTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitfolio.viewmodels.ExerciseViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitfolio.screens.RoutineOverviewScreen
import com.example.fitfolio.screens.RoutineViewerScreen
import com.example.fitfolio.viewmodels.RoutineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitFolioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FitFolio();
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitFolio(
    modifier: Modifier = Modifier,
    exerciseViewModel: ExerciseViewModel = viewModel(),
    routineViewModel: RoutineViewModel = viewModel()
    ) {
    val navController = rememberNavController()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("FitFolio")
            }
        )
    }) {
        NavHost(
            navController = navController,
            startDestination = "RoutinesOverview",
            modifier = modifier.padding(it)
        ) {
            composable("RoutinesOverview") {
                RoutineOverviewScreen(modifier, routineViewModel)
            }
            composable("RoutineViewer") {
                RoutineViewerScreen(exerciseViewModel, routineViewModel)
            }
        }
    }
}
