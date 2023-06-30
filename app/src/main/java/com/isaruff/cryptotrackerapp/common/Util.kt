package com.isaruff.cryptotrackerapp.common

import com.google.gson.Gson
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinDataResponse
import com.isaruff.cryptotrackerapp.data.remote.dto.CoinMarketResponse
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone



fun getSingleCoinObject(jsonString: String, topLevelKey: String): CoinDataResponse? {
    //Turn string into a JSON Object
    /**
     * The server returns dynamic object name for each request,
     * Thus, making it difficult to cast into a simple data class
     */
    val trimmedJsonString = jsonString.trimIndent()
    val jsonObject = JSONObject(trimmedJsonString)
    val result = jsonObject.getJSONObject(topLevelKey)

    //Cast it into a Data Class to work later
    val gson = Gson()
    return gson.fromJson(result.toString(), CoinDataResponse::class.java)

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