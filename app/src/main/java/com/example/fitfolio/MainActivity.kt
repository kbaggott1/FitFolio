package com.example.fitfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitfolio.data.Repository
import com.example.fitfolio.providers.InMemoryRoutinesProvider
import com.example.fitfolio.screens.AboutScreen
import com.example.fitfolio.screens.LoginScreen
import com.example.fitfolio.screens.MotivationScreen
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
    repository: Repository = Repository(InMemoryRoutinesProvider()),
    exerciseViewModel: ExerciseViewModel = viewModel(),
    routineViewModel: RoutineViewModel = RoutineViewModel(repository)
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
            if(currentPage != "Login")
            {
                BottomAppBar (
                    content = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ){
                            IconButton(onClick = { navController.navigate(route = "RoutinesOverview")}) {
                                Icon(Icons.Filled.Home, contentDescription = "Routine Overview")
                            }
                            IconButton(onClick = { navController.navigate(route = "About")}) {
                                Icon(Icons.Filled.Face, contentDescription = "About us screen")
                            }
                            IconButton(onClick = { navController.navigate(route = "Motivation")}) {
                                Icon(Icons.Filled.Favorite, contentDescription = "About us screen")
                            }
                            IconButton(onClick = { }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Logout")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

        }
        ) {
        NavHost(
            navController = navController,
            startDestination = "Login",
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
            composable("About") {
                currentPage = "About"
                AboutScreen()
            }
            composable("Motivation") {
                currentPage = "Motivation"
                MotivationScreen()
            }
            composable("Login") {
                currentPage = "Login"
                LoginScreen()
            }
        }
    }
}