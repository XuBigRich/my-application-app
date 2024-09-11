package cn.piao888.extra

import androidx.fragment.app.Fragment
import cn.piao888.network.AppException
import cn.piao888.network.ResultState

/**
 * @Author: Steven
 * @Date: 2023/4/26
 * @From: com.linglong.lib.common.extra
 * @Note:BaseViewModel请求协程封装扩展函数
 */
/**
 * 显示页面状态，这里有个技巧，成功回调在第一个，其后两个带默认值的回调可省
 * @param resultState 接口返回值
 * @param onLoading 加载中
 * @param onSuccess 成功回调
 * @param onError 失败回调
 */
fun <T> Fragment.parseState(
    resultState: ResultState<T>,
    onSuccess: (T) -> Unit,
    onError: ((AppException) -> Unit)? = null,
    onLoading: (() -> Unit)? = null
) {
    when (resultState) {
        is ResultState.Loading -> {
            onLoading?.run { this }
        }

        is ResultState.Success -> {
            onSuccess(resultState.data)
        }

        is ResultState.Error -> {
            onError?.run { this(resultState.error) }
        }
    }
}