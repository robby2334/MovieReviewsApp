package com.dev.divig.moviereviewsapp.ui.main.favorite

import android.widget.Toast
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.databinding.FragmentFavoriteBinding

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    override fun initView() {
        Toast.makeText(requireContext(), getString(R.string.text_title_favorite), Toast.LENGTH_SHORT).show()
    }
}