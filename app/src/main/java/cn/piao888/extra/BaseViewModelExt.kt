package cn.piao888.extra

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.piao888.network.ApiResponse
import cn.piao888.network.AppException
import cn.piao888.network.ResultState
import cn.piao888.network.paresResult
import cn.piao888.viewModel.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

fun <T> BaseViewModel.request(
    block: suspend () -> ApiResponse<T>,
    resultState: MutableLiveData<ResultState<T>>,
    isShowDialog: Boolean = false,
    loadingMessage: String = "加载中...",
//    success: (T) -> Unit
): Job {
    return viewModelScope.launch {
        runCatching {
            if (isShowDialog) resultState.value = ResultState.onLoading(loadingMessage)
            //请求体
            block()
        }.onSuccess {
            //网络请求成功 关闭弹窗
            resultState.paresResult(it.datas)
        }.onFailure {
            it.printStackTrace()
            //网络请求异常 关闭弹窗
        }
    }
}

suspend fun <T> executeResponse(
    response: ApiResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when (response.respCode) {
            200 -> {
                response.datas?.let { success(it) }
            }

            else -> {
                throw AppException(
                    response.respCode,
                    response.respMsg,
                    response.respMsg
                )
            }
        }
    }
}