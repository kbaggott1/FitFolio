package com.example.fitfolio.interfaces

import com.example.fitfolio.data.Routine

// Interface for routine providers
interface IRoutinesProvider {
    suspend fun getRoutines(userId: String): List<Routine>
    suspend fun addRoutine(userId: String, routine: Routine): Boolean
    suspend fun removeRoutine(userId: String, routine: Routine): Boolean
    suspend fun updateRoutine(userId: String, routine: Routine): Boolean

}
