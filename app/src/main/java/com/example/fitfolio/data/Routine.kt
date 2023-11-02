package com.example.fitfolio.data

import com.example.fitfolio.viewmodels.ExerciseViewModel
class Routine(val id: Int, var name: String, var description: String?, val exercises: ExerciseViewModel) {
    companion object {
        val defaultName: String = "New Routine"
    }
}

