package cn.piao888.repository

import cn.piao888.data.bean.MusicBean
import cn.piao888.data.bean.MusicCollectionBean
import cn.piao888.network.ApiResponse
import cn.piao888.network.apiService
import cn.piao888.network.getRequestBodyForJson
import org.json.JSONObject

class MusicViewRepository {
    suspend fun musicList(pageNumber: Int, pageSize: Int): ApiResponse<MutableList<MusicBean>?> {
        return apiService.musicList(pageNumber, pageSize)
    }

    suspend fun musicCollectionList(
        map: MutableMap<String, Any>
    ): ApiResponse<MutableList<MusicCollectionBean>?> {
        val jsonObject = JSONObject()
        jsonObject.put("pageNumber", map["pageNum"])
        jsonObject.put("pageSize", map["pageSize"])
        return apiService.musicCollectionList(jsonObject.getRequestBodyForJson())
    }
}