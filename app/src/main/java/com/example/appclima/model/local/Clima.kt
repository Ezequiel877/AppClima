package com.example.appclima.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class WeatherResponse {

    @SerializedName("current")
    var main: Main? = null
}


data class Main (
    @SerializedName("sunrise")
    var sunrise : Float = 0.0f,
    @SerializedName("temp")
    var tem: Float = 0.0f,
    @SerializedName("feels_like")
    var pressure: Float = 0.0f,
    @SerializedName("humidity")
    var temp_min: Float = 0.0f,
    @SerializedName("weather")
     var weather: Array<Weather>
)

class Sys {
    @SerializedName("country")
    var country: String? = null
}
data class Weather(@SerializedName("id")val id:String="",@SerializedName("main")val main:String,@SerializedName("description") val description:String="",@SerializedName("icon") val icon:String="")
data class Notas(var id :Int,var title :String, var text:String)

@Parcelize
@Entity(tableName = "memos")
data class NotasEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title: String,
    val text:String) : Parcelable