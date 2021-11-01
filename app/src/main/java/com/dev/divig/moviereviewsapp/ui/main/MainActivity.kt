package com.dev.divig.moviereviewsapp.ui.main

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityMainBinding
import com.dev.divig.moviereviewsapp.ui.about.AboutActivity
import com.dev.divig.moviereviewsapp.ui.detail.DetailActivity
import com.dev.divig.moviereviewsapp.ui.main.adapter.MovieAdapter
import com.dev.divig.moviereviewsapp.utils.Constant
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityContract.Presenter>(
    ActivityMainBinding::inflate
), MainActivityContract.View {

    override fun initView() {
        getMovies()
        setupAppbar()
    }

    override fun initPresenter() {
        val dataSource = MoviesDataSourceImpl(MoviesDatabase.getInstance(this).moviesDao())
        val repository = MainActivityRepository(dataSource)
        setPresenter(MainActivityPresenter(this, repository))
    }

    override fun getMovies() {
        getPresenter().getMovies()
    }

    override fun onDataCallback(response: Resource<List<MovieEntity>>) {
        when (response) {
            is Resource.Loading -> {
                showLoading(true)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    if (it.isEmpty()) {
                        showError(true, getString(R.string.message_empty_movies))
                        showContent(false)
                    } else {
                        showContent(true)
                        setupRecyclerView(it)
                        setupBanner(it)
                    }
                }
            }
            is Resource.Error -> {
                showLoading(false)
                showError(true, response.message)
                showContent(false)
            }
        }
    }

    override fun setupRecyclerView(movies: List<MovieEntity>) {
        val adapter = MovieAdapter(
            MovieAdapter.OnClickListener {
                navigateToDetail(it)
            }
        )
        adapter.submitList(movies)
        getViewBinding().rvMovie.adapter = adapter
    }

    private fun navigateToDetail(movie: MovieEntity) {
        DetailActivity.startActivity(this, movie.id)
    }

    private fun navigateToAbout() {
        AboutActivity.startActivity(this)
    }

    override fun setupBanner(movie: List<MovieEntity>) {
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[0].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[2].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[4].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[6].backdropPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[8].backdropPath, ""))
        }
        getViewBinding().imgSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun setupAppbar() {
        getViewBinding().topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_about -> {
                    navigateToAbout()
                    true
                }
                else -> true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about -> {
                navigateToAbout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showContent(isContentVisible: Boolean) {
        super.showContent(isContentVisible)
        getViewBinding().rvMovie.isVisible = isContentVisible
    }

    override fun showLoading(isLoading: Boolean) {
        super.showLoading(isLoading)
        getViewBinding().sflMainPlaceholder.isVisible = isLoading
        if (isLoading) {
            getViewBinding().sflMainPlaceholder.startShimmer()
        } else {
            getViewBinding().sflMainPlaceholder.stopShimmer()
        }
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        super.showError(isErrorEnabled, msg)
        if (isErrorEnabled) Toast.makeText(this, msg.orEmpty(), Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, MainActivity::class.java)
            context?.startActivity(intent)
        }
    }
}