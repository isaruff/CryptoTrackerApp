package com.isaruff.cryptotrackerapp.presentation.screens.rate_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.isaruff.cryptotrackerapp.R
import com.isaruff.cryptotrackerapp.databinding.FragmentRateSelectionBinding
import com.isaruff.cryptotrackerapp.presentation.base.BaseFragment


class RateSelectionFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentRateSelectionBinding?=null
    private val binding get() = _binding!!

    private val args: RateSelectionFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRateSelectionBinding.inflate(inflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCoinImage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setCoinImage(){
        binding.imageViewCoin.load(args.coinDetails.image)
    }


}