package com.cuemby.mvvmexample.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.cuemby.mvvmexample.R
import com.cuemby.mvvmexample.data.network.ConnectivityInterceptorImpl
import com.cuemby.mvvmexample.data.network.WeatherApiService
import com.cuemby.mvvmexample.data.network.WeatherNetworkDataSourceImpl
import com.cuemby.mvvmexample.internal.glide.GlideApp
import com.cuemby.mvvmexample.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()

    private val viewModelFactory by instance<CurrentWeatherViewModelFactory>()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
//        val weatherApiService = WeatherApiService(ConnectivityInterceptorImpl(this.context!!))
//        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(weatherApiService)
//        weatherNetworkDataSource.downladedCurrentWeather.observe(this, Observer {
//            textview.text = it.toString()
//        })
//
//        GlobalScope.launch(Dispatchers.Main) {
//            weatherNetworkDataSource.fetchCurrentWeather("London")
//
//        }
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()

        val weatherLocation = viewModel.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if (location == null) return@Observer
            updateLocation(location.name)
        })

        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if (it == null) return@Observer
            group_loading.visibility = View.GONE

            updateDate()
            updateTemperatures(it.temperature, it.feelslike)
            updateCondition("Sunny")
            updatePrecipitation(it.precip)
            updateWind(it.windDir, it.windSpeed)
            updateVisibility(it.visibility)

            GlideApp.with(this@CurrentWeatherFragment)
                .load("https://assets.weatherstack.com//images//wsymbols01_png_64//wsymbol_0001_sunny.png")
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperatures(temperature: Double, feelsLikeTemperature: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_feels_like_temperature.text = "Feels like $feelsLikeTemperature$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        textView_precipitation.text = "Precipitation: $precipitationVolume $unitAbbreviation"
    }

    private fun updateWind(windDirection: String, windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("km", "mi")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}
