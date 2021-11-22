package com.dev.divig.moviereviewsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.dev.divig.moviereviewsapp.utils.Utils
import com.dev.divig.moviereviewsapp.utils.Utils.isInternetAvailable

abstract class BaseActivity<B : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(), BaseContract.BaseView {
    private lateinit var binding: B
    abstract val viewModelInstance: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initView()
        observeViewModel()
        initScenarioComponent()
    }

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModelInstance
    fun checkInternetConnection(): Boolean {
        return applicationContext.isInternetAvailable()
    }

    fun hideSoftKeyboard() {
        Utils.hideSoftKeyboard(this, binding.root)
    }

    abstract fun initView()
    override fun observeViewModel() {}
    override fun initScenarioComponent() {}
    override fun showEmptyPlaceholder(isVisible: Boolean) {}
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
}