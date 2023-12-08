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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.viewmodels.ExerciseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseEditorScreen(exerciseViewModel: ExerciseViewModel, exerciseId: String, onExerciseSaved: Unit) {
    val exercise = getExerciseFromId(exerciseId, exerciseViewModel)!!

    // Your UI components go here
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            OutlinedTextField(
                value = exercise.name,
                onValueChange = { exercise.name = it },
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
                value = exercise.description,
                onValueChange = { exercise.description = it },
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
                    value = exercise.sets.toString(),
                    onValueChange = {
                        exercise.sets = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Sets") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                OutlinedTextField(
                    value = exercise.reps.toString(),
                    onValueChange = {
                        exercise.reps = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Reps") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }

            // Save Button
            Button(
                onClick = { onExerciseSaved },
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