package com.isaruff.cryptotrackerapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracked_coin_table")
data class TrackedCoinEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val image: String,
    val minValue: Double,
    val maxValue: Double,
    val priceList: List<Double>,
    val checkpoints: List<String>
)
