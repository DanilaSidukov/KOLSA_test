package com.diphrogram.data.repository

import com.diphrogram.domain.Api
import com.diphrogram.domain.models.video.VideoDto
import com.diphrogram.domain.models.workouts.WorkoutsDto
import com.diphrogram.domain.repository.WorkoutRepository
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class WorkoutRepositoryImpl @Inject constructor(
    private val api: Api
): WorkoutRepository {

    override suspend fun getWorkouts(): Flow<Response<List<WorkoutsDto>>> = flow {
        emit(Response.Loading())
        val response = api.getWorkouts()
        val bodyResponse = response.body()
        if (response.isSuccessful && bodyResponse != null) {
            emit(Response.Success(bodyResponse))
        } else {
            emit(Response.Error(response.message()))
        }
    }

    override suspend fun getVideo(id: Int): Flow<Response<VideoDto>> = flow {
        emit(Response.Loading())
        val response = api.getVideo(id)
        val bodyResponse = response.body()
        if (response.isSuccessful && bodyResponse != null) {
            emit(Response.Success(bodyResponse))
        } else {
            emit(Response.Error(response.message()))
        }
    }
}