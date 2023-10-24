package com.isaruff.cryptotrackerapp.presentation.screens.cyrpto_history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartAnimationType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAStyle
import com.isaruff.cryptotrackerapp.R
import com.isaruff.cryptotrackerapp.databinding.FragmentCryptoHistoryBinding
import com.isaruff.cryptotrackerapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoHistoryFragment :
    BaseFragment<FragmentCryptoHistoryBinding>(FragmentCryptoHistoryBinding::inflate) {

    private val viewModel: CryptoHistoryFragmentViewModel by viewModels()
    private val args: CryptoHistoryFragmentArgs by navArgs()
    private var isDrawn: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHistoryByCoinId(args.coinId)
        observeData()
    }

    private fun observeData() {
        observeState(viewModel.coinHistoryList) {
            setLineChart(it)
        }
    }

    private fun setLineChart(data: List<Double>) {
        if (data.isEmpty()) return
        val aaChartModel = AAChartModel()
        aaChartModel.apply {
            chartType(AAChartType.Line)
            title(getString(R.string.text_while_away))
            titleStyle(AAStyle.Companion.style(color = "#EB5E28"))
            subtitle(args.coinId)
            subtitleStyle(AAStyle.Companion.style(color = "#EB5E28"))
            animationType(AAChartAnimationType.Bounce)
            animationDuration(1000)
            legendEnabled(true)
            dataLabelsStyle(AAStyle.Companion.style(color = "#FFFFFF"))
            axesTextColor("#FFFFFF")
            backgroundColor("#2D3142")
            dataLabelsEnabled(true)
            series(
                arrayOf(
                    AASeriesElement().name(args.coinId).data(data.toTypedArray())
                )
            )
        }
        if (!isDrawn) {
            binding.lineChartView.aa_drawChartWithChartModel(aaChartModel)
            isDrawn = true
        } else {
            binding.lineChartView.aa_refreshChartWithChartModel(aaChartModel)
        }
    }


}