package com.cuemby.mvvmexample.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val feelslike: Double,
    @SerializedName("is_day")
    val isDay: String,
    val precip: Double,
    val pressure: Int,
    val temperature: Double,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}