package com.diphrogram.data.converter

import com.diphrogram.data.BuildConfig
import com.diphrogram.data.models.video.VideoUI
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.data.models.workouts.WorkoutUI
import com.diphrogram.domain.models.video.Video
import com.diphrogram.domain.models.workouts.Workouts
import javax.inject.Inject

interface DataConverter {

    fun convertWorkoutsList(list: List<Workouts>): List<WorkoutUI>

    fun convertVideoData(video: Video): VideoUI
}

internal class DataConverterImpl @Inject constructor() : DataConverter {

    override fun convertWorkoutsList(list: List<Workouts>): List<WorkoutUI> {
        val workoutsList = list.map { item ->
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

    override fun convertVideoData(video: Video): VideoUI {
        val baseUrl = BuildConfig.BASE_URL.dropLast(1)
        return VideoUI(
            id = video.id,
            url = "$baseUrl${video.link}"
        )
    }

    private fun getWorkoutType(type: Int): WorkoutType = when (type) {
        0 -> WorkoutType.ALL
        1 -> WorkoutType.WORKOUT
        2 -> WorkoutType.LIVE
        3 -> WorkoutType.EXERCISE_SET
        else -> WorkoutType.ANOTHER
    }
}