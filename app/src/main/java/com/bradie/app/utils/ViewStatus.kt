package com.bradie.app.utils

data class ViewStatus<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): ViewStatus<T> {
            return ViewStatus(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(msg: String?, data: T? = null): ViewStatus<T> {
            return ViewStatus(
                Status.ERROR,
                data,
                msg
            )
        }

        fun <T> loading(msg: String?, data: T?): ViewStatus<T> {
            return ViewStatus(
                Status.LOADING,
                data,
                msg
            )
        }
    }
}
