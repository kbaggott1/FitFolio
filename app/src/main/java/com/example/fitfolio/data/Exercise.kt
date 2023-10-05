package com.example.fitfolio.data

import androidx.compose.runtime.mutableStateListOf

/* Class representing an individual Exercise */
class Exercise (val name: String, val muscleGroup: List<Muscles>, val description: String, var sets: Int, var reps: Int)