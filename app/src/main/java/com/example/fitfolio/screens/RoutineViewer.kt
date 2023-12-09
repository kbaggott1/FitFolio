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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Routine
import com.example.fitfolio.viewmodels.ExerciseViewModel
import com.example.fitfolio.viewmodels.RoutineViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutineViewerScreen(
    routineViewModel: RoutineViewModel,
    exerciseViewModel: ExerciseViewModel,
    routineId: String,
    onExerciseClick: (String) -> Unit
) {

    LaunchedEffect(routineId) {
        exerciseViewModel.initExercises(routineId)
    }
    val exercises by exerciseViewModel.exerciseList.collectAsState()
    val routine = getRoutineFromId(routineViewModel, routineId)!!

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            RoutineTitle(routine = routine, routineViewModel)
            LazyColumn(contentPadding = it) {
                items(exercises) { exercise ->
                    ExerciseCard(
                        exercise = exercise,
                        onClose = { exerciseViewModel.remove(exercise) },
                        modifier = Modifier.padding(8.dp),
                        onExerciseClick = {it -> onExerciseClick(it)}
                        )
                }
                item() {
                    AddExerciseCard(
                        routine = routine,
                        modifier = Modifier.padding(8.dp),
                        addExercise = {
                            var exercise = Exercise("", "", 0, 0)
                            runBlocking {
                                exerciseViewModel.add(exercise)
                                onExerciseClick(exercise.id)
                            }

                        })
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
fun RoutineTitle(routine: Routine, routineViewModel: RoutineViewModel) {
    var routineNameText by rememberSaveable { mutableStateOf(routine.name) }
    var routineDescriptionText by rememberSaveable { mutableStateOf(routine.description ?: "") }
    var recompose by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            value = routineNameText,
            onValueChange = {
                routineNameText = it
            },
            label = { Text("Routine Name") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        OutlinedTextField(
            value = routineDescriptionText,
            onValueChange = {
                routineDescriptionText = it
            },
            label = { Text("Routine Description") },
            modifier = Modifier
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
    }

    if(routineNameText != routine.name || routineDescriptionText != (routine.description ?: "")) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier,
                onClick = {
                    routine.name = routineNameText;
                    routine.description = routineDescriptionText;
                    recompose = !recompose
                }
            ) {
                Text("Save changes to routine")
            }
        }

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

    LaunchedEffect(recompose) {}
}

// Displays a single exercise in the form of a card.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseCard(
    exercise: Exercise,
    onClose: (Any?) -> Unit,
    modifier: Modifier = Modifier,
    onExerciseClick: (String) -> Unit
) {
    Card(
        modifier = modifier,
        onClick = {onExerciseClick(exercise.id)}
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
                        Icon(Icons.Filled.Close, contentDescription = "Close",
                            Modifier
                                .scale(2f)
                                .padding(10.dp))
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

    Column(modifier = modifier) {
        Text(
            text = exercise.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = TextStyle(fontSize = 30.sp, color = Color.White),
        )

            Text(
            text = exercise.description,
            modifier = Modifier.padding(horizontal = 8.dp),
            style = TextStyle(fontSize = 20.sp, color = Color.White),
        )

        Row(modifier = Modifier) {
            Text(
                text = "Sets: ${exercise.sets}" ,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = TextStyle(fontSize = 20.sp, color = Color.White),
            )
            Text(
                text = "Reps: ${exercise.reps}" ,
                modifier = Modifier.padding(horizontal = 8.dp),
                style = TextStyle(fontSize = 20.sp, color = Color.White),
            )
        }

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
