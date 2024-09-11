package cn.piao888.network

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

fun JSONObject.getRequestBodyForJson(): RequestBody =
    this.toString().toRequestBody("application/json;charset=utf-8".toMediaType())
