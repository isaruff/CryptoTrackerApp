package com.isaruff.cryptotrackerapp.common

import com.google.gson.Gson
import com.isaruff.cryptotrackerapp.data.dto.CoinDataResponse
import org.json.JSONObject

fun getCoinObject(jsonString: String, topLevelKey: String): CoinDataResponse? {
    //Turn string into a JSON Object
    val trimmedJsonString = jsonString.trimIndent()
    val jsonObject = JSONObject(trimmedJsonString)
    val result = jsonObject.getJSONObject(topLevelKey)

    //Cast it into a Data Class to work later
    val gson = Gson()

    return gson.fromJson(result.toString(), CoinDataResponse::class.java)

}