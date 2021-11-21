package com.dev.divig.moviereviewsapp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.dev.divig.moviereviewsapp.utils.Utils.isInternetAvailable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<B : ViewBinding, VM : ViewModel>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : BottomSheetDialogFragment(), BaseContract.BaseView {
    private lateinit var binding: B
    abstract val viewModelInstance: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeViewModel()
        initScenarioComponent()
    }

    fun getViewBinding(): B = binding
    fun getViewModel(): VM = viewModelInstance
    fun checkInternetConnection(): Boolean {
        return requireActivity().isInternetAvailable()
    }

    abstract fun initView()
    override fun observeViewModel() {}
    override fun initScenarioComponent() {}
    override fun showEmptyPlaceholder(isVisible: Boolean) {}
    override fun showContent(isContentVisible: Boolean) {}
    override fun showLoading(isLoading: Boolean) {}
    override fun showError(isErrorEnabled: Boolean, msg: String?) {}
}