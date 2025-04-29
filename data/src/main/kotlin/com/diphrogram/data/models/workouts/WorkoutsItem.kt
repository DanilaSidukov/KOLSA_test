package com.diphrogram.data.models.workouts

data class WorkoutsItem(
    val description: String?,
    val duration: String,
    val id: Int,
    val title: String,
    val type: WorkoutType
)