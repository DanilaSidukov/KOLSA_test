package com.diphrogram.kolsa_test.presentation.video

import androidx.annotation.OptIn
import androidx.media3.common.Format
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.source.TrackGroupArray
import com.diphrogram.kolsa_test.presentation.common.ScreenState
import com.diphrogram.utils.EMPTY

data class VideoModelState(
    val error: String = String.EMPTY,
    val screenState: ScreenState = ScreenState.LoadingProcess,
    val url: String = String.EMPTY,
    val tracksInfo: TracksInfo = TracksInfo()
)

@OptIn(UnstableApi::class)
data class TracksInfo(
    val items: List<String> = emptyList(),
    val trackFormats: List<Format> = emptyList(),
    val trackGroups: TrackGroupArray = TrackGroupArray()
)
