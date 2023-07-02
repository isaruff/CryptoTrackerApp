package com.isaruff.cryptotrackerapp.presentation.screens.crypto_list

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.isaruff.cryptotrackerapp.common.Resource
import com.isaruff.cryptotrackerapp.common.convertUTCtoLocal
import com.isaruff.cryptotrackerapp.common.enums.CurrencyTypes
import com.isaruff.cryptotrackerapp.common.safeNavigate
import com.isaruff.cryptotrackerapp.databinding.FragmentCryptoListBinding
import com.isaruff.cryptotrackerapp.databinding.ItemCryptoCoinBinding
import com.isaruff.cryptotrackerapp.domain.model.CoinDetailsModel
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel
import com.isaruff.cryptotrackerapp.presentation.adapter.GenericListAdapter
import com.isaruff.cryptotrackerapp.presentation.base.BaseFragment
import com.isaruff.cryptotrackerapp.presentation.views.changeBackgroundColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoListFragment :
    BaseFragment<FragmentCryptoListBinding>(FragmentCryptoListBinding::inflate) {

    private val viewModel: CryptoListViewModel by viewModels()
    private lateinit var selectedCurrency: CurrencyTypes

    private val coinMarketsAdapter by lazy {
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
                                image = data.image,
                                idWithCurrency = "${data.currency}_${data.id}"
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
        setCurrency()
        searchCoin()
        binding.initClickListeners()
    }

    private fun setCurrency() {
        observeState(viewModel.selectedCurrency) {
            selectedCurrency = it
        }
    }


    private fun setupRecyclerView() {
        binding.recyclerViewCoinList.apply {
            adapter = coinMarketsAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(15)
        }
        observeState(viewModel.coinListState) {
            when (it) {
                is Resource.Error -> {
                    Snackbar.make(
                        binding.root,
                        "${it.errorDescription}",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is Resource.Loading -> {
                    binding.root.isEnabled = false
                    binding.progressBar.root.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.root.isEnabled = true
                    binding.progressBar.root.visibility = View.GONE
                    coinMarketsAdapter.submitList(it.data ?: emptyList())
                }
            }
        }


    }

    private fun FragmentCryptoListBinding.initClickListeners() {
        buttonCurrencyUSD.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.USD.currency)
            viewModel.setCurrency(CurrencyTypes.USD)

        }
        buttonCurrencyBitcoin.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.BITCOIN.currency)
            viewModel.setCurrency(CurrencyTypes.BITCOIN)

        }
        buttonCurrencyEthereum.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.ETHEREUM.currency)
            viewModel.setCurrency(CurrencyTypes.ETHEREUM)

        }
        buttonCurrencyRipple.setOnClickListener {
            viewModel.getCoinList(CurrencyTypes.RIPPLE.currency)
            viewModel.setCurrency(CurrencyTypes.RIPPLE)
        }
    }


    private fun searchCoin() {
        binding.editTextSearch.addTextChangedListener { searchText ->
            viewModel.searchCoin(searchText.toString())
            observeState(viewModel.coinSearchResult) {
                coinMarketsAdapter.submitList(it)
            }

        }
    }


}