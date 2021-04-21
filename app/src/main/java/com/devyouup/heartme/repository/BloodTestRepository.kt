package com.devyouup.heartme.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.devyouup.heartme.BuildConfig
import com.devyouup.heartme.model.BloodTestConfig
import com.devyouup.heartme.network.RetrofitClient
import com.devyouup.heartme.utils.enrichDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object BloodTestRepository {

    fun fetchBloodTestConfig(): MutableLiveData<BloodTestConfig> {

        val bloodTestConfig = MutableLiveData<BloodTestConfig>()
        val call = RetrofitClient.bloodTestApi.getBloodTestConfig()

        call.enqueue(object: Callback<BloodTestConfig> {
            override fun onFailure(call: Call<BloodTestConfig>, t: Throwable) {
                if (BuildConfig.DEBUG)
                    Log.v("DEBUG : ", t.message.toString())
                bloodTestConfig.value = null
            }

            override fun onResponse(
                call: Call<BloodTestConfig>,
                response: Response<BloodTestConfig>
            ) {
                if (BuildConfig.DEBUG)
                    Log.v("DEBUG : ", response.body().toString())
                val data = response.body()
                bloodTestConfig.value = data
                enrichDataSet(bloodTestConfig)
            }
        })

        return bloodTestConfig
    }

}