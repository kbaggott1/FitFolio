package com.example.fitfolio.data

import com.example.fitfolio.viewmodels.ExerciseViewModel

// Workout class contains information on passed exercise. A workout is a saved / completed routine
class Workout(val id: Int, var name: String, var description: String?, val exercises: ExerciseViewModel)
