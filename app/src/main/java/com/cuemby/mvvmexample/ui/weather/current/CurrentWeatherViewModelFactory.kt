package com.cuemby.mvvmexample.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cuemby.mvvmexample.data.provider.UnitProvider
import com.cuemby.mvvmexample.data.repository.CuembyRepository

class CurrentWeatherViewModelFactory(
    private val cuembyRepository: CuembyRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(cuembyRepository, unitProvider) as T
    }
}