package com.isaruff.cryptotrackerapp.data.local.room.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class ListDoubleConverter {
    @TypeConverter
    fun fromString(value: String): List<Double> {
        return value.split(",").filter {
            it.isNotBlank()
        }.map { it.toDouble() }
    }

    @TypeConverter
    fun toString(value: List<Double>): String {
        return value.joinToString(",")
    }
}