package com.devyouup.heartme.network

import com.devyouup.heartme.model.BloodTestConfig
import retrofit2.Call
import retrofit2.http.GET

interface BloodTestConfigApi {

    @GET("bloodTestConfig.json")
    fun getBloodTestConfig() : Call<BloodTestConfig>
}