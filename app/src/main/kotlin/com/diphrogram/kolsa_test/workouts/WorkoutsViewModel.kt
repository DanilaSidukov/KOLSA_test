package com.diphrogram.kolsa_test.workouts

import androidx.lifecycle.viewModelScope
import com.diphrogram.data.converter.DataConverter
import com.diphrogram.domain.models.workouts.WorkoutsDto
import com.diphrogram.domain.repository.workouts.GetWorkoutsUseCase
import com.diphrogram.kolsa_test.common.BaseViewModel
import com.diphrogram.kolsa_test.common.ScreenState
import com.diphrogram.kolsa_test.common.data.ResourceProvider
import com.diphrogram.utils.network.Response
import com.diphrogram.utils.network.Response.Error
import com.diphrogram.utils.network.Response.Loading
import com.diphrogram.utils.network.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor (
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
    private val dataConverter: DataConverter,
    private val resourceProvider: ResourceProvider
): BaseViewModel<WorkoutsFragmentState>() {

    override fun createInitialState() = WorkoutsFragmentState()

    init {
        getWorkouts()
    }

    private fun getWorkouts() = viewModelScope.launch {
        getWorkoutsUseCase().collect(::handleWorkoutResponse)
    }

    private fun handleWorkoutResponse(response: Response<List<WorkoutsDto>>) {
        when(response) {
            is Error -> {
                setState {
                    copy(error = response.message ?: resourceProvider.somethingWentWrong)
                }
            }
            is Success -> {
                val result = response.data
                if (!result.isNullOrEmpty()) {
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

    fun filterListByTitle(text: String) {
        setState {
            if (text.isBlank()) {
                copy(filteredList = workoutsList)
            } else {
                copy(
                    filteredList = workoutsList.filter {
                        it.title.contains(text, ignoreCase = true)
                    }
                )
            }
        }
    }
}