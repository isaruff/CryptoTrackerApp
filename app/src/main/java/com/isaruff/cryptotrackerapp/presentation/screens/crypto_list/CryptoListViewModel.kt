package com.isaruff.cryptotrackerapp.presentation.screens.crypto_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaruff.cryptotrackerapp.common.Resource
import com.isaruff.cryptotrackerapp.common.enums.CurrencyTypes
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketsDto
import com.isaruff.cryptotrackerapp.data.mapper.toCoinListModel
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel
import com.isaruff.cryptotrackerapp.domain.use_cases.GetCoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase
) : ViewModel() {


    private val _coinListState = MutableStateFlow<Resource<List<CoinListModel>>>(Resource.Loading())
    val coinListState = _coinListState.asStateFlow()

    private val _selectedCurrency = MutableStateFlow(CurrencyTypes.BITCOIN)
    val selectedCurrency = _selectedCurrency.asStateFlow()

    private val _coinListCache = MutableStateFlow<List<CoinListModel>>(emptyList())

    private val _coinSearchResult = MutableStateFlow<List<CoinListModel>>(emptyList())
    val coinSearchResult = _coinSearchResult.asStateFlow()

    init {
        getCoinList("btc")
    }



    fun searchCoin(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchText.isNotBlank()){
                _coinSearchResult.value = _coinListCache.value.filter {
                    it.name.contains(searchText) || it.id.contains(searchText)
                }
            } else{
                _coinSearchResult.value = _coinListCache.value
            }
        }

    }

    fun setCurrency(currency: CurrencyTypes){
        viewModelScope.launch(Dispatchers.Main) {
            _selectedCurrency.value = currency
        }

    }

    fun getCoinList(currency: String) {
        //Side note: Implement paging in the future, but API request limit is a major issue
        //Downside: also does not fully follow Clean Architecture
        viewModelScope.launch {
            val coinListResult = getCoinListUseCase.invoke(
                params = CoinMarketsDto(
                    currency = currency,
                    order = "market_cap_dsc",
                    perPage = 50
                )

            )
            coinListResult.collect {
                when (it) {
                    is Resource.Error -> _coinListState.value = Resource.Error(
                        errorCode = it.responseCode,
                        errorDescription = it.errorDescription
                    )

                    is Resource.Loading -> _coinListState.value = Resource.Loading()
                    is Resource.Success -> {
                        val coinListModel = mutableListOf<CoinListModel>()
                        it.data?.forEach { coinMarketResponse ->
                            coinListModel.add(element = coinMarketResponse.toCoinListModel())
                        }
                        _coinListCache.value = coinListModel
                        _coinListState.value = Resource.Success(data = coinListModel)
                    }
                }
            }
        }


    }
    
}