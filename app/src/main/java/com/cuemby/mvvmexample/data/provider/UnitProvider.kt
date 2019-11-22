package com.cuemby.mvvmexample.data.provider

import com.cuemby.mvvmexample.internal.UnitSystem

interface UnitProvider {
    fun getUnitSystem(): UnitSystem
}