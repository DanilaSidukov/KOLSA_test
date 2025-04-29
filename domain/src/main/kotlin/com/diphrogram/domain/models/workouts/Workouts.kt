package com.diphrogram.domain.models.workouts

import com.diphrogram.utils.EMPTY
import com.diphrogram.utils.ZERO
import com.google.gson.annotations.SerializedName

data class Workouts(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("duration")
    val duration: String = String.EMPTY,
    @SerializedName("id")
    val id: Int = Int.ZERO,
    @SerializedName("title")
    val title: String = String.EMPTY,
    @SerializedName("type")
    val type: Int = Int.ZERO
)