package com.example.fitfolio.data

import com.example.fitfolio.interfaces.IExercisesProvider
import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.interfaces.IUsersProvider
import com.example.fitfolio.providers.ExercisesProvider
import com.example.fitfolio.providers.RoutinesProvider
import com.example.fitfolio.providers.UsersProvider
import com.example.fitfolio.viewmodels.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore


//Repository for all data used in the app
class Repository(
    private val database: FirebaseFirestore,
    private val authenticator: AuthViewModel,
    private val routinesProvider: IRoutinesProvider = RoutinesProvider(database),
    private val usersProvider: IUsersProvider = UsersProvider(database),
    private val exercisesProvider: IExercisesProvider = ExercisesProvider(database)
) {

    private val userId: String
        get() { return authenticator.getCurrentUser()!!.uid }


    // -- ROUTINES METHODS --

    //Retrieves a routine from the repository
    suspend fun getRoutines(): List<Routine> {
        return routinesProvider.getRoutines(this.userId)
    }

    //Adds a routine to the repository
    suspend fun addRoutine(routine: Routine) {
        routinesProvider.addRoutine(this.userId, routine)
    }

    //Removes a routine from the repository
    suspend fun removeRoutine(routine: Routine) {
        routinesProvider.removeRoutine(this.userId, routine)
    }

    //EXERCISE METHODS

    //Gets an exercise from the repository
    suspend fun getExercises(routineId: String): List<Exercise> {
        return exercisesProvider.getExercises(this.userId, routineId)
    }

    //Adds an exercise to the repository
    suspend fun addExercise(routineId: String, exercise: Exercise) {
        exercisesProvider.addExercise(this.userId, routineId, exercise)
    }

    //Removes an exercise from the repository
    suspend fun removeExercise(routineId: String, exercise: Exercise){
        exercisesProvider.removeExercise(this.userId, routineId, exercise)
    }

    //Updates an exercise in the repository
    suspend fun updateExercise(routineId: String, exercise: Exercise) {
        exercisesProvider.updateExercise(this.userId, routineId, exercise)
    }

    //USERS METHODS

    //Adds a user to the repository
    suspend fun addUser(user: User): Boolean {
        return usersProvider.addUser(this.userId, user)
    }

    //Gets a user from the repository
    suspend fun getUser(): User? {
        return usersProvider.getUser(this.userId)
    }
}