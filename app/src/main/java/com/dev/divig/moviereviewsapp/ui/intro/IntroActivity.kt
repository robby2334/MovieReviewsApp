package com.dev.divig.moviereviewsapp.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.databinding.ActivityIntroBinding
import com.dev.divig.moviereviewsapp.ui.main.MainActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var introDataAdapter: IntroAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setIntroPage()
        supportActionBar?.hide()
        setupIndicator()
        setCurrentIndicator(0)
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.ic_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.ic_indicator_inactive
                    )
                )
            }
        }
    }

    private fun setupIndicator() {
        indicatorContainer = binding.llIndicatorsContainer
        val indicators = arrayOfNulls<ImageView>(introDataAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext, R.drawable.ic_indicator_inactive
                    )
                )

                it.layoutParams = layoutParams
                indicatorContainer.addView(it)
            }
        }
    }


    private fun setIntroPage() {
        introDataAdapter = IntroAdapter(
            listOf(
                IntroData(
                    introDataImage = R.drawable.image_test_intro,
                    introDataDesc = getString(R.string.desc_intro_1)
                ),
                IntroData(
                    introDataImage = R.drawable.image_test_intro,
                    introDataDesc = getString(R.string.desc_intro_2)
                ),
                IntroData(
                    introDataImage = R.drawable.image_test_intro,
                    introDataDesc = getString(R.string.desc_intro_3)
                )
            )
        )

        val introViewPager = binding.vpIntro
        introViewPager.adapter = introDataAdapter
        introViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                if (introViewPager.currentItem + 1 < introDataAdapter.itemCount) {
                    binding.mbBtnNext.text = getString(R.string.text_btn_next)

                } else {
                    binding.mbBtnNext.text = getString(R.string.text_navigate_to_main_page)
                }


            }
        })
        (introViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        binding.mbBtnNext.setOnClickListener {
            if (introViewPager.currentItem + 1 < introDataAdapter.itemCount) {
                introViewPager.currentItem += 1
                if (introViewPager.currentItem == 2) {
                    binding.mbBtnNext.text = getString(R.string.text_navigate_to_main_page)
                }
            } else {
                navigateToHomeActivity()
            }
        }

    }

    private fun navigateToHomeActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
//        intent.putExtra()
        startActivity(intent)
        finish()

    }

}