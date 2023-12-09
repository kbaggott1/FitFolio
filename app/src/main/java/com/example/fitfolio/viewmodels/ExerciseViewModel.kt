package com.example.fitfolio.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Repository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(private val repository: Repository) : ViewModel() {
    private var _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    var exerciseList: StateFlow<List<Exercise>> = _exercises.asStateFlow()
    private var routineId: String = ""

    /**
     * Initializes the exercises
     * @param routineId ID of the routine that the exercises are inside of
     */
    suspend fun initExercises(routineId: String) {
        Log.d("ExerciseViewModel", "Loading exercises for routine $routineId")
        viewModelScope.launch {
            // Update the StateFlow with the new list of routines
            _exercises.value = repository.getExercises(routineId).toMutableList()
        }

        this.routineId = routineId
    }

    /**
     * Adds an exercise the database
     * @param exercise The exercise being added to the database
     */
    suspend fun add(exercise: Exercise) = coroutineScope {
        launch {
            repository.addExercise(routineId, exercise)
            // Update the StateFlow with the new list of routines
            _exercises.value = _exercises.value + exercise
        }
    }

    /**
     * Updates an exercise in the database
     * @param exercise The exercise being updated
     */
    suspend fun update(exercise: Exercise) = coroutineScope {
        launch {
            repository.updateExercise(routineId, exercise)
        }
    }

    /**
     * Removes an exercise from the database
     * @param exercise The exercise being removed from the database
     */
    fun remove(exercise: Exercise) {
        viewModelScope.launch {
            repository.removeExercise(routineId, exercise)
            // Update the StateFlow with the new list of routines
            _exercises.value = _exercises.value - exercise
        }
    }
}

