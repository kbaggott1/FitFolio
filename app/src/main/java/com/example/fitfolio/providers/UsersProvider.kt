package com.example.fitfolio.providers

import com.example.fitfolio.data.User
import com.example.fitfolio.interfaces.IUsersProvider
import com.example.fitfolio.viewmodels.AuthViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject


class UsersProvider(private val db: FirebaseFirestore, private val autheticator: AuthViewModel) : IUsersProvider {

    //Gets the user that is currently logged in from the database
    override fun getUser(): User? {
        //Get the snapshot
        val userSnapshot = autheticator.getCurrentUser()?.let { db.collection("users").document(it.uid) }

        //Initialize to null, in the event we can't find a user
        var user: User? = null

        //Look for the user and convert the snapshot to a user
        userSnapshot?.get()?.addOnSuccessListener { documentSnapshot ->
            user = documentSnapshot.toObject<User>()
        }

        //Return the user, will be null if none was found
        return user
    }


    /**
     * Adds a user to the database
     * @param user The user being added to the database
     */
    override fun addUser(user: User): Boolean {
        val documentTask = autheticator.getCurrentUser()?.let { db.collection("users").document(it.uid).set(user) }

        var isSuccess = false
        documentTask?.addOnSuccessListener { isSuccess = true }

        return isSuccess
    }

}