package com.dev.divig.moviereviewsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding, P : BaseContract.BasePresenter>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity(), BaseContract.BaseView {
    private lateinit var binding: B
    private lateinit var presenter: P

    fun getViewBinding(): B = binding
    fun getPresenter(): P = presenter
    fun setPresenter(presenter: P) {
        this.presenter = presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingFactory(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    abstract fun initView()
    abstract fun initPresenter()
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
}