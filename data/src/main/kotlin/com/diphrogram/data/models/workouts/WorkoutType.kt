package com.diphrogram.data.models.workouts

enum class WorkoutType(val type: Int?) {
    Workout(1),
    Live(2),
    ExerciseSet(3),
    Another(null);

    companion object {
        fun getValues(data: Int): WorkoutType {
            return enumValues<WorkoutType>().firstOrNull { data == it.type } ?: Another
        }
    }
}