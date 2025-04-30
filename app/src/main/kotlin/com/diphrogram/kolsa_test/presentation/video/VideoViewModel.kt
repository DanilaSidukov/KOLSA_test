package com.diphrogram.kolsa_test.presentation.video

import androidx.annotation.OptIn
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.MappingTrackSelector
import com.diphrogram.data.converter.toUI
import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.network.Response
import com.diphrogram.domain.network.Response.Error
import com.diphrogram.domain.network.Response.Loading
import com.diphrogram.domain.network.Response.Success
import com.diphrogram.domain.repository.video.GetVideoUseCase
import com.diphrogram.kolsa_test.presentation.common.BaseViewModel
import com.diphrogram.kolsa_test.presentation.common.ScreenState
import com.diphrogram.utils.ZERO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase,
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
                val videoData = response.data.toUI()
                setState {
                    copy(url = videoData.url)
                }
            }
            else -> Unit
        }
        setState {
            copy(
                screenState = if (response is Loading) ScreenState.LoadingProcess
                else ScreenState.LoadingComplete
            )
        }
    }

    @OptIn(UnstableApi::class)
    fun getTracksInfo(trackSelector: MappingTrackSelector): TracksInfo? {
        val items = mutableListOf(MOCK_QUALITY)
        val trackFormats = mutableListOf<Format>()

        val trackInfo = trackSelector.currentMappedTrackInfo ?: return null
        val rendererIndex = (Int.ZERO until trackInfo.rendererCount)
            .firstOrNull { trackInfo.getRendererType(it) == C.TRACK_TYPE_VIDEO } ?: return null
        val trackGroups = trackInfo.getTrackGroups(rendererIndex)
        for (trackIndex in Int.ZERO until trackGroups.length) {
            val group = trackGroups.get(trackIndex)
            for (groupIndex in Int.ZERO until group.length) {
                val format = group.getFormat(groupIndex)
                val label = "${format.height}$CHAR_P (${format.bitrate / 1000} $KB_PER_SECOND)"
                items.add(label)
                trackFormats.add(format)
            }
        }
        return TracksInfo(
            items = items,
            trackFormats = trackFormats,
            trackGroups = trackGroups
        )
    }
}

private const val MOCK_QUALITY = "240p"
private const val CHAR_P = "p"
private const val KB_PER_SECOND = "kbps"