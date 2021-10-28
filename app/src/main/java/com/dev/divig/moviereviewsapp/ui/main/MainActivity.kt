package com.dev.divig.moviereviewsapp.ui.main

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
import com.dev.divig.moviereviewsapp.utils.DataDummy
import java.util.ArrayList

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityContract.Presenter>(
    ActivityMainBinding::inflate
), MainActivityContract.View {

    override fun initView() {
        setupRecyclerView(DataDummy.getMovies())
//        insertMovies()
//        getMovies()
        setupBanner()
        setupAppbar()
    }

    override fun initPresenter() {
        val dataSource = MoviesDataSourceImpl(MoviesDatabase.getInstance(this).moviesDao())
        val repository = MainActivityRepository(dataSource)
        setPresenter(MainActivityPresenter(this, repository))
    }

    override fun setupRecyclerView(movies: List<MovieEntity>) {
        val adapter = MovieAdapter(
            MovieAdapter.OnClickListener {
                setClickListeners(it)
            }
        )
        adapter.submitList(movies)
        getViewBinding().rvMovie.adapter = adapter
    }

    override fun setClickListeners(movie: MovieEntity) {
        Toast.makeText(this, movie.title, Toast.LENGTH_SHORT).show()
    }

    override fun onInsertDataCallback(response: Resource<Pair<Boolean, Int>>) {
        when (response) {
            is Resource.Loading -> {
            }
            is Resource.Success -> {
                showLoading(false)
                response.data?.let { result ->
                    var msg: Pair<String, String>? = null
                    when (result.second) {
                        Constant.ACTION_INSERT -> {
                            msg = Pair("Insert Success", "Insert Failed")
                        }
                        Constant.ACTION_EDIT -> {
                            msg = Pair("Edit Success", "Edit Failed")
                        }
                        Constant.ACTION_DELETE -> {
                            msg = Pair("Delete Success", "Delete Failed")
                        }
                    }
                    if (result.first) {
                        Toast.makeText(this, msg?.first, Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, msg?.second, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            is Resource.Error -> {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }
        }
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

    override fun insertMovies() {
        getPresenter().insertMovies(DataDummy.getMovies())
    }

    override fun getMovies() {
        getPresenter().getMovies()
    }

    override fun setupBanner() {
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        val imageList = ArrayList<SlideModel>().apply {
            add(SlideModel("$baseUrl/lNyLSOKMMeUPr1RsL4KcRuIXwHt.jpg", ""))
            add(SlideModel("$baseUrl/2cdrhlf3hvvueGyQDx0u8jpWvQR.jpg", ""))
            add(SlideModel("$baseUrl/8Y43POKjjKDGI9MH89NW0NAzzp8.jpg", ""))
            add(SlideModel("$baseUrl/x6E7DS5ZcMoCITjkO0RiLLQ9Nb0.jpg", ""))
            add(SlideModel("$baseUrl/ux6gkGSKNFtp2NFYxwYFxVWdnGa.jpg", ""))
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
    
    private fun navigateToAbout(){
        Toast.makeText(this, "open about page", Toast.LENGTH_SHORT).show()
    }

    private fun setupAppbar(){
        getViewBinding().topAppBar.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.menu_about -> {
                    Toast.makeText(this, "goto about page", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
    }

}