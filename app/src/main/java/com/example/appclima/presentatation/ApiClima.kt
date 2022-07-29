package com.example.appclima.presentatation

import com.example.appclima.model.Main
import com.example.appclima.model.WeatherResponse
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClima {

    @GET("data/3.0/onecall?")
    fun getClima(@Query("lat")lat:String, @Query("lon")long:String, @Query("appid")appikey:String):Call<WeatherResponse>


}

