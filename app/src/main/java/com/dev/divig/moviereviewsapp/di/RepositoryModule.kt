package com.dev.divig.moviereviewsapp.di

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.auth.AuthApiDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSource
import com.dev.divig.moviereviewsapp.ui.detail.DetailRepository
import com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.ReviewsBottomSheetRepository
import com.dev.divig.moviereviewsapp.ui.intro.IntroRepository
import com.dev.divig.moviereviewsapp.ui.main.movie.MovieFragmentRepository
import com.dev.divig.moviereviewsapp.ui.main.profile.ProfileRepository
import com.dev.divig.moviereviewsapp.ui.splashscreen.SplashAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSplashScreenRepository(
        localDataSource: LocalDataSource
    ): SplashAppRepository {
        return SplashAppRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideIntroRepository(
        localDataSource: LocalDataSource
    ): IntroRepository {
        return IntroRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieFragmentRepository(
        localDataSource: LocalDataSource,
        movieApiDataSource: MovieApiDataSource
    ): MovieFragmentRepository {
        return MovieFragmentRepository(localDataSource, movieApiDataSource)
    }

    @Provides
    @Singleton
    fun provideDetailRepository(
        localDataSource: LocalDataSource,
        movieApiDataSource: MovieApiDataSource
    ): DetailRepository {
        return DetailRepository(localDataSource, movieApiDataSource)
    }

    @Provides
    @Singleton
    fun provideReviewsBottomSheetRepository(
        localDataSource: LocalDataSource
    ): ReviewsBottomSheetRepository {
        return ReviewsBottomSheetRepository(localDataSource)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        authApiDataSource: AuthApiDataSource,
        localDataSource: LocalDataSource
    ): ProfileRepository {
        return ProfileRepository(authApiDataSource,localDataSource)
    }
}