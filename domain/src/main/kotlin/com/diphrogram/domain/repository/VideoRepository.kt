package com.diphrogram.domain.repository

import com.diphrogram.domain.models.video.Video
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    suspend fun getVideo(id: Int): Flow<Response<Video>>
}