package com.diphrogram.data.converter

import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.data.models.workouts.WorkoutItem
import com.diphrogram.domain.models.workouts.WorkoutsDto
import javax.inject.Inject

interface DataConverter {

    fun convertWorkoutsList(list: List<WorkoutsDto>): List<WorkoutItem>
}

internal class DataConverterImpl @Inject constructor() : DataConverter {

    override fun convertWorkoutsList(list: List<WorkoutsDto>): List<WorkoutItem> {
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

    private fun getWorkoutType(type: Int): WorkoutType {
        val workoutType = WorkoutType.getValues(type)
        return workoutType
    }
}