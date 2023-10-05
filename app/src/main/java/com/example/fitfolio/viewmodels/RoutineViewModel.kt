package com.example.fitfolio.viewmodels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Routine
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class RoutineViewModel : ViewModel()  {
    private val _routines = getMockRoutines().toMutableStateList()
    val routines: SnapshotStateList<Routine>
        get() = _routines

    fun add(routine: Routine) {
        _routines.add(routine)
    }

    fun remove(routine: Routine) {
        _routines.remove(routine);
    }

}

private fun getMockRoutines(): List<Routine> {
    return listOf<Routine>(
        Routine("Chest Day",null, getMockExercises())
    )
}