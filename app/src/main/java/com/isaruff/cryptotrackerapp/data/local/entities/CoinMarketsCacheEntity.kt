package com.isaruff.cryptotrackerapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.isaruff.cryptotrackerapp.data.remote.dto.SparklineList

@Entity(tableName = "coin_markets_cache_table")
data class CoinMarketsCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val idWithCurrency: String,
    val id: String,
    val name: String,
    val image:String,
    val lastUpdated: String,
    val currency: String,
    val currentPrice: Double,
    val sparklineList: List<Double>
)