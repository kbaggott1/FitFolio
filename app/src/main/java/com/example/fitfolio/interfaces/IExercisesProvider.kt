package com.example.fitfolio.interfaces

import com.example.fitfolio.data.Exercise

interface IExercisesProvider {

    suspend fun getExercises(userId: String, routineId: String): List<Exercise>
    suspend fun addExercise(userId: String, routineId: String, exercise: Exercise): Boolean
    suspend fun removeExercise(userId: String, routineId: String, exercise: Exercise): Boolean
    suspend fun updateExercise(userId: String, routineId: String, exercise: Exercise): Boolean
}
