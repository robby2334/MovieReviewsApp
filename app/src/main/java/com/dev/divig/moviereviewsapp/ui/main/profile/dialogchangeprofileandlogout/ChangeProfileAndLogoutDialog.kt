package com.dev.divig.moviereviewsapp.ui.main.profile.dialogchangeprofileandlogout

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.base.model.Resource
import com.dev.divig.moviereviewsapp.databinding.FragmentChangeProfileAndLogoutBinding
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.StringUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangeProfileAndLogoutDialog(
    private val typeDialog: String,
    private val onClickOkay: (dialog: Dialog?) -> Unit
) :
    DialogFragment(), ChangeProfileAndLogoutContract.View {

    private lateinit var binding: FragmentChangeProfileAndLogoutBinding
    private val viewModel: ChangeProfileAndLogoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeProfileAndLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun initView() {
        setTransparentDialog()

        when (typeDialog) {
            Constant.TYPE_CHANGE_PROFILE_DIALOG -> showChangeProfileDialog()
            Constant.TYPE_LOGOUT_DIALOG -> showLogoutDialog()
        }
    }

    override fun observeViewModel() {
        viewModel.getUserLiveData().observe(viewLifecycleOwner){ response ->
            when (response) {
                is Resource.Loading -> {
//                    showLoading(true)
//                    showContent(false)
                }
                is Resource.Success -> {
//                    showLoading(false)
//                    showContent(true)

                }
                is Resource.Error -> {
//                    showLoading(false)
//                    showContent(true)
//                    showError(true, response.message)
                }
            }
        }
    }

    override fun showContent(isContentVisible: Boolean) {

    }

    override fun showLoading(isLoading: Boolean) {

    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
    }


    private fun showLogoutDialog() {
        binding.groupLogoutLayout.isVisible = true
        binding.btnOk.setOnClickListener {
            setLoginSession()
            onClickOkay(dialog)
        }
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun showChangeProfileDialog() {
        binding.groupChangeProfileLayout.isVisible = true
        binding.btnSave.setOnClickListener {
            dialog?.dismiss()
        }
    }

    fun setTransparentDialog() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun checkFormValidation(): Boolean {

        val email = binding.etEmail.text.toString()
        var isFormValid = true
        //for checking is email empty
        when {
            email.isEmpty() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error = getString(R.string.error_email_invalid)
            }
            else -> {
                binding.tilEmail.isErrorEnabled = false
            }
        }
        return isFormValid
    }

    override fun setLoginSession() {
        viewModel.setLoginSession()
    }

    override fun updateUserData() {
        val username = binding.etUserName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        viewModel.updateUserData(username,email)
    }
}