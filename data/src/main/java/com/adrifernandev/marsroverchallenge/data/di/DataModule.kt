package com.adrifernandev.marsroverchallenge.data.di

import com.adrifernandev.marsroverchallenge.data.datasource.remote.RoverRemoteDataSource
import com.adrifernandev.marsroverchallenge.data.datasource.remote.RoverRemoteDataSourceImpl
import com.adrifernandev.marsroverchallenge.data.datasource.remote.service.RoverService
import com.adrifernandev.marsroverchallenge.data.datasource.remote.service.RoverServiceImpl
import com.adrifernandev.marsroverchallenge.data.repository.RoverRepositoryImpl
import com.adrifernandev.marsroverchallenge.domain.repository.RoverRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMarsRoverRepository(
        roverRemoteDataSource: RoverRemoteDataSource
    ): RoverRepository {
        return RoverRepositoryImpl(roverRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRoverRemoteDataSource(
        roverService: RoverService
    ): RoverRemoteDataSource {
        return RoverRemoteDataSourceImpl(roverService)
    }

    @Provides
    @Singleton
    fun provideRoverService(
        json: Json
    ): RoverService {
        return RoverServiceImpl(json)
    }

    @Provides
    @Singleton
    fun providesJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }
}