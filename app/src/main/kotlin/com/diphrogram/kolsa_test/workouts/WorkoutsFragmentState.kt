package com.diphrogram.kolsa_test.workouts

import com.diphrogram.data.models.workouts.WorkoutsItem
import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.utils.EMPTY

data class WorkoutsFragmentState(
    val error: String = String.EMPTY,
    val workoutsList: List<WorkoutsItem> = emptyList(),
    val screenState: ScreenState = ScreenState.LoadingProcess
)
