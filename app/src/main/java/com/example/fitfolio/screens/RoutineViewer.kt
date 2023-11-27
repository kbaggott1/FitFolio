package com.example.fitfolio.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Muscles
import com.example.fitfolio.data.Routine
import com.example.fitfolio.viewmodels.ExerciseViewModel
import com.example.fitfolio.viewmodels.RoutineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineViewerScreen(routineViewModel: RoutineViewModel, exerciseViewModel: ExerciseViewModel, routineId: String) {

    LaunchedEffect(routineId) {
        exerciseViewModel.initExercises(routineId)
    }
    val exercises by exerciseViewModel.exerciseList.collectAsState()
    val routine = getRoutineFromId(routineViewModel, routineId)!! //TODO THIS IS RETURNING NULL

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            RoutineTitle(routine = routine)
            LazyColumn(contentPadding = it) {
                items(exercises) { exercise ->
                    ExerciseCard(exercise = exercise, onClose = { exerciseViewModel.remove(exercise) }, modifier = Modifier.padding(8.dp))
                }
                item() {
                    AddExerciseCard(routine = routine, modifier = Modifier.padding(8.dp), addExercise = { exerciseViewModel.add(Exercise("-- Exercise Name --", listOf(Muscles.None), "-- Description --", 0, 0)) })
                }
            }
        }
    }
}


//Gets a routine that matches the provided ID. Returns null if none were found.
private fun getRoutineFromId(routineViewModel: RoutineViewModel, id: String): Routine? {
    for (routine in routineViewModel.routineList.value) {
        if (routine.id == id) {
            return routine
        }
    }
    return null
}


// Represents the routine title. Can be modified to reflect the new name
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineTitle(routine: Routine) {
    var routineNameText by remember { mutableStateOf(routine.name) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = routineNameText,
            onValueChange = {
                routineNameText = it
                routine.name = routineNameText
            },
            label = { Text("Routine Name") },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(SolidColor(Color.Gray))
        )
    }
}

// Displays a single exercise in the form of a card.
@Composable
fun ExerciseCard(exercise: Exercise, onClose: (Any?) -> Unit, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ExerciseInformation(exercise = exercise)
                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Spacer(modifier = Modifier.height(1.dp))
                    IconButton(onClick = { onClose(exercise) }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            }
        }
    }
}

// Displays the information contained inside an exercise card
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun ExerciseInformation(
    exercise: Exercise,
    modifier: Modifier = Modifier
) {
    var exerciseNameText by remember { mutableStateOf(exercise.name) }
    var exerciseDescription by remember { mutableStateOf(exercise.description) }

    Column(modifier = modifier) {
        BasicTextField(
            value = exerciseNameText,
            onValueChange = {
                exerciseNameText = it
                exercise.name = exerciseNameText
            },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            textStyle = TextStyle(fontSize = 30.sp, color = Color.White),
        )

        BasicTextField(
            value = exerciseDescription,
            onValueChange = {
                exerciseDescription = it
                exercise.description = exerciseDescription
            },
            modifier = Modifier.padding(horizontal = 8.dp),
            textStyle = TextStyle(fontSize = 15.sp, color = Color.White),
        )

    }
}

// Card used as a button to add a new exercise to the routine
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseCard(
    routine: Routine,
    modifier: Modifier = Modifier,
    addExercise: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = addExercise
    ) {
        Box(
            modifier =
            Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .fillMaxWidth()
                .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier, contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Filled.AddCircle,
                        contentDescription = "Add Exercise",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
    }
}
