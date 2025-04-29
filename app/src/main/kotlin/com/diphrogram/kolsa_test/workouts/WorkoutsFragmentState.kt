package com.diphrogram.kolsa_test.workouts

import com.diphrogram.data.models.workouts.WorkoutItem
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.utils.EMPTY

data class WorkoutsFragmentState(
    val error: String = String.EMPTY,
    val workoutsList: List<WorkoutItem> = emptyList(),
    val filteredList: List<WorkoutItem> = emptyList(),
    val screenState: ScreenState = ScreenState.LoadingProcess,
    val currentSearchText: String = String.EMPTY,
    val currentFilterType: WorkoutType = WorkoutType.ALL
)
