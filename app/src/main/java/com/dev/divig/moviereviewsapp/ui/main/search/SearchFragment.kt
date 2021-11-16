package com.dev.divig.moviereviewsapp.ui.main.search

import android.widget.Toast
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun initView() {
        Toast.makeText(requireContext(), getString(R.string.text_title_search), Toast.LENGTH_SHORT)
            .show()
    }
}