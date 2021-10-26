package com.dev.divig.moviereviewsapp.data.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dev.divig.moviereviewsapp.databinding.ActivityDetailBinding

class DetailMovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


    }
}