package com.isaruff.cryptotrackerapp.common


sealed interface Resource {
    data class Success<T>(val data: T? = null) : Resource
    object Loading : Resource
    data class Error(val errorCode: Int, val description: String? = null) : Resource
}