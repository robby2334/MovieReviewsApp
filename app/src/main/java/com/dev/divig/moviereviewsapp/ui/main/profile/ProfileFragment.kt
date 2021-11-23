package com.dev.divig.moviereviewsapp.ui.main.profile

import androidx.fragment.app.viewModels
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.databinding.FragmentProfileBinding
import com.dev.divig.moviereviewsapp.ui.login.LoginActivity
import com.dev.divig.moviereviewsapp.ui.main.profile.dialog.CustomDialogFragment
import com.dev.divig.moviereviewsapp.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate),
    ProfileContract.View {

    override val viewModelInstance: ProfileViewModel by viewModels()

    override fun initView() {
        getUserData()
        setClickListeners()
    }

    override fun setClickListeners() {
        getViewBinding().trBtnChangeProfile.setOnClickListener {
            showChangeProfileDialog()
        }
        getViewBinding().trBtnAbout.setOnClickListener {
            showAboutDialog()
        }
        getViewBinding().trBtnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    override fun observeViewModel() {
        getViewModel().getUserLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                    showContent(false)
                }
                is Resource.Success -> {
                    showLoading(false)
                    showContent(true)
                    response.data?.let {
                        fetchData(it.first, it.second)
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

    override fun fetchData(username: String, email: String) {
        getViewBinding().tvUsername.text = username
        getViewBinding().tvEmail.text = email
    }

    override fun getUserData() {
        getViewModel().getUserData()
    }

    override fun showChangeProfileDialog() {
        CustomDialogFragment(Constant.TYPE_CHANGE_PROFILE_DIALOG) {
            showSnackBarSuccess(getString(R.string.message_success_update))
            getUserData()
            it?.dismiss()
        }.show(
            childFragmentManager,
            null
        )
    }

    override fun showAboutDialog() {
        CustomDialogFragment(Constant.TYPE_ABOUT_DIALOG) {}.show(
            childFragmentManager,
            null
        )
    }

    override fun showLogoutDialog() {
        CustomDialogFragment(Constant.TYPE_LOGOUT_DIALOG) {
            it?.dismiss()
            LoginActivity.startActivity(requireContext())
        }.show(
            childFragmentManager,
            null
        )
    }
}