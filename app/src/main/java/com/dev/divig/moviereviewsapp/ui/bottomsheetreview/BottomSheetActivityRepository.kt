package com.dev.divig.moviereviewsapp.ui.bottomsheetreview

import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSource
import com.dev.divig.moviereviewsapp.data.model.ReviewEntity
import javax.sql.CommonDataSource

class BottomSheetActivityRepository(
    private val dataSource: MoviesDataSource
) : BottomSheetActivityContract.Repository{
    override suspend fun getAllReviews(): List<ReviewEntity> {
        TODO("Not yet implemented")
    }
}