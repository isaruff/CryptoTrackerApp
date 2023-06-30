package com.isaruff.cryptotrackerapp.presentation.screens.crypto_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenStarted
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.isaruff.cryptotrackerapp.R
import com.isaruff.cryptotrackerapp.common.Resource
import com.isaruff.cryptotrackerapp.common.convertUTCtoLocal
import com.isaruff.cryptotrackerapp.common.enums.CurrencyTypes
import com.isaruff.cryptotrackerapp.common.safeNavigate
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import com.isaruff.cryptotrackerapp.databinding.FragmentCryptoListBinding
import com.isaruff.cryptotrackerapp.databinding.ItemCryptoCoinBinding
import com.isaruff.cryptotrackerapp.domain.model.CoinDetailsModel
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel
import com.isaruff.cryptotrackerapp.presentation.adapter.GenericListAdapter
import com.isaruff.cryptotrackerapp.presentation.base.BaseFragment
import com.isaruff.cryptotrackerapp.presentation.views.changeBackgroundColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CryptoListFragment :
    BaseFragment<FragmentCryptoListBinding>(FragmentCryptoListBinding::inflate) {

    private val viewModel: CryptoListViewModel by viewModels()
    private lateinit var selectedCurrency: CurrencyTypes

    private val adapter by lazy {
        GenericListAdapter<CoinListModel, ItemCryptoCoinBinding>(
            inflate = ItemCryptoCoinBinding::inflate,
            onBind = { data, _ ->
                imageViewCoin.load(data.image)
                textViewCoinName.text = data.name
                textViewLastUpdated.text = convertUTCtoLocal(data.lastUpdated)
                textViewCoinPrice.text = data.currentPrice.toString()
                imageViewCoinStatus.changeBackgroundColor(
                    requireContext(), selectedCurrency.currencyColor
                )

                this.root.setOnClickListener {
                    findNavController().safeNavigate(
                        directions = CryptoListFragmentDirections.actionCryptoListFragmentToRateSelectionFragment(
                            coinDetails = CoinDetailsModel(
                                id = data.id,
                                name = data.name,
                                image = data.image
                            )
                        )
                    )
                }


            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setInitialCurrency()
        searchCoin()
        binding.initClickListeners()
    }

    private fun setInitialCurrency() {
        observeState(viewModel.selectedCurrency) {
            selectedCurrency = it
        }
    }


    private fun setupRecyclerView() {
        binding.recyclerViewCoinList.adapter = adapter
        observeState(viewModel.coinListState) {
            when (it) {
                is Resource.Error -> {
                    Snackbar.make(binding.root, "Request Error, Loading Cache", Snackbar.LENGTH_LONG).show()
                }

                is Resource.Loading -> {
                    binding.root.isEnabled = false
                    binding.progressBar.root.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.root.isEnabled = true
                    binding.progressBar.root.visibility = View.GONE
                    adapter.submitList(it.data ?: emptyList())
                }
            }
        }


    }

    private fun FragmentCryptoListBinding.initClickListeners() {
        buttonCurrencyBitcoin.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.BITCOIN.currency)
            viewModel.setCurrency(CurrencyTypes.BITCOIN)
            observeState(viewModel.selectedCurrency) {
                selectedCurrency = it
            }
        }
        buttonCurrencyEthereum.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.ETHEREUM.currency)
            viewModel.setCurrency(CurrencyTypes.ETHEREUM)
            observeState(viewModel.selectedCurrency) {
                selectedCurrency = it
            }
        }
        buttonCurrencyRipple.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.RIPPLE.currency)
            viewModel.setCurrency(CurrencyTypes.RIPPLE)
            observeState(viewModel.selectedCurrency) {
                selectedCurrency = it
            }
        }
    }


    private fun searchCoin() {
        binding.editTextSearch.addTextChangedListener { searchText ->
            viewModel.searchCoin(searchText.toString())
            observeState(viewModel.coinSearchResult) {
                adapter.submitList(it)
            }

        }
    }


}