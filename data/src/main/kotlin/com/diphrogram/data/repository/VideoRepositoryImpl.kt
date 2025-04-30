package com.diphrogram.data.repository

import com.diphrogram.data.network.flowWithCatch
import com.diphrogram.domain.VideoApi
import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.network.Response
import com.diphrogram.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class VideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi
): VideoRepository {

    override suspend fun getVideo(id: Int): Flow<Response<Video>> = flowWithCatch {
        emit(Response.Loading())
        val response = videoApi.getVideo(id)
        val bodyResponse = response.body()
        if (response.isSuccessful && bodyResponse != null) {
            emit(Response.Success(bodyResponse))
        } else {
            emit(Response.Error(response.message()))
        }
    }
}