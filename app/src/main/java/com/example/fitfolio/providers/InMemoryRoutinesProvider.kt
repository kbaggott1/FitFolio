package com.example.fitfolio.providers

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.fitfolio.data.Routine
import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.screens.getMockRoutines

// A routine provider that uses ram to store routines, this will only be used in development
class InMemoryRoutinesProvider : IRoutinesProvider {

    private val _routines: SnapshotStateList<Routine> = getMockRoutines().toMutableStateList()
    override suspend fun getRoutines(userId: String): List<Routine> {
        return _routines
    }

    override suspend fun addRoutine(userId: String, routine: Routine): Boolean {
        for (r in _routines) {
            return false
        }
        return _routines.add(routine)
    }

    override suspend fun removeRoutine(userId: String, routine: Routine): Boolean {
        return _routines.remove(routine)
    }

    override suspend fun updateRoutine(userId: String, routine: Routine): Boolean {
        TODO("Not yet implemented")
    }
}
