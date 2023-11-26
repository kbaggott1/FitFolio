package com.example.fitfolio.interfaces

import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Routine

interface IExercisesProvider {

    suspend fun getExercises(userId: String, routineId: String): List<Exercise>
    suspend fun addExercise(userId: String, routineId: String, exercise: Exercise): Boolean
    suspend fun removeExercise(userId: String, routineId: String, exercise: Exercise): Boolean
}