package com.diphrogram.data.di.converter

import com.diphrogram.data.converter.DataConverter
import com.diphrogram.data.converter.DataConverterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConverterModule {

    @Singleton
    @Binds
    abstract fun bindDataConverter(impl: DataConverterImpl): DataConverter
}