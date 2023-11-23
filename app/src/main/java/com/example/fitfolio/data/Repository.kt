package com.example.fitfolio.data

import com.example.fitfolio.interfaces.IRoutinesProvider


//Repository for all data used in the app
class Repository(
    private val routinesProvider: IRoutinesProvider
    //other providers will go in here
) {
    fun getRoutines(): List<Routine> {
        return routinesProvider.getRoutines()
    }
    fun addRoutine(routine: Routine) {
        routinesProvider.addRoutine(routine)
    }
    fun removeRoutine(routine: Routine) {
        routinesProvider.removeRoutine(routine)
    }
}