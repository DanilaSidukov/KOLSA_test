package com.diphrogram.domain.repository

import com.diphrogram.domain.models.video.VideoDto
import com.diphrogram.domain.models.workouts.WorkoutsDto
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {

    suspend fun getWorkouts(): Flow<Response<List<WorkoutsDto>>>

    suspend fun getVideo(id: Int): Flow<Response<VideoDto>>
}