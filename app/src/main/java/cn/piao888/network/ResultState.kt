package cn.piao888.network

import androidx.lifecycle.MutableLiveData

sealed class ResultState<out T> {
    companion object {
        fun <T> onLoading(loadingMessage: String): ResultState<T> = Loading(loadingMessage)
        fun <T> onSuccess(data: T): ResultState<T> = Success(data)
        fun <T> onError(error: AppException): ResultState<T> = Error(error)
    }

    data class Loading(val loadingMessage: String) : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: AppException) : ResultState<Nothing>()
}
fun <T> MutableLiveData<ResultState<T>>.paresResult(result: T) {
    value = ResultState.onSuccess(result)
}
