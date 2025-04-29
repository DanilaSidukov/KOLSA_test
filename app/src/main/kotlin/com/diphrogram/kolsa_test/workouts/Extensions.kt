package com.diphrogram.kolsa_test.workouts

import android.content.Context
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.kolsa_test.R

fun getWorkoutTypeValue(context: Context, workoutType: WorkoutType): String {
    return with(context) {
        when (workoutType) {
            WorkoutType.WORKOUT -> getString(R.string.workout)
            WorkoutType.LIVE -> getString(R.string.live)
            WorkoutType.EXERCISE_SET -> getString(R.string.set)
            WorkoutType.ANOTHER -> getString(R.string.another)
            WorkoutType.ALL -> getString(R.string.all)
        }
    }
}

fun getWorkoutType(context: Context, value: String): WorkoutType {
    return with(context){
        when(value) {
            getString(R.string.workout) -> WorkoutType.WORKOUT
            getString(R.string.live) -> WorkoutType.LIVE
            getString(R.string.set) -> WorkoutType.EXERCISE_SET
            getString(R.string.another) -> WorkoutType.ANOTHER
            else -> WorkoutType.ALL
        }
    }
}