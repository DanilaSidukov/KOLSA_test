package com.diphrogram.data.repository.video

import com.diphrogram.domain.models.video.VideoDto
import com.diphrogram.domain.repository.WorkoutRepository
import com.diphrogram.domain.repository.video.GetVideoUseCase
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetVideoUseCaseImpl @Inject constructor(
    private val repository: WorkoutRepository
): GetVideoUseCase {

    override suspend fun invoke(id: Int): Flow<Response<VideoDto>> = withContext(Dispatchers.IO) {
        repository.getVideo(id)
    }
}