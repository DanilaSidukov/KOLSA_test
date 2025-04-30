package com.diphrogram.kolsa_test.presentation.video

import androidx.lifecycle.viewModelScope
import com.diphrogram.data.converter.DataConverter
import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.repository.video.GetVideoUseCase
import com.diphrogram.kolsa_test.common.BaseViewModel
import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.utils.network.Response
import com.diphrogram.utils.network.Response.Error
import com.diphrogram.utils.network.Response.Loading
import com.diphrogram.utils.network.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase,
    private val dataConverter: DataConverter,
): BaseViewModel<VideoModelState>() {

    override fun createInitialState() = VideoModelState()

    fun getVideo(id: Int) = viewModelScope.launch {
        getVideoUseCase(id).collect(::handleResponse)
    }

    private fun handleResponse(response: Response<Video>) {
        when(response) {
            is Error -> {
                setState {
                    copy(error = response.message)
                }
            }
            is Success -> {
                val videoData = dataConverter.convertVideoData(response.data)
                println("url: ${videoData.url}")
                setState {
                    copy(url = videoData.url)
                }
            }
            else -> {}
        }
        setState {
            copy(
                screenState = if (response is Loading) ScreenState.LoadingProcess
                else ScreenState.LoadingComplete
            )
        }
    }
}