package cn.piao888.network;

import cn.piao888.data.bean.MusicBean
import cn.piao888.data.bean.MusicCollectionBean
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    companion object {
        /** 请求根地址 */
        val BASE_URL: String = "http://192.168.3.11:8080"
    }

    @GET("/application/getMusicBean")
    suspend fun musicList(@Query("pageNumber")pageNumber: Int,  @Query("pageSize")pageSize: Int): ApiResponse<MutableList<MusicBean>?>

    @POST("/application/getMusicCollectionBean")
    suspend fun musicCollectionList(@Body requestBody: RequestBody): ApiResponse<MutableList<MusicCollectionBean>?>
}