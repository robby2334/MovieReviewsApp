package com.dev.divig.moviereviewsapp.di

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSourceImpl
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.dao.MoviesDao
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSourceImpl
import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSourceImpl
import com.dev.divig.moviereviewsapp.data.network.services.AuthApiService
import com.dev.divig.moviereviewsapp.data.network.services.MovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(
        moviePreference: MoviePreference,
        moviesDao: MoviesDao
    ): LocalDataSource {
        return LocalDataSourceImpl(moviePreference, moviesDao)
    }

    @Singleton
    @Provides
    fun provideAuthDataSource(authApiService: AuthApiService): AuthApiDataSource {
        return AuthApiDataSourceImpl(authApiService)
    }

    @Singleton
    @Provides
    fun provideMovieDataSource(movieApiService: MovieApiService): MovieApiDataSource {
        return MovieApiDataSourceImpl(movieApiService)
    }
}