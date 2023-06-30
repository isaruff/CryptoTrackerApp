package com.isaruff.cryptotrackerapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "tracked_coin_table")
data class TrackedCoinEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val image: String,
    val currentPrice: Double,
//    val priceList: List<Double>,
//    val checkpoints: List<String>
)
