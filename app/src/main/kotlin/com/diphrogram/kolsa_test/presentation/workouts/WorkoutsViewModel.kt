package com.diphrogram.kolsa_test.presentation.workouts

import androidx.lifecycle.viewModelScope
import com.diphrogram.data.converter.DataConverter
import com.diphrogram.data.models.workouts.WorkoutType
import com.diphrogram.domain.models.workouts.Workouts
import com.diphrogram.domain.network.Response
import com.diphrogram.domain.network.Response.Error
import com.diphrogram.domain.network.Response.Loading
import com.diphrogram.domain.network.Response.Success
import com.diphrogram.domain.repository.workouts.GetWorkoutsUseCase
import com.diphrogram.kolsa_test.common.data.ResourceProvider
import com.diphrogram.kolsa_test.presentation.common.BaseViewModel
import com.diphrogram.kolsa_test.presentation.common.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor (
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val dataConverter: DataConverter,
    private val resourceProvider: ResourceProvider
): BaseViewModel<WorkoutsModelState>() {

    override fun createInitialState() = WorkoutsModelState()

    init {
        getWorkouts()
    }

    private fun getWorkouts() = viewModelScope.launch {
        getWorkoutsUseCase().collect(::handleWorkoutResponse)
    }

    private fun handleWorkoutResponse(response: Response<List<Workouts>>) {
        when(response) {
            is Error -> {
                setState {
                    copy(error = response.message)
                }
            }
            is Success -> {
                val result = response.data
                if (result.isNotEmpty()) {
                    val workoutsList = dataConverter.convertWorkoutsList(result)
                    setState {
                        copy(
                            workoutsList = workoutsList,
                            filteredList = workoutsList
                        )
                    }
                } else {
                    setState {
                        copy(error = resourceProvider.emptyResult)
                    }
                }
            }
            else -> Unit
        }
        setState {
            copy(
                screenState = if (response is Loading) ScreenState.LoadingProcess
                else ScreenState.LoadingComplete
            )
        }
    }

    fun filterListByTitle(title: String) {
        setState {
            copy(currentSearchText = title)
        }
        applyFilters()
    }

    fun filterListByType(type: WorkoutType) {
        setState {
            copy(currentFilterType = type)
        }
        applyFilters()
    }

    private fun applyFilters() {
        val originalList = state.value.workoutsList
        val searchText = state.value.currentSearchText
        val filterType = state.value.currentFilterType

        val filtered = originalList
            .filter { it.title.contains(searchText, ignoreCase = true) }
            .filter { filterType == WorkoutType.ALL || it.type == filterType }

        setState {
            copy(filteredList = filtered)
        }
    }
}