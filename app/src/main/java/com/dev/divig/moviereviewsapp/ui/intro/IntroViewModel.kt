package com.dev.divig.moviereviewsapp.ui.intro

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(private val repository: IntroRepository) : ViewModel(),
    IntroContract.ViewModel {

    override fun setStateFirstRunApp() {
        repository.setStateFirstRunApp(false)
    }
}