package com.dev.divig.moviereviewsapp.ui.main

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.data.model.MovieEntity
import com.dev.divig.moviereviewsapp.databinding.ActivityMainBinding
import com.dev.divig.moviereviewsapp.ui.main.adapter.MovieAdapter
import com.dev.divig.moviereviewsapp.utils.DataDummy.getMovies

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityContract.Presenter>(
    ActivityMainBinding::inflate
), MainActivityContract.View {

    override fun initView() {
        setupRecyclerView()
    }

    override fun initPresenter() {
        val presenter = MainActivityPresenter(this)
        setPresenter(presenter)
    }

    override fun setupRecyclerView() {
        val adapter = MovieAdapter(
            MovieAdapter.OnClickListener {
                setClickListeners(it)
            }
        )
        adapter.submitList(getMovies())
        getViewBinding().rvMovie.adapter = adapter
    }

    override fun setClickListeners(movie: MovieEntity) {
        Toast.makeText(this, movie.title, Toast.LENGTH_SHORT).show()
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
}