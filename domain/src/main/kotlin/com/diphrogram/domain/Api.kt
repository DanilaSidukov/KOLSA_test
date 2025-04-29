package com.diphrogram.domain

import com.diphrogram.domain.models.video.VideoDto
import com.diphrogram.domain.models.workouts.WorkoutsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("get_workouts")
    suspend fun getWorkouts(): Response<List<WorkoutsDto>>

    @GET("get_video")
    suspend fun getVideo(
        @Query("id") id: Int
    ): Response<VideoDto>
}