package com.adrifernandev.marsroverchallenge.data.di

import com.adrifernandev.marsroverchallenge.data.repository.RoverRepositoryImpl
import com.adrifernandev.marsroverchallenge.domain.repository.RoverRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMarsRoverRepository(): RoverRepository {
        return RoverRepositoryImpl()
    }
}