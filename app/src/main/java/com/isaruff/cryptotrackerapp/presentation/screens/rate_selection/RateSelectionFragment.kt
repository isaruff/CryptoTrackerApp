package com.isaruff.cryptotrackerapp.presentation.screens.rate_selection

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.isaruff.cryptotrackerapp.R
import com.isaruff.cryptotrackerapp.common.safeNavigate
import com.isaruff.cryptotrackerapp.common.toEditable
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.databinding.FragmentRateSelectionBinding
import com.isaruff.cryptotrackerapp.presentation.base.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateSelectionFragment :
    BaseBottomSheetFragment<FragmentRateSelectionBinding>(FragmentRateSelectionBinding::inflate) {

    private val args: RateSelectionFragmentArgs by navArgs()
    private val viewModel: RateSelectionViewModel by viewModels()
    private var buttonText = ""
    private var permissionGranted: Boolean = false
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
        buttonText =
            if (isGranted) getString(R.string.text_save) else getString(R.string.text_enable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCoinImage()

        binding.initClickListeners()
        binding.checkPreviousCoinRecord()

    }


    private fun setCoinImage() {
        binding.imageViewCoin.load(args.coinDetails.image)
    }

    private fun FragmentRateSelectionBinding.getTrackedCoinDetails(): TrackedCoinEntity {
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

    private fun FragmentRateSelectionBinding.checkPreviousCoinRecord() {
        viewModel.getCoinDetails(args.coinDetails.id)
        observeState(viewModel.coinDetailsState) {
            if (it == null) {
                buttonUpsert.text = getString(R.string.text_save)
                buttonDelete.visibility = View.GONE
                buttonShowHistory.visibility = View.GONE
            } else {
                buttonUpsert.text = getString(R.string.text_update)
                editTextMax.text = it.maxValue.toString().toEditable()
                editTextMin.text = it.minValue.toString().toEditable()
                buttonDelete.visibility = View.VISIBLE
                buttonShowHistory.visibility = View.VISIBLE
            }
        }
    }

    private fun FragmentRateSelectionBinding.initClickListeners() {
        buttonUpsert.setOnClickListener {
            if (isInputValid()) {
                viewModel.startTrackingCoin(getTrackedCoinDetails())
                askNotificationPermission()
                if (permissionGranted) {
                    viewModel.initCoinTracker(requireContext())
                }
            }
        }
        buttonDelete.setOnClickListener {
            viewModel.stopTrackingCoin(getTrackedCoinDetails())
        }
        buttonShowHistory.setOnClickListener {
            findNavController().safeNavigate(
                directions = RateSelectionFragmentDirections.actionRateSelectionFragmentToCryptoHistoryFragment(
                    args.coinDetails.id
                )
            )
        }
    }

    private fun FragmentRateSelectionBinding.isInputValid(): Boolean {
        return if (editTextMax.text.toString().isBlank() || editTextMax.text.toString().isBlank()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.warning_empty_fields), Toast.LENGTH_SHORT
            )
                .show()
            false
        } else {
            true
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireActivity(), Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                binding.buttonUpsert.text = getString(R.string.text_save)
                permissionGranted = true
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.msg_notification),
                    Toast.LENGTH_SHORT
                ).show()
                binding.buttonUpsert.text = getString(R.string.text_enable)
            } else {
                binding.buttonUpsert.text = getString(R.string.text_enable)
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            permissionGranted = true
        }
    }


}