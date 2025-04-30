package com.diphrogram.kolsa_test.presentation.video

import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.utils.EMPTY

data class VideoModelState(
    val error: String = String.EMPTY,
    val screenState: ScreenState = ScreenState.LoadingProcess,
    val url: String = String.EMPTY
)
