package com.diphrogram.domain

import com.diphrogram.domain.models.video.Video
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("get_video")
    suspend fun getVideo(
        @Query("id") id: Int
    ): Response<Video>
}