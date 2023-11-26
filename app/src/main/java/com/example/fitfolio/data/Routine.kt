package com.example.fitfolio.data

import com.example.fitfolio.viewmodels.ExerciseViewModel
import java.util.UUID

class Routine(var name: String, var description: String?, val exercises: ExerciseViewModel, val id: String = Routine.generateUniqueId()) {

    constructor(): this("", null, ExerciseViewModel(), "")
    companion object {
        val defaultName: String = "New Routine"
        fun generateUniqueId(): String {
            // Implement your logic to generate a unique ID, such as using UUID
            return UUID.randomUUID().toString()
        }
    }


}
