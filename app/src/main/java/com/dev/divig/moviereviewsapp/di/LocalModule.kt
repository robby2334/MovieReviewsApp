package com.dev.divig.moviereviewsapp.di

import android.content.Context
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideMoviePreferences(@ApplicationContext context: Context): MoviePreference {
        return MoviePreference(context)
    }

    @Singleton
    @Provides
    fun provideMoviesDao(@ApplicationContext context: Context): MoviesDao {
        return MoviesDatabase.getInstance(context).moviesDao()
    }
}