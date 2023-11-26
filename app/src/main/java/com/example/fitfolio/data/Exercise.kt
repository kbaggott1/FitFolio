package com.example.fitfolio.data
import java.util.UUID

/* Class representing an individual Exercise */
class Exercise(var name: String, var muscleGroup: List<Muscles>, var description: String, var sets: Int, var reps: Int, val id: String = Routine.generateUniqueId())
