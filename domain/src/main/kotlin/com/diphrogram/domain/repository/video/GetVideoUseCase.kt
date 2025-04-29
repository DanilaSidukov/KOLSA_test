package com.diphrogram.domain.repository.video

import com.diphrogram.domain.models.video.VideoDto
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.flow.Flow

interface GetVideoUseCase {

    suspend operator fun invoke(id: Int): Flow<Response<VideoDto>>
}