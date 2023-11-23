package com.example.fitfolio.interfaces

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.fitfolio.data.Routine

//Interface for routine providers
interface IRoutinesProvider {
    fun getRoutines(): List<Routine>
    fun addRoutine(routine: Routine): Boolean
    fun removeRoutine(routine: Routine): Boolean
}