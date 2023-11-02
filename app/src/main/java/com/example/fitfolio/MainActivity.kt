package com.example.fitfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.AlertDialogDefaults.titleContentColor
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.smallTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitfolio.screens.RoutineOverviewScreen
import com.example.fitfolio.screens.RoutineViewerScreen
import com.example.fitfolio.ui.theme.FitFolioTheme
import com.example.fitfolio.viewmodels.ExerciseViewModel
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
                    FitFolio()
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
    var currentPage by rememberSaveable { mutableStateOf("Routines Overview") }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            colors = smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),

            title = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                    Text(text = currentPage, textAlign = TextAlign.Center)
                }
            }
        )
    },
        bottomBar = {
            BottomAppBar (
                actions = {
                IconButton(onClick = { navController.navigate(route = "RoutinesOverview")}) {
                    Icon(Icons.Filled.Home, contentDescription = "Routine Overview")
                }
            })
        }
        ) {
        NavHost(
            navController = navController,
            startDestination = "RoutinesOverview",
            modifier = modifier.padding(it)
        ) {
            composable("RoutinesOverview") {
                currentPage = "Routines Overview"
                RoutineOverviewScreen(modifier, routineViewModel, navController)
            }
            composable(
                "RoutineViewer/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType }
                )
            ) { navBackStackEntry ->
                val routineId = navBackStackEntry.arguments?.getInt("id")
                currentPage = "Routine Viewer"
                RoutineViewerScreen(routineViewModel, routineId!!)
            }
        }
    }
}
