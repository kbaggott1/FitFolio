package com.example.fitfolio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultLiveData.value = task.isSuccessful
            }

        return resultLiveData
    }

    fun loginUser(email: String, password: String): LiveData<Boolean> {
        val resultLiveData = MutableLiveData<Boolean>()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                resultLiveData.value = task.isSuccessful
            }

        return resultLiveData
    }

    fun logout() {
        auth.signOut()
    }
}