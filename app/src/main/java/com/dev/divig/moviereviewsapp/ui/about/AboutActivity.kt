package com.dev.divig.moviereviewsapp.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dev.divig.moviereviewsapp.BuildConfig
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAbout)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.tvVersion.text =
            getString(R.string.text_placeholder_version, BuildConfig.VERSION_NAME)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, AboutActivity::class.java)
            context?.startActivity(intent)
        }
    }
}