package cn.piao888.network


/**
 * @Author: Steven
 * @Date: 2023/4/27
 * @From: com.linglong.lib.common.network
 * @Note:接口返回外层封装实体
 */
data class ApiResponse<T>(
    val datas: T,
    val respCode: Int,
    val respMsg: String,
    val sno: String,
)
