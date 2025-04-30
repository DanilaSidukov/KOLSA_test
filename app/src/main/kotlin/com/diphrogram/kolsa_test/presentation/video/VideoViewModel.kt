package com.diphrogram.kolsa_test.presentation.video

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.media3.common.C
import androidx.media3.common.Format
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.diphrogram.data.converter.DataConverter
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

@UnstableApi
@HiltViewModel
class VideoViewModel @Inject constructor(
    private val getVideoUseCase: GetVideoUseCase,
    private val dataConverter: DataConverter,
): BaseViewModel<VideoModelState>() {

    override fun createInitialState() = VideoModelState()

    private lateinit var trackSelector: DefaultTrackSelector

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

    fun getTracksInfo(): TracksInfo? {
        val items = mutableListOf(MOCK_QUALITY)
        val trackFormats = mutableListOf<Format>()
        val mappedTrackInfo = if (trackSelector.currentMappedTrackInfo != null) {
            val trackInfo = trackSelector.currentMappedTrackInfo!!
            val rendererIndex = (Int.ZERO until trackInfo.rendererCount)
                .firstOrNull { trackInfo.getRendererType(it) == C.TRACK_TYPE_VIDEO }

            if (rendererIndex != null) {
                val trackGroups = trackInfo.getTrackGroups(rendererIndex)
                for (trackIndex in Int.ZERO until trackGroups.length) {
                    val group = trackGroups.get(trackIndex)
                    for (groupIndex in Int.ZERO until group.length) {
                        val format = group.getFormat(groupIndex)
                        val label = "${format.height}p (${format.bitrate / 1000} kbps)"
                        items.add(label)
                        trackFormats.add(format)
                    }
                }
                TracksInfo(
                    items = items,
                    trackFormats = trackFormats,
                    trackGroups = trackGroups
                )
            } else return null
        } else null
        return mappedTrackInfo
    }

    fun buildExoPlayer(context: Context): ExoPlayer {
        trackSelector = DefaultTrackSelector(context)
        trackSelector.setParameters(
            trackSelector.buildUponParameters()
                .setMaxVideoSizeSd()
        )

        val renderFactory = DefaultRenderersFactory(context)
            .setEnableDecoderFallback(true)

        val player = ExoPlayer.Builder(context)
            .setRenderersFactory(renderFactory)
            .setTrackSelector(trackSelector)
            .setSeekBackIncrementMs(SEEK_VALUE)
            .setSeekForwardIncrementMs(SEEK_VALUE)
            .build()

        player.playbackParameters = PlaybackParameters(1f)
        return player
    }

    fun setTrackSelector(tracksInfo: TracksInfo, index: Int) {
        val parameters = trackSelector.buildUponParameters()
            .setOverrideForType(TrackSelectionOverride(tracksInfo.trackGroups[index], index))
            .build()
        trackSelector.parameters = parameters
    }
}

private const val MOCK_QUALITY = "240p"
private const val SEEK_VALUE = 3000L