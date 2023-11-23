package com.example.fitfolio.providers

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.example.fitfolio.data.Routine
import com.example.fitfolio.interfaces.IRoutinesProvider
import com.example.fitfolio.viewmodels.ExerciseViewModel

//A routine provider that uses ram to store routines, this will only be used in development
class InMemoryRoutinesProvider : IRoutinesProvider {

    private val _routines: SnapshotStateList<Routine> = getMockRoutines().toMutableStateList()
    override fun getRoutines(): List<Routine> {
        return _routines
    }

    override fun addRoutine(routine: Routine): Boolean {
        for (r in _routines) {
            if (routine.id == r.id) {
                return false
            }
        }
        return _routines.add(routine)
    }

    override fun removeRoutine(routine: Routine): Boolean {
        return _routines.remove(routine)
    }

    private fun getMockRoutines(): List<Routine> {
        return listOf<Routine>(
            Routine(0, "Chest Day", null, ExerciseViewModel()),
            Routine(1, "Back Day", null, ExerciseViewModel()),
            Routine(2, "Leg Day", null, ExerciseViewModel())
        )
    }
}