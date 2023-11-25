package com.example.fitfolio.data

import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.interfaces.IUsersProvider
import kotlinx.coroutines.Deferred


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
    fun addUsers(user: User): Boolean{
        return usersProvider.addUser(user)
    }

    fun getUser(){
        usersProvider.getUser()
    }
}