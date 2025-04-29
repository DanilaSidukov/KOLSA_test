package com.diphrogram.domain.models.video

import com.diphrogram.utils.EMPTY
import com.diphrogram.utils.ZERO
import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("duration")
    val duration: String = String.EMPTY,
    @SerializedName("id")
    val id: Int = Int.ZERO,
    @SerializedName("link")
    val link: String = String.EMPTY
)