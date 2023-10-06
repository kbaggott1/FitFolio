package com.example.fitfolio.viewmodels
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.fitfolio.data.Exercise
import com.example.fitfolio.data.Muscles

class ExerciseViewModel : ViewModel() {
    private val _exercises = getMockExercises().toMutableStateList()
    val exercises: List<Exercise>
        get() = _exercises

    fun add(exercise: Exercise) {
        _exercises.add(exercise)
    }

    fun remove(exercise: Any?) {
        _exercises.remove(exercise)
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
