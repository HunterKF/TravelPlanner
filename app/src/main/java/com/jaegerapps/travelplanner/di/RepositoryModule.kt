package com.jaegerapps.travelplanner.di

import com.jaegerapps.travelplanner.data.GptRepositoryImpl
import com.jaegerapps.travelplanner.domain.GptRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGptRepository(
        gptRepositoryImpl: GptRepositoryImpl
    ): GptRepository
}