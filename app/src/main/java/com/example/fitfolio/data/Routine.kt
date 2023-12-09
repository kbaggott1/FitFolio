package com.example.fitfolio.data

import java.util.UUID

//A routine contains a list of exercises and belongs to a user
class Routine(val id: String, var name: String, var description: String?) {

    constructor(): this("", "",null)
    companion object {
        val defaultName: String = "New Routine"
        fun generateUniqueId(): String {
            // Implement your logic to generate a unique ID, such as using UUID
            return UUID.randomUUID().toString()
        }
    }
}
