package com.isaruff.cryptotrackerapp.presentation.screens.rate_selection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.isaruff.cryptotrackerapp.common.safeNavigate
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.databinding.FragmentRateSelectionBinding
import com.isaruff.cryptotrackerapp.presentation.base.BaseBottomSheetFragment
import com.isaruff.cryptotrackerapp.presentation.views.toEditable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateSelectionFragment : BaseBottomSheetFragment<FragmentRateSelectionBinding>(FragmentRateSelectionBinding::inflate) {

    private val args: RateSelectionFragmentArgs by navArgs()
    private val viewModel: RateSelectionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCoinImage()

        binding.initClickListeners()
        binding.checkPreviousCoinRecord()

    }


    private fun setCoinImage(){
        binding.imageViewCoin.load(args.coinDetails.image)
    }
    private fun FragmentRateSelectionBinding.getTrackedCoinDetails(): TrackedCoinEntity{
        return TrackedCoinEntity(
            id = args.coinDetails.id,
            name = args.coinDetails.name,
            image = args.coinDetails.image,
            minValue = editTextMin.text.toString().toDouble(),
            maxValue = editTextMax.text.toString().toDouble(),
            priceList = emptyList(),
            checkpoints = emptyList()
        )
    }

    private fun FragmentRateSelectionBinding.checkPreviousCoinRecord(){
        viewModel.getCoinDetails(args.coinDetails.id)
        observeState(viewModel.coinDetailsState){
            if (it==null){
                buttonUpsert.text = "Save"
                buttonDelete.visibility = View.GONE
            } else{
                buttonUpsert.text = "Update"
                editTextMax.text = it.maxValue.toString().toEditable()
                editTextMin.text = it.minValue.toString().toEditable()
                buttonDelete.visibility = View.VISIBLE
            }
        }
    }

    private fun FragmentRateSelectionBinding.initClickListeners(){
        buttonUpsert.setOnClickListener {
            if (isInputValid()){
                viewModel.startTrackingCoin(getTrackedCoinDetails())
            }
        }
        buttonDelete.setOnClickListener {
            viewModel.stopTrackingCoin(getTrackedCoinDetails())
        }
        buttonShowHistory.setOnClickListener {
            findNavController().safeNavigate(directions = RateSelectionFragmentDirections.actionRateSelectionFragmentToCryptoHistoryFragment(args.coinDetails.idWithCurrency))
        }
    }

    private fun FragmentRateSelectionBinding.isInputValid(): Boolean{
        return if (editTextMax.text.toString().isBlank() || editTextMax.text.toString().isBlank() ){
            Toast.makeText(requireContext(), "Cannot leave the fields empty", Toast.LENGTH_SHORT).show()
            false
        } else{
            true
        }
    }


}