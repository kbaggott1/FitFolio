package com.example.fitfolio.data

import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.interfaces.IUsersProvider


//Repository for all data used in the app
class Repository(
    private val routinesProvider: IRoutinesProvider,
    private val usersProvider: IUsersProvider
) {

    //ROUTINES METHODS
    suspend fun getRoutines(): List<Routine> {
        return routinesProvider.getRoutines()
    }

    fun addRoutine(routine: Routine) {
        routinesProvider.addRoutine(routine)
    }
    fun removeRoutine(routine: Routine) {
        routinesProvider.removeRoutine(routine)
    }

    //USERS METHODS
    suspend fun addUser(user: User): Boolean {
        return usersProvider.addUser(user)
    }

    suspend fun getUser(): User? {
        return usersProvider.getUser()
    }
}