package com.example.fitfolio.viewmodels

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.fitfolio.data.Repository
import com.example.fitfolio.data.Routine
import kotlinx.coroutines.*
class RoutineViewModel(private val repository: Repository) : ViewModel() {
    val routines: SnapshotStateList<Routine>
        get() = runBlocking {
            async {
                repository.getRoutines()
            }.await().toMutableStateList()
        }

    fun add(routine: Routine) {
        repository.addRoutine(routine)
    }
    fun remove(routine: Routine) {
        repository.removeRoutine(routine)
    }
}
