package com.dev.divig.moviereviewsapp.ui.main

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.room.MoviesDatabase
import com.dev.divig.moviereviewsapp.data.local.room.datasource.MoviesDataSourceImpl
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityMainBinding
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
                showError(false, null)
                showContent(false)
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let {
                    if (it.isEmpty()) {
                        showError(true, getString(R.string.text_empty_movie))
                        showContent(false)
                    } else {
                        showError(false, null)
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
        Toast.makeText(this, movie.title, Toast.LENGTH_SHORT).show()
    }

    override fun setupBanner(movie: List<MovieEntity>) {
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[0].posterPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[1].posterPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[2].posterPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[3].posterPath, ""))
            add(SlideModel(Constant.BASE_URL_IMAGE + movie[4].posterPath, ""))
        }
        getViewBinding().imgSlider.setImageList(imageList, ScaleTypes.FIT)
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

    private fun navigateToAbout() {
        Toast.makeText(this, "open about page", Toast.LENGTH_SHORT).show()
    }

    private fun setupAppbar() {
        getViewBinding().topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_about -> {
                    Toast.makeText(this, "goto about page", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, MainActivity::class.java)
            context?.startActivity(intent)
        }
}