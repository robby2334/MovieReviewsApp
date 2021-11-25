package com.dev.divig.moviereviewsapp.ui.login.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dev.divig.moviereviewsapp.R
import com.dev.divig.moviereviewsapp.data.local.model.UserEntity
import com.dev.divig.moviereviewsapp.databinding.ItemLoginRegisterBinding
import com.dev.divig.moviereviewsapp.utils.Constant
import com.dev.divig.moviereviewsapp.utils.StringUtils

class LoginAdapter(
    private val itemList: List<String>,
    private val itemClick: (Pair<Int, UserEntity>) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ItemLoginRegisterBinding

    fun clearFieldFormRegister() {
        clearFieldForm()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) Constant.ITEM_TYPE_LOGIN else Constant.ITEM_TYPE_REGISTER
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constant.ITEM_TYPE_LOGIN) {
            binding =
                ItemLoginRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoginViewHolder(binding, itemClick)
        } else {
            binding =
                ItemLoginRegisterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            RegisterViewHolder(binding, itemClick)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LoginViewHolder) {
            holder.bind(itemList[position])
        } else if (holder is RegisterViewHolder) {
            holder.bind(itemList[position])
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class LoginViewHolder(
        private val binding: ItemLoginRegisterBinding,
        private val itemClick: (Pair<Int, UserEntity>) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                groupUsernameLayout.isVisible = false
                btnAction.text = item
                btnAction.setOnClickListener {
                    if (checkValidation(this, Constant.ITEM_TYPE_LOGIN)) {
                        val etEmail = binding.etEmail.text.toString().trim()
                        val etPassword = binding.etPassword.text.toString().trim()
                        val userEntity = UserEntity(null, etEmail, etPassword)
                        itemClick(Pair(Constant.ITEM_TYPE_LOGIN, userEntity))
                    }
                }
            }
        }
    }

    inner class RegisterViewHolder(
        private val binding: ItemLoginRegisterBinding,
        private val itemClick: (Pair<Int, UserEntity>) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            with(binding) {
                btnAction.text = item
                btnAction.setOnClickListener {
                    if (checkValidation(this, Constant.ITEM_TYPE_REGISTER)) {
                        val etUsername = binding.etUsername.text.toString().trim()
                        val etEmail = binding.etEmail.text.toString().trim()
                        val etPassword = binding.etPassword.text.toString().trim()
                        val userEntity = UserEntity(etUsername, etEmail, etPassword)
                        itemClick(Pair(Constant.ITEM_TYPE_REGISTER, userEntity))
                    }
                }
            }
        }
    }

    private fun clearFieldForm() {
        binding.etUsername.text = null
        binding.etEmail.text = null
        binding.etPassword.text = null
    }

    private fun checkValidation(binding: ItemLoginRegisterBinding, type: Int?): Boolean {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        var isFormValid = true

        if (type != Constant.ITEM_TYPE_LOGIN) {
            val username = binding.etUsername.text.toString().trim()
            if (username.isEmpty()) {
                isFormValid = false
                binding.tilUsername.isErrorEnabled = true
                binding.tilUsername.error =
                    binding.root.context.getString(R.string.error_username_empty)
            } else {
                binding.tilUsername.isErrorEnabled = false
            }
        }
        when {
            email.isEmpty() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error =
                    binding.root.context.getString(R.string.error_email_empty)
            }
            StringUtils.isEmailValid(email).not() -> {
                isFormValid = false
                binding.tilEmail.isErrorEnabled = true
                binding.tilEmail.error =
                    binding.root.context.getString(R.string.error_email_invalid)
            }
            else -> {
                binding.tilEmail.isErrorEnabled = false
            }
        }
        if (password.isEmpty()) {
            isFormValid = false
            binding.tilPassword.isErrorEnabled = true
            binding.tilPassword.error =
                binding.root.context.getString(R.string.error_password_empty)
        } else {
            binding.tilPassword.isErrorEnabled = false
        }
        return isFormValid
    }
}