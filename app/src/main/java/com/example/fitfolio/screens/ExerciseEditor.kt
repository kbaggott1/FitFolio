package com.example.fitfolio.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun ExerciseEditorScreen(exerciseId: String) {
    var name by rememberSaveable { String }
}