package com.example.fitfolio.providers

import android.util.Log
import com.example.fitfolio.data.Routine
import com.example.fitfolio.interfaces.IRoutinesProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RoutinesProvider(private val db: FirebaseFirestore) : IRoutinesProvider {

    /**
     * Gets a routine from the database
     * @param userId the user that contains the routine
     */
    override suspend fun getRoutines(userId: String): List<Routine> {
        return try {
            withContext(Dispatchers.IO) {
                val docRef = db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .get().await()

                docRef.toObjects()
            }
        } catch (ex: Exception) {
            Log.e("FIRESTORE", ex.message.toString())
            emptyList()
        }
    }

    /**
     * Adds a routine to the database
     * @param userId the user that will contain the routine
     * @param routine the routine that will be added to the database
     */
    override suspend fun addRoutine(userId: String, routine: Routine): Boolean {
        val routineDocument = db
            .collection("users")
            .document(userId)
            .collection("routines")
            .document(routine.id)
            .set(routine)

        var isSuccess = false
        routineDocument.addOnSuccessListener { isSuccess = true }

        return isSuccess
    }

    /**
     * Removes a routine to the database
     * @param userId the user that contains the routine
     * @param routine the routine that will be removed from the database
     */
    override suspend fun removeRoutine(userId: String, routine: Routine): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routine.id)
                    .delete()
                    .await()
            }
            true
        } catch (ex: Exception) {
            false
        }
    }

    /**
     * Updates a routine to the database
     * @param userId the user that contains the routine
     * @param routine the routine that will be updated in the database
     */
    override suspend fun updateRoutine(
        userId: String,
        routine: Routine
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routine.id)
                    .update(
                        "name",
                        routine.name,
                        "description",
                        routine.description
                    )
                    .await()
            }
            true
        } catch (ex: Exception) {
            false
        }
    }
}
