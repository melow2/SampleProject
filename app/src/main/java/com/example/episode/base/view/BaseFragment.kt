package com.example.episode.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.episode.base.ext.observeEvent
import com.example.episode.base.viewmodel.AppSingleEvent
import com.example.episode.base.viewmodel.AppViewModel
import com.example.episode.base.viewmodel.AppViewState
import timber.log.Timber

abstract class BaseFragment<
        S : AppViewState,
        E : AppSingleEvent,
        VM : AppViewModel<S, E>,
        VB : ViewDataBinding>(@LayoutRes private val layoutId: Int) : Fragment(), BaseView<S, E> {

    abstract val viewModel: VM
    var binding:VB? =null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        return binding?.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("$this:: onViewCreated()")
        setUpView(view,savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.state.observe(viewLifecycleOwner,::render)
        viewModel.singleEvent.observeEvent(viewLifecycleOwner,::handleEvent)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("$this::onDestroyView()")
        binding = null
    }

    protected abstract fun setUpView(view: View, savedInstanceState: Bundle?)

}