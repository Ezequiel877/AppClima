package com.example.appclima.model.remote

import com.example.appclima.model.WeatherResponse
import com.example.appclima.repository.ApiClima
import retrofit2.Call

class RetrofitImpl(private val retrofit:RetrofitClient):ApiClima {
    override fun getClima(lat: String, long: String, appikey: String): Call<WeatherResponse> {
        TODO("Not yet implemented")
    }
}