package com.example.fitfolio.data

import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.interfaces.IUsersProvider
import com.example.fitfolio.providers.InMemoryRoutinesProvider
import com.example.fitfolio.providers.UsersProvider
import com.example.fitfolio.viewmodels.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore


//Repository for all data used in the app
class Repository(
    private val database: FirebaseFirestore,
    private val authenticator: AuthViewModel,
    private val routinesProvider: IRoutinesProvider = InMemoryRoutinesProvider(),
    private val usersProvider: IUsersProvider = UsersProvider(database)
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
        return usersProvider.addUser(authenticator.getCurrentUser()!!.uid, user)
    }

    suspend fun getUser(): User? {
        return usersProvider.getUser(authenticator.getCurrentUser()!!.uid)
    }
}