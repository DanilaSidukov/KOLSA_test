package com.diphrogram.domain.repository.workouts

import com.diphrogram.domain.models.workouts.Workouts
import com.diphrogram.utils.network.Response
import kotlinx.coroutines.flow.Flow

interface GetWorkoutsUseCase {

    suspend operator fun invoke(): Flow<Response<List<Workouts>>>
}