package com.isaruff.cryptotrackerapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CoinDetailsModel(
    val id: String,
    val name: String,
    val image: String,
    val idWithCurrency: String
): Parcelable