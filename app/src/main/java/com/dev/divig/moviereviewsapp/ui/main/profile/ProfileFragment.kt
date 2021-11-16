package com.dev.divig.moviereviewsapp.ui.main.profile

import android.widget.Toast
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.databinding.FragmentProfileBinding

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun initView() {
        Toast.makeText(requireContext(), getString(R.string.text_title_profile), Toast.LENGTH_SHORT).show()
    }
}