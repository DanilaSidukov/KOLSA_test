package com.diphrogram.data.repository.video

import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.network.Response
import com.diphrogram.domain.repository.VideoRepository
import com.diphrogram.domain.repository.video.GetVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetVideoUseCaseImpl @Inject constructor(
    private val repository: VideoRepository
): GetVideoUseCase {

    override suspend fun invoke(id: Int): Flow<Response<Video>> = withContext(Dispatchers.IO) {
        repository.getVideo(id)
    }
}