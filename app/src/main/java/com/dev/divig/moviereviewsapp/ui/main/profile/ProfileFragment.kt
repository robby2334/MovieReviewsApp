package com.dev.divig.moviereviewsapp.ui.main.profile

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.databinding.FragmentProfileBinding
import com.dev.divig.moviereviewsapp.ui.main.profile.dialogchangeprofileandlogout.ChangeProfileAndLogoutDialog
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {

    override val viewModelInstance: ProfileViewModel by viewModels()

    override fun initView() {
        setClickListeners()
    }

    override fun fetchData(username : String,email : String) {
        getViewBinding().tvName.text = username
        getViewBinding().tvEmail.text = email
    }

    override fun setClickListeners() {
        getViewBinding().ivBtnChangeProfile.setOnClickListener {
            showChangeProfileDialog()
        }
        getViewBinding().ivBtnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }


    override fun observeViewModel() {
        viewModel.getUserLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let {
                        fetchData(it.first,it.second)
                    }
                }
                is Resource.Error -> {
                    showLoading(false)
                    showContent(true)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun showChangeProfileDialog() {
        ChangeProfileAndLogoutDialog(Constant.TYPE_CHANGE_PROFILE_DIALOG) {}.show(
            childFragmentManager,
            null
        )
    }

    override fun showLogoutDialog() {
        ChangeProfileAndLogoutDialog(Constant.TYPE_LOGOUT_DIALOG){
            //goToLoginPage
            Toast.makeText(requireContext(), "Go To Login Page", Toast.LENGTH_SHORT).show()
            it?.dismiss()
        }.show(
            childFragmentManager,
            null
        )
    }
}