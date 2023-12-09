package com.example.fitfolio.data

/* Class representing an individual Exercise */
class Exercise(var name: String, var description: String, var sets: Int, var reps: Int, val id: String = Routine.generateUniqueId()) {
    constructor() : this("", "", 0, 0, "")
}
