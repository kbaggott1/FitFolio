package com.example.fitfolio.interfaces

import com.example.fitfolio.data.Workout

// Interface for routine providers
interface IWorkoutsProvider {
    fun getWorkouts(): List<Workout>
    fun addWorkout(routine: Workout): Boolean
    fun removeWorkout(routine: Workout): Boolean
}
