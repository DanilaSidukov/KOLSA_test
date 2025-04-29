package com.diphrogram.data.converter

import com.diphrogram.data.models.workouts.WorkoutItem
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.domain.models.workouts.Workouts
import javax.inject.Inject

interface DataConverter {

    fun convertWorkoutsList(list: List<Workouts>): List<WorkoutItem>
}

internal class DataConverterImpl @Inject constructor() : DataConverter {

    override fun convertWorkoutsList(list: List<Workouts>): List<WorkoutItem> {
        val workoutsList = list.map { item ->
            WorkoutItem(
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
}