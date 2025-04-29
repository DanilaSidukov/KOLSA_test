package com.diphrogram.data.di.repository

import com.diphrogram.data.repository.VideoRepositoryImpl
import com.diphrogram.data.repository.WorkoutRepositoryImpl
import com.diphrogram.data.repository.video.GetVideoUseCaseImpl
import com.diphrogram.data.repository.workouts.GetWorkoutsUseCaseImpl
import com.diphrogram.domain.repository.VideoRepository
import com.diphrogram.domain.repository.WorkoutRepository
import com.diphrogram.domain.repository.video.GetVideoUseCase
import com.diphrogram.domain.repository.workouts.GetWorkoutsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindWorkoutRepository(impl: WorkoutRepositoryImpl): WorkoutRepository

    @Singleton
    @Binds
    abstract fun bindVideoRepository(impl: VideoRepositoryImpl): VideoRepository

    @Singleton
    @Binds
    abstract fun bindGetVideoUseCase(impl: GetVideoUseCaseImpl): GetVideoUseCase

    @Singleton
    @Binds
    abstract fun bindGetWorkoutsUseCase(impl: GetWorkoutsUseCaseImpl): GetWorkoutsUseCase
}