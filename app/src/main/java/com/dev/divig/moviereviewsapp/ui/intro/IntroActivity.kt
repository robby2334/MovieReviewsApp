package com.dev.divig.moviereviewsapp.ui.intro

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseActivity
import com.dev.divig.moviereviewsapp.databinding.ActivityIntroBinding
import com.dev.divig.moviereviewsapp.ui.intro.adapter.IntroAdapter
import com.dev.divig.moviereviewsapp.ui.intro.model.Intro
import com.dev.divig.moviereviewsapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity :
    BaseActivity<ActivityIntroBinding, IntroViewModel>(ActivityIntroBinding::inflate),
    IntroContract.View {
    private lateinit var introDataAdapter: IntroAdapter
    private lateinit var indicatorContainer: LinearLayout
    override val viewModelInstance: IntroViewModel by viewModels()

    override fun initView() {
        setIntro()
        supportActionBar?.hide()
        setupIndicator()
        setCurrentIndicator(0)
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
                    introDataDesc = getString(R.string.text_desc_intro_1)
                ),
                Intro(
                    introDataImage = R.drawable.img_intro_2,
                    introDataDesc = getString(R.string.text_desc_intro_2)
                ),
                Intro(
                    introDataImage = R.drawable.img_intro_3,
                    introDataDesc = getString(R.string.text_desc_intro_3)
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
                navigateToLoginPage()
            }
        }
    }

    override fun navigateToLoginPage() {
        getViewModel().setStateFirstRunApp()
        LoginActivity.startActivity(this)
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, IntroActivity::class.java)
            context?.startActivity(intent)
        }
    }
}