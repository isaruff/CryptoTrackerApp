package com.isaruff.cryptotrackerapp.common


sealed class Resource<T>(
    val responseCode: Int? = null,
    val data: T? = null,
    val errorDescription: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Loading<T>() : Resource<T>()
    class Error<T>(errorCode: Int?, errorDescription: String?) :
        Resource<T>(responseCode = errorCode, errorDescription = errorDescription)
}