package com.isaruff.cryptotrackerapp.presentation.screens.cyrpto_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.isaruff.cryptotrackerapp.R
import com.isaruff.cryptotrackerapp.databinding.FragmentCryptoHistoryBinding
import com.isaruff.cryptotrackerapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoHistoryFragment :
    BaseFragment<FragmentCryptoHistoryBinding>(FragmentCryptoHistoryBinding::inflate) {

    private val viewModel: CryptoHistoryFragmentViewModel by viewModels()
    private val args: CryptoHistoryFragmentArgs by navArgs()
    private val entries = mutableListOf<Entry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHistoryByCoinId(args.idWithCurrency)
        setHistoryChart()
    }

    private fun setHistoryChart() {
        observeState(viewModel.coinHistoryList) { list ->
            list.forEachIndexed { index, element ->
                entries.add(Entry(index.toFloat(), element.toFloat()))
                println(entries)
            }
        }
        val lineDataSet = LineDataSet(entries, "History over 7 days").apply {
            setDrawValues(false)
            setDrawFilled(true)
            lineWidth = 3f
            fillColor = R.color.gray
            fillAlpha = R.color.red
        }
    }


}