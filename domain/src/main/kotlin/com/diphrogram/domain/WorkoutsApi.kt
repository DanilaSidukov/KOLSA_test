package com.diphrogram.domain

import com.diphrogram.domain.models.workouts.Workouts
import retrofit2.Response
import retrofit2.http.GET

interface WorkoutsApi {

    @GET("get_workouts")
    suspend fun getWorkouts(): Response<List<Workouts>>
}