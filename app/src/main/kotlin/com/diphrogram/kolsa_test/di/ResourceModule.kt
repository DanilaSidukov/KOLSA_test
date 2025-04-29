package com.diphrogram.kolsa_test.di

import com.diphrogram.kolsa_test.common.data.ResourceProvider
import com.diphrogram.kolsa_test.common.data.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourceModule {

    @Singleton
    @Binds
    abstract fun bindResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}