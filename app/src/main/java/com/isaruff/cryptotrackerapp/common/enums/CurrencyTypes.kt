package com.isaruff.cryptotrackerapp.common.enums

import com.isaruff.cryptotrackerapp.R

enum class CurrencyTypes(val currency: String, val currencyColor: Int) {
    USD(currency = "usd", currencyColor = R.color.green),
    BITCOIN(currency = "btc", currencyColor = R.color.color_tertiary),
    RIPPLE(currency = "xrp", currencyColor = R.color.blue),
    ETHEREUM(currency = "eth", currencyColor = R.color.purple)

}