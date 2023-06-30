package com.isaruff.cryptotrackerapp.presentation.screens.crypto_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaruff.cryptotrackerapp.common.Resource
import com.isaruff.cryptotrackerapp.common.enums.CurrencyTypes
import com.isaruff.cryptotrackerapp.data.local.entities.CoinMarketsCacheEntity
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketsDto
import com.isaruff.cryptotrackerapp.data.mapper.toCoinListModel
import com.isaruff.cryptotrackerapp.domain.model.CoinListModel
import com.isaruff.cryptotrackerapp.domain.use_cases.GetCacheCoinsUseCase
import com.isaruff.cryptotrackerapp.domain.use_cases.GetCoinListUseCase
import com.isaruff.cryptotrackerapp.domain.use_cases.UpsertCacheCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val upsertCacheCoinUseCase: UpsertCacheCoinUseCase,
    private val getCacheCoinsUseCase: GetCacheCoinsUseCase
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

    private fun getCacheFromDatabase() {
        viewModelScope.launch {
            val currency = _selectedCurrency.value.currency
            _coinListState.value = Resource.Loading()
            getCacheCoinsUseCase.invoke(currency).collect {
                val convertedList = mutableListOf<CoinListModel>()
                it.forEach { coinCache ->
                    convertedList.add(coinCache.toCoinListModel())
                }
                _coinListState.value = Resource.Success(data = convertedList)
            }

        }
    }

    private fun setCacheToDatabase(coinListModel: CoinListModel) {
        viewModelScope.launch {
            val currency = _selectedCurrency.value.currency
            upsertCacheCoinUseCase.invoke(
                coinMarketsCacheEntity = CoinMarketsCacheEntity(
                    id = coinListModel.id,
                    name = coinListModel.name,
                    image = coinListModel.image,
                    lastUpdated = coinListModel.lastUpdated,
                    currency = currency,
                    currentPrice = coinListModel.currentPrice,
                    idWithCurrency = "${currency}_${coinListModel.id}"
                )
            )
        }
    }


    fun searchCoin(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (searchText.isNotBlank()) {
                _coinSearchResult.value = _coinListCache.value.filter {
                    it.name.contains(searchText) || it.id.contains(searchText)
                }
            } else {
                _coinSearchResult.value = _coinListCache.value
            }
        }

    }

    fun setCurrency(currency: CurrencyTypes) {
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
                    is Resource.Error -> {
                        _coinListState.value = Resource.Error(
                            errorCode = it.responseCode,
                            errorDescription = it.errorDescription
                        )
                        getCacheFromDatabase()
                    }

                    is Resource.Loading -> _coinListState.value = Resource.Loading()
                    is Resource.Success -> {
                        val coinListModel = mutableListOf<CoinListModel>()
                        it.data?.forEach { coinMarketResponse ->
                            coinListModel.add(element = coinMarketResponse.toCoinListModel())
                            setCacheToDatabase(coinListModel = coinMarketResponse.toCoinListModel())
                        }
                        _coinListCache.value = coinListModel
                        _coinListState.value = Resource.Success(data = coinListModel)

                    }
                }
            }
        }


    }

}