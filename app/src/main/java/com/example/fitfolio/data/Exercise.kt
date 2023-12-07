package com.example.fitfolio.data

/* Class representing an individual Exercise */
class Exercise(var name: String, var muscleGroup: List<Muscles>?, var description: String, var sets: Int, var reps: Int, val id: String = Routine.generateUniqueId()) {
    constructor() : this("", null, "", 0, 0, "")
}
