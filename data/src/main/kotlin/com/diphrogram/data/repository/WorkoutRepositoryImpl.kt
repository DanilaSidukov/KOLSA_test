package com.diphrogram.data.repository

import com.diphrogram.data.network.flowWithCatch
import com.diphrogram.domain.WorkoutsApi
import com.diphrogram.domain.models.workouts.Workouts
import com.diphrogram.domain.network.Response
import com.diphrogram.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class WorkoutRepositoryImpl @Inject constructor(
    private val workoutsApi: WorkoutsApi
): WorkoutRepository {

    override suspend fun getWorkouts(): Flow<Response<List<Workouts>>> = flowWithCatch {
        emit(Response.Loading())
        val response = workoutsApi.getWorkouts()
        val bodyResponse = response.body()
        if (response.isSuccessful && bodyResponse != null) {
            emit(Response.Success(bodyResponse))
        } else {
            emit(Response.Error(response.message()))
        }
    }
}