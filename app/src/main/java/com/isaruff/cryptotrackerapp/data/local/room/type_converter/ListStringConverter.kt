package com.isaruff.cryptotrackerapp.data.local.room.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
@ProvidedTypeConverter
class ListStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return value.joinToString(",")
    }
}