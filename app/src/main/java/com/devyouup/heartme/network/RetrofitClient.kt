package com.devyouup.heartme.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val BASE_URL = "https://s3.amazonaws.com/s3.helloheart.home.assignment/"
    private val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level = Level.BODY
        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okhttpClient = OkHttpClient.Builder()
        okhttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val bloodTestApi: BloodTestConfigApi by lazy {
        retrofitClient
            .build()
            .create(BloodTestConfigApi::class.java)
    }

}