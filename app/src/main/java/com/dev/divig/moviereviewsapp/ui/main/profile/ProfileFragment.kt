package com.dev.divig.moviereviewsapp.ui.main.profile

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.databinding.FragmentProfileBinding

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {

    override val viewModelInstance: ProfileViewModel by viewModels()

    override fun initView() {
        Toast.makeText(requireContext(), getString(R.string.text_title_profile), Toast.LENGTH_SHORT)
            .show()
    }
}