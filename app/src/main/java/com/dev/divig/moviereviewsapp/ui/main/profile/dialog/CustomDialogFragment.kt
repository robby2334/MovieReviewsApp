package com.dev.divig.moviereviewsapp.ui.main.profile.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dev.divig.moviereviewsapp.BuildConfig
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.BaseDialogFragment
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.databinding.FragmentCustomDialogBinding
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomDialogFragment(
    private val typeDialog: String,
    private val onCallback: (dialog: Dialog?) -> Unit
) :
    BaseDialogFragment<FragmentCustomDialogBinding, CustomDialogViewModel>(
        FragmentCustomDialogBinding::inflate
    ), CustomDialogContract.View {

    override val viewModelInstance: CustomDialogViewModel by viewModels()

    override fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        when (typeDialog) {
            Constant.TYPE_CHANGE_PROFILE_DIALOG -> showChangeProfileDialog()
            Constant.TYPE_ABOUT_DIALOG -> showAboutDialog()
            Constant.TYPE_LOGOUT_DIALOG -> showLogoutDialog()
        }
    }

    override fun observeViewModel() {
        getViewModel().getUserLiveData().observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    onCallback(dialog)
                }
                is Resource.Error -> {
                    showLoading(false)
                    showError(true, response.message)
                }
            }
        }
    }

    override fun showLoading(isLoading: Boolean) {
        getViewBinding().progressBar.isVisible = isLoading
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        showSnackBarError(getString(R.string.message_error_update))
    }

    private fun showChangeProfileDialog() {
        getUserData()
        getViewBinding().groupChangeProfileLayout.isVisible = true
        getViewBinding().btnPositive.text = getString(R.string.text_placeholder_update)
        getViewBinding().btnPositive.setOnClickListener {
            hideSoftKeyboard()
            if (checkFormValidation()) {
                if (checkInternetConnection()) {
                    updateUserData()
                } else {
                    showSnackBarError(getString(R.string.message_lost_connection))
                }
            }
        }
        getViewBinding().btnNegative.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun showAboutDialog() {
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)

        getViewBinding().groupAboutLayout.isVisible = true
        getViewBinding().btnPositive.isVisible = false
        getViewBinding().tvPlaceholderVersion.text =
            getString(R.string.text_placeholder_version, BuildConfig.VERSION_NAME)
        getViewBinding().btnNegative.text = getString(R.string.text_placeholder_close)
        getViewBinding().btnNegative.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun showLogoutDialog() {
        getViewBinding().groupLogoutLayout.isVisible = true
        getViewBinding().btnPositive.setOnClickListener {
            setLoginSession()
            onCallback(dialog)
        }
        getViewBinding().btnNegative.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun checkFormValidation(): Boolean {
        val username = getViewBinding().etUsername.text.toString().trim()
        val email = getViewBinding().etEmail.text.toString().trim()
        var isFormValid = true
        when {
            username.isEmpty() -> {
                isFormValid = false
                getViewBinding().tilUsername.isErrorEnabled = true
                getViewBinding().tilUsername.error = getString(R.string.error_username_empty)
            }
            else -> getViewBinding().tilUsername.isErrorEnabled = false
        }

        when {
            email.isEmpty() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                getViewBinding().tilEmail.isErrorEnabled = true
                getViewBinding().tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                getViewBinding().tilEmail.isErrorEnabled = false
            }
        }

        return isFormValid
    }

    override fun setLoginSession() {
        getViewModel().setLoginSession()
    }

    override fun getUserData() {
        val response = getViewModel().getUserData()
        fetchData(response)
    }

    override fun updateUserData() {
        val username = getViewBinding().etUsername.text.toString().trim()
        val email = getViewBinding().etEmail.text.toString().trim()

        getViewModel().updateUserData(username, email)
    }

    override fun fetchData(data: UserEntity) {
        getViewBinding().etUsername.setText(data.username)
        getViewBinding().etEmail.setText(data.email)
    }
}