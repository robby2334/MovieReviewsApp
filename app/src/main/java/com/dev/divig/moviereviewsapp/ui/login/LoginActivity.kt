package com.dev.divig.moviereviewsapp.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dev.divig.moviereviewsapp.databinding.ActivityLoginBinding
import com.dev.divig.moviereviewsapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private val viewModel : LoginViewModel by viewModels()


    private lateinit var iUsername : String
    private lateinit var iPassword : String

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            iUsername = binding.etName.text.toString()
            iPassword = binding.etPassword.text.toString()

            RegisterActivity.startActivity(this)

            when{
                iUsername == "" ->{
                    binding.etName.error = "Please enter your name"
                    binding.etPassword.requestFocus()
                }
                iPassword == "" ->{
                    binding.etPassword.error = "Password can't be empty"
                    binding.etPassword.requestFocus()
                }else-> pushLogin(iUsername, iPassword)
            }
        }


    }

    private fun pushLogin(iUsername: String, iPassword: String){
        viewModel.getLoginResultLiveData()

    }


    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        viewModel.getLoginResultLiveData().observe(this){}
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context?) {
            val intent = Intent(context, LoginActivity::class.java)
            context?.startActivity(intent)
        }
    }


}