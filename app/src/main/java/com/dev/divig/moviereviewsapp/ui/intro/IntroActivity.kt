package com.dev.divig.moviereviewsapp.ui.intro

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.data.local.preference.MoviePreference
import com.dev.divig.moviereviewsapp.databinding.ActivityIntroBinding
import com.dev.divig.moviereviewsapp.ui.intro.adapter.IntroAdapter
import com.dev.divig.moviereviewsapp.ui.intro.model.Intro

class IntroActivity :
    BaseActivity<ActivityIntroBinding, IntroContract.Presenter>(ActivityIntroBinding::inflate),
    IntroContract.View {
    private lateinit var introDataAdapter: IntroAdapter
    private lateinit var indicatorContainer: LinearLayout

    override fun initView() {
        setIntro()
        supportActionBar?.hide()
        setupIndicator()
        setCurrentIndicator(0)
    }

    override fun initPresenter() {
        val repository = IntroRepository(MoviePreference(this))
        setPresenter(IntroPresenter(repository))
    }

    override fun setCurrentIndicator(position: Int) {
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

    override fun setupIndicator() {
        indicatorContainer = getViewBinding().llIndicatorsContainer
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

    override fun setIntro() {
        introDataAdapter = IntroAdapter(
            listOf(
                Intro(
                    introDataImage = R.drawable.img_intro_1,
                    introDataDesc = getString(R.string.desc_intro_1)
                ),
                Intro(
                    introDataImage = R.drawable.img_intro_2,
                    introDataDesc = getString(R.string.desc_intro_2)
                ),
                Intro(
                    introDataImage = R.drawable.img_intro_3,
                    introDataDesc = getString(R.string.desc_intro_3)
                )
            )
        )

        val introViewPager = getViewBinding().vpIntro
        introViewPager.adapter = introDataAdapter
        introViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                if (introViewPager.currentItem + 1 < introDataAdapter.itemCount) {
                    getViewBinding().mbBtnNext.text = getString(R.string.text_placeholder_next)
                } else {
                    getViewBinding().mbBtnNext.text = getString(R.string.text_navigate_to_main_page)
                }
            }
        })
        (introViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        getViewBinding().mbBtnNext.setOnClickListener {
            if (introViewPager.currentItem + 1 < introDataAdapter.itemCount) {
                introViewPager.currentItem += 1
                if (introViewPager.currentItem == 2) {
                    getViewBinding().mbBtnNext.text = getString(R.string.text_navigate_to_main_page)
                }
            } else {
                navigateToMainPage()
            }
        }
    }

    override fun navigateToMainPage() {
        getPresenter().setStateFirstRunApp()
        Toast.makeText(this, "Navigate to Main Page", Toast.LENGTH_SHORT).show()
        // TODO: 27-Oct-21 Navigate to MainPage
    }
}