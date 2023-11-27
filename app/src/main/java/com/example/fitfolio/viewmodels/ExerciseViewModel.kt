package com.example.fitfolio.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Muscles
import com.example.fitfolio.data.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExerciseViewModel(private val repository: Repository) : ViewModel() {
    private var _exercises = MutableStateFlow<List<Exercise>>(emptyList())
    var exerciseList: StateFlow<List<Exercise>> = _exercises.asStateFlow()
    private var routineId: String = ""

    suspend fun initExercises(routineId: String) {

        Log.d("ExerciseViewModel", "Loading exercises for routine $routineId")
        viewModelScope.launch {
            // Update the StateFlow with the new list of routines
            _exercises.value = repository.getExercises(routineId).toMutableList()
        }

        this.routineId = routineId
    }


    fun add(exercise: Exercise) {
        viewModelScope.launch {
            repository.addExercise(routineId, exercise)
            // Update the StateFlow with the new list of routines
            _exercises.value = _exercises.value + exercise
        }
    }

    fun remove(exercise: Exercise) {
        viewModelScope.launch {
            repository.removeExercise(routineId, exercise)
            // Update the StateFlow with the new list of routines
            _exercises.value = _exercises.value - exercise
        }
    }
}

fun getMockExercises(): List<Exercise> {
    return listOf<Exercise>(
        Exercise("Pushups", listOf(Muscles.UPPERCHEST, Muscles.LOWERCHEST, Muscles.TRICEPS, Muscles.FRONTDELTS), "Spread Arms a part and push up", 4, 12),
        Exercise("Seated Bicep Curl", listOf(Muscles.BICEPS), "Sit down and Curl", 4, 12),
        Exercise("Tricep Pull down", listOf(Muscles.TRICEPS), "Pull down on the Cords", 4, 12),
        Exercise("Leg Press", listOf(Muscles.QUADRICEPS), "Push up with your legs", 4, 12),
        Exercise("Chest Press", listOf(Muscles.MIDCHEST, Muscles.UPPERCHEST), "Light Weight", 4, 12),
        Exercise("Flex in the mirror", Muscles.values().toList(), "Appreciate the gains", 10, 200)
    )
}
