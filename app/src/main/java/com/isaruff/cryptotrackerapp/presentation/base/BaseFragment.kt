package com.isaruff.cryptotrackerapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment<VB: ViewBinding>(private val onInflate: (LayoutInflater, ViewGroup?, Boolean) -> VB): Fragment() {

    private var _binding: VB?=null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = onInflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun<T> observeState(state: StateFlow<T>, block: suspend (T) -> Unit){
        lifecycleScope.launch {
            state.flowWithLifecycle(lifecycle).collect {
                block.invoke(it)
            }
        }
    }

}