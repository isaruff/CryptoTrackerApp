package com.isaruff.cryptotrackerapp.common

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.isaruff.cryptotrackerapp.data.remote.dto.SimpleCoinResponse
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


fun parseSimpleCoinResponse(json: String): List<SimpleCoinResponse> {
    val gson = Gson()
    val coinDataMap = gson.fromJson<Map<String, Map<String, String>>>(json.trimIndent(), object : TypeToken<Map<String, Map<String, String>>>() {}.type)
    val coinDataList = mutableListOf<SimpleCoinResponse>()
    for ((coinName, coin) in coinDataMap) {
        val usdValue = coin["usd"]?.toDoubleOrNull()
        if (usdValue != null) {
            coinDataList.add(SimpleCoinResponse(coinName, usdValue))
        }
    }
    return coinDataList
}


fun convertUTCtoLocal(utcTime: String): String {
    val utcFormat =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault(Locale.Category.FORMAT))
    utcFormat.timeZone = TimeZone.getTimeZone("UTC")
    val utcDate = utcFormat.parse(utcTime)

    val localFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.GERMAN)
    val deviceTimeZone = TimeZone.getDefault()
    localFormat.timeZone = deviceTimeZone
    return if (utcDate != null) {
        localFormat.format(utcDate)
    } else {
        utcTime
    }
}

//Avoiding crashing while Navigating in very rapid manner
fun NavController.safeNavigate(directions: NavDirections, extras: Navigator.Extras? = null) {
    try {
        if (extras == null) {
            this.navigate(directions)
        } else {
            this.navigate(directions, extras)
        }
    } catch (e: Exception) {
        Log.e("NAVIGATION_ERROR", e.toString())
    }
}



