package com.cuemby.mvvmexample.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.cuemby.mvvmexample.data.provider.UnitProvider
import com.cuemby.mvvmexample.data.repository.CuembyRepository
import com.cuemby.mvvmexample.internal.UnitSystem
import com.cuemby.mvvmexample.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val cuembyRepository: CuembyRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        cuembyRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeferred{
        cuembyRepository.getWeatherLocation()
    }

}
