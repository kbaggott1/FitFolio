package com.example.fitfolio.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.viewmodels.ExerciseViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditorScreen(exerciseViewModel: ExerciseViewModel, exerciseId: String, onExerciseSaved: () -> Boolean) {
    val exercise = getExerciseFromId(exerciseId, exerciseViewModel)!!
    var exerciseName by rememberSaveable { mutableStateOf(exercise.name) }
    var description by rememberSaveable { mutableStateOf(exercise.description) }
    var sets by rememberSaveable { mutableStateOf(exercise.sets.toString()) }
    var reps by rememberSaveable { mutableStateOf(exercise.reps.toString()) }
    // Your UI components go here
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = exerciseName,
                onValueChange = { exerciseName = it; exercise.name = it; },
                label = { Text("Exercise Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Muscle Group (assuming Muscles is a sealed class or enum)
            // Add your logic to select muscle groups
            // ...

            // Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it; exercise.description = it },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Sets and Reps
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = sets,
                    onValueChange = {
                        sets = it; exercise.sets = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Sets") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                OutlinedTextField(
                    value = reps,
                    onValueChange = {
                        reps = it; exercise.reps = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Reps") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            // Save Button
            Button(
                onClick = {
                    runBlocking {
                        exerciseViewModel.update(exercise)
                        onExerciseSaved()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Save")
            }
        }
    }
}

private fun getExerciseFromId(exerciseId: String, exerciseViewModel: ExerciseViewModel): Exercise? {
    for (exercise in exerciseViewModel.exerciseList.value) {
        if (exercise.id == exerciseId) {
            return exercise
        }
    }
    return null
}
