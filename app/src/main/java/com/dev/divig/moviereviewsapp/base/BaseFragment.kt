package com.dev.divig.moviereviewsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding, P : BaseContract.BasePresenter>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment(), BaseContract.BaseView {
    private lateinit var binding: B
    private lateinit var presenter: P

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initView()
    }

    fun getViewBinding(): B = binding
    fun getPresenter(): P = presenter
    fun setPresenter(presenter: P) {
        this.presenter = presenter
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