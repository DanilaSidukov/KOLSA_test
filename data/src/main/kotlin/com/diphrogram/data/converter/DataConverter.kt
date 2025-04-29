package com.diphrogram.data.converter

import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.data.models.workouts.WorkoutsItem
import com.diphrogram.domain.models.workouts.WorkoutsDto
import javax.inject.Inject

interface DataConverter {

    fun convertWorkoutsList(list: List<WorkoutsDto>): List<WorkoutsItem>
}

internal class DataConverterImpl @Inject constructor() : DataConverter {

    override fun convertWorkoutsList(list: List<WorkoutsDto>): List<WorkoutsItem> {
        val workoutsList = list.map { item ->
            WorkoutsItem(
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