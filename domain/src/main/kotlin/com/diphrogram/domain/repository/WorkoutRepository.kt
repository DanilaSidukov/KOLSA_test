package com.diphrogram.domain.repository

import com.diphrogram.domain.models.workouts.Workouts
import com.diphrogram.domain.network.Response
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun getWorkouts(): Flow<Response<List<Workouts>>>
}