package com.example.fitfolio.providers

import android.util.Log
import com.example.fitfolio.data.Routine
import com.example.fitfolio.interfaces.IRoutinesProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RoutinesProvider(private val db: FirebaseFirestore): IRoutinesProvider {

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
        }
        catch (ex: Exception) {
            Log.e("FIRESTORE", ex.message.toString())
            emptyList()
        }
    }

    override suspend fun addRoutine(userId: String, routine: Routine): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val routineDocument = db
                        .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routine.id)
                    .set(routine)
                    .await()
            }
            true
        } catch (e: Exception) {
            // Handle the exception if needed
            false
        }
    }

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

    override suspend fun updateRoutine(
        userId: String,
        routine: Routine,
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                db
                    .collection("users")
                    .document(userId)
                    .collection("routines")
                    .document(routine.id)
                    .update(
                        "name", routine.name,
                        "description", routine.description,

                    )
                    .await()
            }
            true
        } catch (ex: Exception) {
            false
        }
    }
}