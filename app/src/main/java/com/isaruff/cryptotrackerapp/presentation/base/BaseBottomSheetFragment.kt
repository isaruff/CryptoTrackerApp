package com.isaruff.cryptotrackerapp.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.isaruff.cryptotrackerapp.R
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseBottomSheetFragment<VB: ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean)-> VB): BottomSheetDialogFragment() {

    private var _binding: VB?=null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    protected fun<T> observeState(state: StateFlow<T>, block: suspend (T) -> Unit){
        lifecycleScope.launch {
            state.flowWithLifecycle(lifecycle).collect {
                block.invoke(it)
            }
        }
    }
}