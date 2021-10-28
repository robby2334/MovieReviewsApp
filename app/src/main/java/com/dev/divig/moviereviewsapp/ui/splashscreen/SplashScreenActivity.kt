package com.dev.divig.moviereviewsapp.ui.splashscreen

import android.os.CountDownTimer
import android.widget.Toast
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.databinding.ActivitySplashScreenBinding
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.DataDummy

class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding, SplashScreenContract.Presenter>(
        ActivitySplashScreenBinding::inflate
    ),
    SplashScreenContract.View {
    private var timer: CountDownTimer? = null

    override fun initView() {
        insertMovies()
        supportActionBar?.hide()
    }

    override fun initPresenter() {
        val localDataSource = MoviesDataSourceImpl(MoviesDatabase.getInstance(this).moviesDao())
        val moviePreference = MoviePreference(this)
        val repository = SplashScreenRepository(localDataSource, moviePreference)
        setPresenter(SplashScreenPresenter(this, repository))
    }

    override fun onDataCallback(response: Resource<Boolean>) {
        when (response) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                response.data?.let { result ->
                    if (result) {
                        setSplashScreenTimer()
                    } else {
                        Toast.makeText(
                            this,
                            "There was a problem loading the data, please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                        setSplashScreenTimer()
                    }
                }
            }
            is Resource.Error -> {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertMovies() {
        getPresenter().insertMovies(DataDummy.getMovies())
    }

    private fun setSplashScreenTimer() {
        timer = object : CountDownTimer(Constant.THREE_SECOND, Constant.ONE_SECOND) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                checkStateFirstRunApp()
            }
        }
        timer?.start()
    }

    private fun checkStateFirstRunApp() {
        getPresenter().checkStateFirstRunApp()
    }

    override fun navigateToIntroPage() {
        // TODO: 27-Oct-21 Navigate to IntroPage
    }

    override fun navigateToMainPage() {
        // TODO: 27-Oct-21 Navigate to MainPage
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            timer = null
        }
    }
}