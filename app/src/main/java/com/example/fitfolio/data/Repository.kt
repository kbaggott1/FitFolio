package com.example.fitfolio.data

import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.interfaces.IUsersProvider
import com.example.fitfolio.providers.RoutinesProvider
import com.example.fitfolio.providers.UsersProvider
import com.example.fitfolio.viewmodels.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore


//Repository for all data used in the app
class Repository(
    private val database: FirebaseFirestore,
    private val authenticator: AuthViewModel,
    private val routinesProvider: IRoutinesProvider = RoutinesProvider(database),
    private val usersProvider: IUsersProvider = UsersProvider(database)
) {

    private val userId: String
        get() { return authenticator.getCurrentUser()!!.uid }


    //ROUTINES METHODS
    suspend fun getRoutines(): List<Routine> {
        return routinesProvider.getRoutines(this.userId)
    }

    suspend fun addRoutine(routine: Routine) {
        routinesProvider.addRoutine(this.userId, routine)
    }
    suspend fun removeRoutine(routine: Routine) {
        routinesProvider.removeRoutine(this.userId, routine)
    }

    //USERS METHODS
    suspend fun addUser(user: User): Boolean {
        return usersProvider.addUser(this.userId, user)
    }

    suspend fun getUser(): User? {
        return usersProvider.getUser(this.userId)
    }
}