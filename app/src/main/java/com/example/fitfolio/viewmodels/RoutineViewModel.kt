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

        fun add(routine: Routine) {
            viewModelScope.launch {
                repository.addRoutine(routine)
                // Update the LiveData with the new list of routines
                _routines.add(routine)
            }
        }

        fun remove(routine: Routine) {
            viewModelScope.launch {
                repository.removeRoutine(routine)
                // Update the LiveData with the new list of routines
                _routines = repository.getRoutines().toMutableStateList()
                _routines.remove(routine)
            }
        }
    }

