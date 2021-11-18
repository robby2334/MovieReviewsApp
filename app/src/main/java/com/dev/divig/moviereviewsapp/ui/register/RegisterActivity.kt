package com.dev.divig.moviereviewsapp.ui.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dev.divig.moviereviewsapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() , RegisterContract.View {

    private lateinit var sName: String
    private lateinit var sEmail: String
    private lateinit var sPassword: String
    private lateinit var reEnterPassword: String

    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            sName = binding.etFullName.text.toString()
            sEmail = binding.etEmail.text.toString()
            sPassword = binding.etPassword.text.toString()
            reEnterPassword = binding.etReEnterPassword.text.toString()

            when {
                sName == "" -> {
                    binding.etFullName.error = "Please enter your name"
                    binding.etFullName.requestFocus()
                }
                sEmail == "" -> {
                    binding.etEmail.error = "Please Enter your email"
                    binding.etEmail.requestFocus()
                }
                sPassword == "" -> {
                    binding.etPassword.error = "Password can't be empty"
                    binding.etPassword.requestFocus()
                }

                reEnterPassword == "" -> {
                    binding.etReEnterPassword.error = "Enter your email again"
                    binding.etReEnterPassword.requestFocus()
                }
                else -> {
                    saveUsername(sName, sName, sPassword, reEnterPassword)
                }
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sName: String, sEmail: String) {

    }

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }
    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, RegisterActivity::class.java)
            context?.startActivity(intent)
        }
    }


}


