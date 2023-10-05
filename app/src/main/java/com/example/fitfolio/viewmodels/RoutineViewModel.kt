package com.example.fitfolio.viewmodels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Routine
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewmodel.compose.viewModel
class RoutineViewModel : ViewModel()  {
    private val _routines = getMockRoutines().toMutableStateList()
    val routines: SnapshotStateList<Routine>
        get() = _routines

    fun add(routine: Routine) {
        for(r in _routines) {
            if (routine.id == r.id ) {
                return
            }
        }
        _routines.add(routine)
    }
    fun remove(routine: Routine) {
        _routines.remove(routine);
    }

}

private fun getMockRoutines(): List<Routine> {
    return listOf<Routine>(
        Routine(0,"Chest Day",null, ExerciseViewModel()),
        Routine(1,"Back Day",null, ExerciseViewModel()),
        Routine(2, "Leg Day",null, ExerciseViewModel())
    )
}