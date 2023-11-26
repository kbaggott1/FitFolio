package com.example.fitfolio.providers

import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Routine
import com.example.fitfolio.interfaces.IExercisesProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ExercisesProvider(private val db: FirebaseFirestore) : IExercisesProvider{

    /**
     * Gets all exercises that belong to a specific routine from the database
     * @param userId The ID of the user who contains the routine that contains the exercises
     * @param routineId The routine that contains the exercises
     */
    override suspend fun getExercises(userId: String, routineId: String): List<Exercise> {
        return try {
            withContext(Dispatchers.IO) {
                val docRef = db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routineId)
                    .collection("exercises")
                    .get().await()

                docRef.toObjects()
            }
        }
        catch (ex: Exception) {
            emptyList()
        }
    }

    /**
     * Adds an exercise to the database
     * @param userId The ID of the user who contains the routine that will contain the exercise
     * @param routineId The routine that will contain the exercise
     * @param exercise The exercise that will be added
     */
    override suspend fun addExercise(
        userId: String,
        routineId: String,
        exercise: Exercise
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val routineDocument = db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routineId)
                    .collection("exercises")
                    .document(exercise.id)
                    .set(exercise)
                    .await()
            }
            true
        } catch (e: Exception) {
            // Handle the exception if needed
            false
        }
    }

    /**
     * Removes an exercise from the database
     * @param userId The ID of the user who contains the routine that contains the exercise
     * @param routineId The routine that contains the exercise
     * @param exercise The exercise that will be deleted
     */
    override suspend fun removeExercise(
        userId: String,
        routineId: String,
        exercise: Exercise
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routineId)
                    .collection("exercises")
                    .document(exercise.id)
                    .delete()
                    .await()
            }
            true
        } catch (ex: Exception) {
            false
        }
    }

}