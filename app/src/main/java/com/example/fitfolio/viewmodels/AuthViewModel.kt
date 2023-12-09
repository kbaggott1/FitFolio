package com.example.fitfolio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel() : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    /**
     * Returns the user that is currently logged in
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Registers a for the app user using firebase
     * @param email Email address of the user being registered
     * @param password Password of the user being registered
     */
    fun registerUser(email: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultLiveData.value = task.isSuccessful
            }

        return resultLiveData
    }

    /**
     * Logs a user into the application using firebase authentication
     * @param email Email address of the user logging in
     * @param password Password of the user that is logging in
     */
    fun loginUser(email: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultLiveData.value = task.isSuccessful
            }

        return resultLiveData
    }

    /**
     * Logs the user out of the application
     */
    fun logout() {
        auth.signOut()
    }
}
