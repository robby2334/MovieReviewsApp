package com.dev.divig.moviereviewsapp.ui.main.movie.model

import com.dev.divig.moviereviewsapp.data.local.model.MovieEntity

data class ParentEntity(
    var parentItemTitle: String,
    var childItemList: List<MovieEntity>
)