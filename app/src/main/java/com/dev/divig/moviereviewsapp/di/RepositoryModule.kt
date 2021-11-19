package com.dev.divig.moviereviewsapp.di

import com.dev.divig.moviereviewsapp.data.local.datasource.LocalDataSource
import com.dev.divig.moviereviewsapp.data.network.datasource.movie.MovieApiDataSource
import com.dev.divig.moviereviewsapp.ui.detail.DetailRepository
import com.dev.divig.moviereviewsapp.ui.detail.bottomsheetreview.ReviewsBottomSheetRepository
import com.dev.divig.moviereviewsapp.ui.intro.IntroRepository
import com.dev.divig.moviereviewsapp.ui.main.favorite.FavoriteRepository
import com.dev.divig.moviereviewsapp.ui.main.movie.MovieRepository
import com.dev.divig.moviereviewsapp.ui.main.movie.bottomsheet.MovieBottomSheetRepository
import com.dev.divig.moviereviewsapp.ui.main.search.SearchRepository
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
    ): MovieRepository {
        return MovieRepository(localDataSource, movieApiDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        movieApiDataSource: MovieApiDataSource
    ): SearchRepository {
        return SearchRepository(movieApiDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        localDataSource: LocalDataSource
    ): FavoriteRepository {
        return FavoriteRepository(localDataSource)
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
    fun provideMovieBottomSheetRepository(
        localDataSource: LocalDataSource
    ): MovieBottomSheetRepository {
        return MovieBottomSheetRepository(localDataSource)
    }
}