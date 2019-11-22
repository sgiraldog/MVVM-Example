package com.cuemby.mvvmexample

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.cuemby.mvvmexample.data.db.CuembyDataBase
import com.cuemby.mvvmexample.data.network.*
import com.cuemby.mvvmexample.data.provider.LocationProvider
import com.cuemby.mvvmexample.data.provider.LocationProviderImpl
import com.cuemby.mvvmexample.data.provider.UnitProvider
import com.cuemby.mvvmexample.data.provider.UnitProviderImpl
import com.cuemby.mvvmexample.data.repository.CuembyRepository
import com.cuemby.mvvmexample.data.repository.CuembyRepositoryImpl
import com.cuemby.mvvmexample.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CuembyApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CuembyApplication))

        bind() from singleton { CuembyDataBase(instance()) }
        bind() from singleton { instance<CuembyDataBase>().currentWeatherDao() }
        bind() from singleton { instance<CuembyDataBase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<CuembyRepository>() with singleton { CuembyRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}