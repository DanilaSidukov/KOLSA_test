package com.diphrogram.data.repository

import com.diphrogram.domain.VideoApi
import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.repository.VideoRepository
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class VideoRepositoryImpl @Inject constructor(
    private val videoApi: VideoApi
): VideoRepository {

    override suspend fun getVideo(id: Int): Flow<Response<Video>> = flow {
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