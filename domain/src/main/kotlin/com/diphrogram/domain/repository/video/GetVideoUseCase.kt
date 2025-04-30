package com.diphrogram.domain.repository.video

import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.network.Response
import kotlinx.coroutines.flow.Flow

interface GetVideoUseCase {

    suspend operator fun invoke(id: Int): Flow<Response<Video>>
}