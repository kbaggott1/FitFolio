package com.example.fitfolio.providers

import android.util.Log
import com.example.fitfolio.data.User
import com.example.fitfolio.interfaces.IUsersProvider
import com.example.fitfolio.viewmodels.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class UsersProvider(private val db: FirebaseFirestore, private val authenticator: AuthViewModel) : IUsersProvider {

    //Gets the user that is currently logged in from the database
    override suspend fun getUser(): User? {
        //Get the snapshot
        val userid = authenticator.getCurrentUser()?.uid

        val docRef = db.collection("users").document(userid!!)
        return try {
                withContext(Dispatchers.IO) {
                    val userSnapshot = docRef.get().await()
                    userSnapshot.toObject<User>()
                }
            }

        catch (e: Exception) {
            Log.d("E", e.toString())
            null
        }
    }


    /**
     * Adds a user to the database
     * @param user The user being added to the database
     */
    override suspend fun addUser(user: User): Boolean {
        val documentTask = authenticator.getCurrentUser()?.let { db.collection("users").document(it.uid).set(user) }

        var isSuccess = false
        documentTask?.addOnSuccessListener { isSuccess = true }

        return isSuccess
    }

}