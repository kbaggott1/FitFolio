package com.example.fitfolio.providers

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.fitfolio.data.Routine
import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.screens.getMockRoutines
import com.example.fitfolio.viewmodels.ExerciseViewModel

//A routine provider that uses ram to store routines, this will only be used in development
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

    /*
    private fun getMockRoutines(): List<Routine> {
        return listOf<Routine>(
            Routine("Chest Day", null, ExerciseViewModel()),
            Routine("Back Day", null, ExerciseViewModel()),
            Routine("Leg Day", null, ExerciseViewModel())
        )
    }
    */
}