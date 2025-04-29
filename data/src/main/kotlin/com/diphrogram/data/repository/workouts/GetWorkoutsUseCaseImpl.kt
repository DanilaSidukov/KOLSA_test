package com.diphrogram.data.repository.workouts

import com.diphrogram.domain.models.workouts.WorkoutsDto
import com.diphrogram.domain.repository.WorkoutRepository
import com.diphrogram.domain.repository.workouts.GetWorkoutsUseCase
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GetWorkoutsUseCaseImpl @Inject constructor(
    private val repository: WorkoutRepository
): GetWorkoutsUseCase {

    override suspend fun invoke(): Flow<Response<List<WorkoutsDto>>> = withContext(Dispatchers.IO){
        repository.getWorkouts()
    }
}