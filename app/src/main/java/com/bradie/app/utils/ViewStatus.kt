package com.bradie.app.utils

sealed class ViewStatus<T>

object LOADING: ViewStatus<Nothing>()
data class FAILED<T>(val message: String): ViewStatus<T>()
data class SUCCESS<T>(val data: T): ViewStatus<T>()