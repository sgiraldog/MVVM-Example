package com.cuemby.mvvmexample.data.repository


import androidx.lifecycle.LiveData
import com.cuemby.mvvmexample.data.db.dao.CurrentWeatherDao
import com.cuemby.mvvmexample.data.db.dao.WeatherLocationDao
import com.cuemby.mvvmexample.data.db.entity.CurrentWeatherEntry
import com.cuemby.mvvmexample.data.db.entity.WeatherLocation
import com.cuemby.mvvmexample.data.network.WeatherNetworkDataSource
import com.cuemby.mvvmexample.data.network.response.CurrentWeatherResponse
import com.cuemby.mvvmexample.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime


class CuembyRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherLocationDao: WeatherLocationDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val locationProvider: LocationProvider
) : CuembyRepository {


    init {
        weatherNetworkDataSource.downladedCurrentWeather.observeForever { newCurrentWeater ->
            persistFetchedCurrentWeather(newCurrentWeater)
        }
    }

    override suspend fun getWeatherLocation(): LiveData<WeatherLocation> {
        return withContext(Dispatchers.IO) {
            return@withContext weatherLocationDao.getLocation()
        }
    }

    override suspend fun getCurrentWeather(isMetric: Boolean): LiveData<CurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
            weatherLocationDao.upsert(fetchedWeather.location)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = weatherLocationDao.getLocation().value

        if (lastWeatherLocation == null
            || locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(locationProvider.getPreferredLocationString())

    }

    private fun isFetchCurrentNeeded(lastFetchedTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}