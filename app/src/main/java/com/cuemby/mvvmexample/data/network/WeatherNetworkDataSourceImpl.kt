package com.cuemby.mvvmexample.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cuemby.mvvmexample.data.network.response.CurrentWeatherResponse
import com.cuemby.mvvmexample.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
    private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downladedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchCurrentWeather = weatherApiService
                .getCurrentWeather(location)
                .await()

            _downloadedCurrentWeather.postValue(fetchCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection", e)
        }
    }
}