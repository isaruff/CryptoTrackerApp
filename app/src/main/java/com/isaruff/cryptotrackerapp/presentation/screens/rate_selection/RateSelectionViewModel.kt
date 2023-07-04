package com.isaruff.cryptotrackerapp.presentation.screens.rate_selection

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkRequest
import com.isaruff.cryptotrackerapp.data.local.entities.TrackedCoinEntity
import com.isaruff.cryptotrackerapp.data.worker.CoinTrackerScheduler
import com.isaruff.cryptotrackerapp.domain.use_cases.DeleteTrackedCoinUseCase
import com.isaruff.cryptotrackerapp.domain.use_cases.GetTrackedCoinByIdUseCase
import com.isaruff.cryptotrackerapp.domain.use_cases.UpsertTrackedCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RateSelectionViewModel @Inject constructor(
    private val upsertTrackedCoinUseCase: UpsertTrackedCoinUseCase,
    private val getTrackedCoinByIdUseCase: GetTrackedCoinByIdUseCase,
    private val deleteTrackedCoinUseCase: DeleteTrackedCoinUseCase
) : ViewModel() {

    private val _coinDetailsState = MutableStateFlow<TrackedCoinEntity?>(null)
    val coinDetailsState = _coinDetailsState.asStateFlow()


    fun getCoinDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getTrackedCoinByIdUseCase.invoke(id).collect {
                _coinDetailsState.value = it
            }
        }
    }

    fun startTrackingCoin(trackedCoinEntity: TrackedCoinEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            upsertTrackedCoinUseCase.invoke(entity = trackedCoinEntity)
        }
    }

    fun stopTrackingCoin(trackedCoinEntity: TrackedCoinEntity){
        viewModelScope.launch(Dispatchers.IO) {
            deleteTrackedCoinUseCase.invoke(entity = trackedCoinEntity)
        }
    }


    fun initCoinTracker(context: Context){
        CoinTrackerScheduler.refreshPeriodicWork(context)
    }

}