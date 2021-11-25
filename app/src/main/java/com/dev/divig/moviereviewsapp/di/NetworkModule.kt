package com.dev.divig.moviereviewsapp.di

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.services.AuthApiService
import com.dev.divig.moviereviewsapp.data.network.services.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideAuthApiServices(localDataSource: LocalDataSource): AuthApiService {
        return AuthApiService.invoke(localDataSource)
    }

    @Singleton
    @Provides
    fun provideMovieApiServices(): MovieApiService {
        return MovieApiService.invoke()
    }
}