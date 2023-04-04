package com.example.shared.data.remote

import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import retrofit2.Retrofit
import com.example.core.BuildConfig
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//todo : preparing usecase for get token
class RetrofitClient(val chuckInterceptor : ChuckerInterceptor) {
    inline fun <reified I> create() : I{
        //todo : create auth interceptor

        //okhttp
        val okHttpClient = okhttp3.OkHttpClient.Builder()
            .addInterceptor(chuckInterceptor)
            .connectTimeout(120,TimeUnit.SECONDS)
            .readTimeout(120,TimeUnit.SECONDS)
            .build()

        //retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(I::class.java)
    }
}