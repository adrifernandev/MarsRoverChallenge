package com.adrifernandev.marsroverchallenge.domain.di

import com.adrifernandev.marsroverchallenge.domain.usecases.NavigateRoverUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideNavigateRoverUseCase(): NavigateRoverUseCase {
        return NavigateRoverUseCase()
    }
}