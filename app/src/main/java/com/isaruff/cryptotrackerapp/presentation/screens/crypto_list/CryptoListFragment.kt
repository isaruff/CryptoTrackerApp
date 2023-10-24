package com.isaruff.cryptotrackerapp.presentation.screens.crypto_list

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.isaruff.cryptotrackerapp.common.Resource
import com.isaruff.cryptotrackerapp.common.changeBackgroundColor
import com.isaruff.cryptotrackerapp.common.enums.CurrencyTypes
import com.isaruff.cryptotrackerapp.common.safeNavigate
import com.isaruff.cryptotrackerapp.common.setIdWithCurrency
import com.isaruff.cryptotrackerapp.databinding.FragmentCryptoListBinding
import com.isaruff.cryptotrackerapp.domain.model.CoinDetailsModel
import com.isaruff.cryptotrackerapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoListFragment :
    BaseFragment<FragmentCryptoListBinding>(FragmentCryptoListBinding::inflate) {

    private val viewModel: CryptoListViewModel by viewModels()
    private lateinit var selectedCurrency: CurrencyTypes
    private lateinit var coinListAdapter: CoinListAdapter


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
        observeState(viewModel.selectedCurrency) { currency ->
            binding.recyclerViewCoinList.apply {
                coinListAdapter = CoinListAdapter(
                    changeCurrencyColor = { imageView ->
                        imageView.changeBackgroundColor(requireContext(), currency.currencyColor)
                    },
                    onClickListener = { itemData ->
                        findNavController().safeNavigate(
                            directions = CryptoListFragmentDirections.actionCryptoListFragmentToRateSelectionFragment(
                                CoinDetailsModel(
                                    id = itemData.id,
                                    name = itemData.name,
                                    image = itemData.image,
                                    idWithCurrency = setIdWithCurrency(
                                        id = itemData.id,
                                        currency = itemData.currency
                                    )
                                )
                            )
                        )
                    }
                )
                adapter = coinListAdapter
                setHasFixedSize(true)
            }


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
                    coinListAdapter.submitList(it.data ?: emptyList())
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
                coinListAdapter.submitList(it)
            }

        }
    }


}