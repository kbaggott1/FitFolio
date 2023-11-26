package com.example.fitfolio.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfolio.data.Repository
import com.example.fitfolio.data.Routine
import kotlinx.coroutines.launch

class RoutineViewModel(private val repository: Repository) : ViewModel() {
        var _routines = mutableStateListOf<Routine>()
        var routineList: SnapshotStateList<Routine> = _routines
                get() {return _routines}

    init {
        viewModelScope.launch {
            _routines = repository.getRoutines().toMutableStateList()
        }
    }
       fun getRoutines(): SnapshotStateList<Routine> {
           viewModelScope.launch {
               _routines = repository.getRoutines().toMutableStateList()
           }
           return _routines
       }

    /**
     * Adds a routine to the database and to the list in this viewmodel.
     * The list is what we use in the front-end to access the necessary
     * data about the routines.
     * @param routine The routine being added to the database
     */
    fun add(routine: Routine) {
            viewModelScope.launch {
                repository.addRoutine(routine)
                // Update the LiveData with the new list of routines
                _routines.add(routine)
            }
        }

    /**
     * Removes a routine to the database and to the list in this viewmodel.
     * The list is what we use in the front-end to access the necessary
     * data about the routines.
     * @param routine The routine being removed from the database
     */
    fun remove(routine: Routine) {
        viewModelScope.launch {
            repository.removeRoutine(routine)
            // Update the LiveData with the new list of routines
            _routines.remove(routine)
        }
    }
}

