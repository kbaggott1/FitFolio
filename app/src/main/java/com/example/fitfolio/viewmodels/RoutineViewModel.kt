package com.example.fitfolio.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfolio.data.Repository
import com.example.fitfolio.data.Routine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoutineViewModel(private val repository: Repository) : ViewModel() {
        private var _routines = MutableStateFlow<List<Routine>>(emptyList())
        var routineList: StateFlow<List<Routine>> = _routines.asStateFlow()

    init {
        viewModelScope.launch {
            // Update the StateFlow with the new list of routines
            _routines.value = repository.getRoutines().toMutableList()
        }
    }
    suspend fun getRoutines(): List<Routine> {
        val routines = repository.getRoutines()
        // Update the StateFlow with the new list of routines
        _routines.value = routines.toMutableList()
        return routines
    }

    /**
     * Adds a routine to the database and updates the StateFlow.
     * @param routine The routine being added to the database
     */
    fun add(routine: Routine) {
        viewModelScope.launch {
            repository.addRoutine(routine)
            // Update the StateFlow with the new list of routines
            _routines.value = _routines.value + routine
        }
    }

    /**
     * Removes a routine from the database and updates the StateFlow.
     * @param routine The routine being removed from the database
     */
    fun remove(routine: Routine) {
        viewModelScope.launch {
            repository.removeRoutine(routine)
            // Update the StateFlow with the new list of routines
            _routines.value = _routines.value - routine
        }
    }
}

