package com.diphrogram.data.converter

import com.diphrogram.data.BuildConfig
import com.diphrogram.data.models.video.VideoUI
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.data.models.workouts.WorkoutUI
import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.models.workouts.Workouts

fun List<Workouts>.toUIList(): List<WorkoutUI> {
    val workoutsList = this.map { item ->
        WorkoutUI(
            id = item.id,
            title = item.title,
            type = getWorkoutType(item.type),
            description = item.description,
            duration = item.duration
        )
    }
    return workoutsList
}
private fun getWorkoutType(type: Int): WorkoutType = when (type) {
    0 -> WorkoutType.ALL
    1 -> WorkoutType.WORKOUT
    2 -> WorkoutType.LIVE
    3 -> WorkoutType.EXERCISE_SET
    else -> WorkoutType.ANOTHER
}

fun Video.toUI(): VideoUI {
    val baseUrl = BuildConfig.BASE_URL.dropLast(1)
    return VideoUI(
        id = this.id,
        url = "$baseUrl${this.link}"
    )
}