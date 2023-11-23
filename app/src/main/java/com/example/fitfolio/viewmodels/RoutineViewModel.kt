package com.example.fitfolio.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.fitfolio.data.Repository
import com.example.fitfolio.data.Routine
class RoutineViewModel(private val repository: Repository) : ViewModel() {
    val routines: SnapshotStateList<Routine>
        get() = repository.getRoutines().toMutableStateList()

    fun add(routine: Routine) {
        repository.addRoutine(routine)
    }
    fun remove(routine: Routine) {
        repository.removeRoutine(routine)
    }
}
