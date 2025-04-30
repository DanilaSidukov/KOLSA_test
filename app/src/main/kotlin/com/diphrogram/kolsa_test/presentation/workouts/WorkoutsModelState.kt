package com.diphrogram.kolsa_test.presentation.workouts

import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.data.models.workouts.WorkoutUI
import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.utils.EMPTY

data class WorkoutsModelState(
    val error: String = String.EMPTY,
    val workoutsList: List<WorkoutUI> = emptyList(),
    val filteredList: List<WorkoutUI> = emptyList(),
    val screenState: ScreenState = ScreenState.LoadingProcess,
    val currentSearchText: String = String.EMPTY,
    val currentFilterType: WorkoutType = WorkoutType.ALL
)
