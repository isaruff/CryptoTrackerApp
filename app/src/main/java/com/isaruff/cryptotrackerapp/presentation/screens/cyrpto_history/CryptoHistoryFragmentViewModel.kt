package com.isaruff.cryptotrackerapp.presentation.screens.cyrpto_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaruff.cryptotrackerapp.domain.use_cases.GetCacheCoinById
import com.isaruff.cryptotrackerapp.domain.use_cases.GetCacheCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoHistoryFragmentViewModel @Inject constructor(
    private val getCacheCoinById: GetCacheCoinById
): ViewModel() {

    private val _coinHistoryList = MutableStateFlow<List<Double>>(emptyList())
    val coinHistoryList = _coinHistoryList.asStateFlow()


    fun getHistoryByCoinId(uniqueId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val coin = getCacheCoinById.invoke(uniqueId)
            coin.collect{
                _coinHistoryList.value = it.sparklineList
            }

        }
    }
}