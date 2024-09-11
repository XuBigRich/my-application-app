package cn.piao888.network;

import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiService: ApiService by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
    NetworkApi.INSTANCE.getApi(ApiService::class.java, ApiService.BASE_URL)
}

class NetworkApi : BaseNetworkApi() {
    /** 请求超时时间 */
    companion object {
        private const val TIME_OUT_SECONDS = 20
        val INSTANCE: NetworkApi by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkApi()
        }
    }


    /**
     * 实现重写父类的setHttpClientBuilder方法，
     * 在这里可以添加拦截器，可以对 OkHttpClient.Builder 做任意操作
     */
    override fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.apply {
//            //ssl的套接工厂
//            sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
//            //设置缓存配置 缓存最大10M
//            cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
//            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
//            addInterceptor(HttpRequestInterceptor())
//            //添加缓存拦截器 可传入缓存天数，不传默认7天
//            addInterceptor(CacheInterceptor())
//            addInterceptor(HttpResponseInterceptor())
//            // 日志拦截器
//            addInterceptor(LogInterceptor())
            //超时时间 连接、读、写
            connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            retryOnConnectionFailure(true)//错误重连
        }
        return builder
    }


    /**
     * 实现重写父类的setRetrofitBuilder方法，
     * 在这里可以对Retrofit.Builder做任意操作，比如添加GSON解析器，protobuf等
     */
    override fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder {
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }
}